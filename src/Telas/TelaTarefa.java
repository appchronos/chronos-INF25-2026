
package Telas;

import Modelos.SessaoUsuario;
import java.sql.*;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import AcessoDB.ModuloDbConecta;
import java.awt.Component;


public class TelaTarefa extends javax.swing.JFrame {

    private Connection conexao = null;  
    private int idTopicoAtual = 1;
    
    public void atualizarNomeUsuario() {
        nomeUsuario.setText(SessaoUsuario.getInstance().getNomeUsuario());
    }
    
    private void tutorial() {
        
        TelaAdicionarTarefa telaCadastroAberta = null;
        TelaVisaoGeral telaVisaoGeralAberta = null;

        for (javax.swing.JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.isVisible()) {
                if (frame instanceof TelaAdicionarTarefa) {
                    telaCadastroAberta = (TelaAdicionarTarefa) frame;
                    break;
                } else if (frame instanceof TelaVisaoGeral) {
                    telaVisaoGeralAberta = (TelaVisaoGeral) frame;
                    break;
                }
            }
        }

        if (telaCadastroAberta != null) {
            telaCadastroAberta.exibirTutorialTela();
        } else if (telaVisaoGeralAberta != null) {
            telaVisaoGeralAberta.exibirTutorialTela();
        } else {
            boolean possuiTarefas = false;

            for (java.awt.Component comp : pnlListaTarefas.getComponents()) {
                if (comp instanceof CardTarefa) { 
                    possuiTarefas = true;
                    break;
                }
            }

            String textoTutorial = "<html><body style='width: 360px; font-family: Segoe UI, sans-serif;'>";

            if (possuiTarefas) {
                textoTutorial += "<h2 style='color: #107C41; margin-bottom: 5px;'>Guia de Gerenciamento de Tarefas</h2>"
                        + "<p>Muito bem! Você já tem tarefas listadas. Veja como interagir com elas:</p>"
                        + "<hr>"
                        + "<ol>"
                        + "<li><b>Iniciar Tarefa:</b> Clique no botão de ação dentro do card para iniciar a atividade.</li>"
                        + "<li><b>Prioridade Dinâmica:</b> Ao iniciar uma tarefa, ela é <b>movida automaticamente para o topo</b> da sua lista!</li>"
                        + "<li><b>Sincronização em Tempo Real:</b> Assim que você interage com uma tarefa, o seu gráfico na <b>Visão Geral</b> é atualizado instantaneamente.</li>"
                        + "<li><b>Atalho de Ajuda:</b> Lembre-se do <b>Ctrl + T</b> para abrir este guia rapidamente.</li>"
                        + "</ol>";
            } else {
                textoTutorial += "<h2 style='color: #2B579A; margin-bottom: 5px;'>Guia Rápido do Aplicativo</h2>"
                        + "<p>Olá, <b>" + nomeUsuario.getText() + "</b>! Veja como organizar sua rotina aqui:</p>"
                        + "<hr>"
                        + "<ol>"
                        + "<li><b>Navegar por Tópicos:</b> Clique nos botões do topo para filtrar suas tarefas por categoria.</li>"
                        + "<li><b>Criar Tarefas:</b> Clique no botão <b>'Criar Tarefa'</b> na parte inferior para abrir o formulário.</li>"
                        + "<li><b>Lista Dinâmica:</b> Se não houver tarefas no tópico, o app exibirá um aviso.</li>"
                        + "<li><b>Atalho Útil:</b> Pressione <b>Ctrl + T</b> em qualquer janela para ver a ajuda correspondente!</li>"
                        + "</ol>";
            }

            textoTutorial += "</body></html>";

            JOptionPane.showMessageDialog(this, textoTutorial, "Tutorial do Usuário", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
     public void carregarTarefasDoBanco(int idTopico) {
        this.idTopicoAtual = idTopico;

        for (Component comp : pnlListaTarefas.getComponents()) {
            comp.setVisible(false);
        }
        pnlListaTarefas.removeAll();
        pnlListaTarefas.revalidate(); 

        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = ModuloDbConecta.connector();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao checar conexão: " + ex.getMessage());
        }

        String sql = "SELECT * FROM t_tarefa WHERE id_usuario = ? AND id_topico = ?";

        try {
            PreparedStatement pstLocal = conexao.prepareStatement(sql);
            pstLocal.setInt(1, SessaoUsuario.getInstance().getIdUsuario());
            pstLocal.setInt(2, idTopico);
            boolean encontrouTarefas = false;

            ResultSet rsLocal = pstLocal.executeQuery();
            while (rsLocal.next()) {
                encontrouTarefas = true;
                int id = rsLocal.getInt("id_tarefa");
                String nome = rsLocal.getString("nm_tarefa");
                String desc = rsLocal.getString("ds_tarefa");
                double valor = rsLocal.getDouble("vl_tarefa");

                CardTarefa card = new CardTarefa(this.getConexao(), id);
                card.setExibirDados(nome, desc, valor);

                card.setOnTarefaIniciadaListener(new CardTarefa.OnTarefaIniciadaListener() {
                    @Override
                    public void onTarefaIniciada(CardTarefa cardIniciado) {

                        pnlListaTarefas.remove(cardIniciado);
                        pnlListaTarefas.add(cardIniciado, 0);
                        pnlListaTarefas.revalidate();
                        pnlListaTarefas.repaint();

                        for (javax.swing.JInternalFrame frame : desktopPane.getAllFrames()) {
                            if (frame instanceof TelaVisaoGeral && frame.isVisible()) {
                                ((TelaVisaoGeral) frame).atualizarDashboard();
                                break;
                            }
                        }
                    }
                });
                card.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));
                pnlListaTarefas.add(card);
            }

            rsLocal.close();
            pstLocal.close();

            if (!encontrouTarefas) {
                javax.swing.JLabel lblAvisoVazio = new javax.swing.JLabel("Você não tem tarefas aqui. Clique em 'Criar Tarefa' para começar!");
                lblAvisoVazio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 14));
                lblAvisoVazio.setForeground(java.awt.Color.GRAY);
                lblAvisoVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnlListaTarefas.add(javax.swing.Box.createVerticalStrut(20));
                pnlListaTarefas.add(lblAvisoVazio);
            } else {
                pnlListaTarefas.add(javax.swing.Box.createVerticalGlue());
            }

            pnlListaTarefas.revalidate();
            pnlListaTarefas.repaint();
            scrollPainelCards.revalidate();
            scrollPainelCards.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas do tópico: " + e.getMessage());
        }
    }
    
    public void selecionarTopico(int numeroTopico) {
        this.idTopicoAtual = numeroTopico;
        carregarTarefasDoBanco(numeroTopico);
    }
    
    public Connection getConexao() {
        try {
            if (this.conexao == null || this.conexao.isClosed()) {
                this.conexao = ModuloDbConecta.connector();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao reabrir conexão no Getter: " + e.getMessage());
        }
        return this.conexao;
    }

    public TelaTarefa() {
        initComponents();

        lblMensagem.setText("Dica: Use Ctrl + T para abrir o tutorial desta lista. Ele muda quando você tem tarefas criadas!");
        
        if (SessaoUsuario.getInstance().getNomeUsuario() != null) {
            nomeUsuario.setText(SessaoUsuario.getInstance().getNomeUsuario());
        }
        
        conexao = ModuloDbConecta.connector();
            
        carregarTarefasDoBanco(idTopicoAtual);
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        scrollPainelCards = new javax.swing.JScrollPane();
        pnlListaTarefas = new javax.swing.JPanel();
        btnEstudo = new javax.swing.JButton();
        btnCasa = new javax.swing.JButton();
        btnProjeto = new javax.swing.JButton();
        btnRotina = new javax.swing.JButton();
        btnTrabalho = new javax.swing.JButton();
        btnViagem = new javax.swing.JButton();
        btnLivro = new javax.swing.JButton();
        btnPesquisa = new javax.swing.JButton();
        btnOutro = new javax.swing.JButton();
        lblSelecionarTopico = new javax.swing.JLabel();
        btnNovaTarefa = new javax.swing.JButton();
        lblFrase = new javax.swing.JLabel();
        nomeUsuario = new javax.swing.JLabel();
        lblMensagem = new javax.swing.JLabel();
        mnItens = new javax.swing.JMenuBar();
        MnConfiguracao = new javax.swing.JMenu();
        mnUsuario = new javax.swing.JMenuItem();
        mnTutorial = new javax.swing.JMenuItem();
        mnIndicadorTempo = new javax.swing.JMenu();
        mnIndicadorVG = new javax.swing.JMenuItem();
        Sair = new javax.swing.JMenu();
        mnSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minhas Tarefas");
        setResizable(false);

        pnlListaTarefas.setLayout(new javax.swing.BoxLayout(pnlListaTarefas, javax.swing.BoxLayout.Y_AXIS));
        scrollPainelCards.setViewportView(pnlListaTarefas);

        btnEstudo.setText("Estudo");
        btnEstudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstudoActionPerformed(evt);
            }
        });

        btnCasa.setText("Casa");
        btnCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCasaActionPerformed(evt);
            }
        });

        btnProjeto.setText("Projeto");
        btnProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProjetoActionPerformed(evt);
            }
        });

        btnRotina.setText("Rotina");
        btnRotina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRotinaActionPerformed(evt);
            }
        });

        btnTrabalho.setText("Trabalho");
        btnTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrabalhoActionPerformed(evt);
            }
        });

        btnViagem.setText("Viagem");
        btnViagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViagemActionPerformed(evt);
            }
        });

        btnLivro.setText("Livro");
        btnLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLivroActionPerformed(evt);
            }
        });

        btnPesquisa.setText("Pesquisa");
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        btnOutro.setText("Outro");
        btnOutro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutroActionPerformed(evt);
            }
        });

        lblSelecionarTopico.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSelecionarTopico.setForeground(new java.awt.Color(255, 255, 255));
        lblSelecionarTopico.setText("Selecione um Tópico:");

        btnNovaTarefa.setText("Criar Tarefa");
        btnNovaTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaTarefaActionPerformed(evt);
            }
        });

        lblFrase.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFrase.setForeground(new java.awt.Color(255, 255, 255));
        lblFrase.setText("Que tal criar uma tarefa?");

        nomeUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nomeUsuario.setForeground(new java.awt.Color(255, 255, 255));
        nomeUsuario.setText("Usuario");

        lblMensagem.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblMensagem.setForeground(new java.awt.Color(255, 255, 255));
        lblMensagem.setText("Tutorial Mensagem");

        desktopPane.setLayer(scrollPainelCards, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnEstudo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnCasa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnProjeto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnRotina, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnTrabalho, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnViagem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnLivro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnPesquisa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnOutro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(lblSelecionarTopico, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnNovaTarefa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(lblFrase, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(nomeUsuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(lblMensagem, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, desktopPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblFrase)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomeUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSelecionarTopico))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, desktopPaneLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(btnEstudo)
                                .addGap(18, 18, 18)
                                .addComponent(btnCasa)
                                .addGap(18, 18, 18)
                                .addComponent(btnProjeto)
                                .addGap(18, 18, 18)
                                .addComponent(btnRotina)
                                .addGap(19, 19, 19)
                                .addComponent(btnTrabalho)
                                .addGap(18, 18, 18)
                                .addComponent(btnViagem)))
                        .addGap(18, 18, 18)
                        .addComponent(btnPesquisa)
                        .addGap(18, 18, 18)
                        .addComponent(btnLivro)
                        .addGap(18, 18, 18)
                        .addComponent(btnOutro))
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(desktopPaneLayout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addComponent(btnNovaTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollPainelCards, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMensagem))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelecionarTopico)
                    .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFrase)
                        .addComponent(nomeUsuario)))
                .addGap(8, 8, 8)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCasa)
                    .addComponent(btnProjeto)
                    .addComponent(btnRotina)
                    .addComponent(btnTrabalho)
                    .addComponent(btnViagem)
                    .addComponent(btnLivro)
                    .addComponent(btnPesquisa)
                    .addComponent(btnOutro)
                    .addComponent(btnEstudo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPainelCards, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNovaTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMensagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNovaTarefa.getAccessibleContext().setAccessibleName("Clique aqui para Criar Tarefa");

        MnConfiguracao.setForeground(new java.awt.Color(0, 51, 255));
        MnConfiguracao.setText("Configurações");
        MnConfiguracao.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        mnUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        mnUsuario.setText("Conta");
        mnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUsuarioActionPerformed(evt);
            }
        });
        MnConfiguracao.add(mnUsuario);

        mnTutorial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnTutorial.setText("Tutorial");
        mnTutorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTutorialActionPerformed(evt);
            }
        });
        MnConfiguracao.add(mnTutorial);

        mnItens.add(MnConfiguracao);
        MnConfiguracao.getAccessibleContext().setAccessibleDescription("");

        mnIndicadorTempo.setForeground(new java.awt.Color(255, 102, 0));
        mnIndicadorTempo.setText("Indicadores de Tempo");
        mnIndicadorTempo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        mnIndicadorVG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnIndicadorVG.setText("Visão Geral");
        mnIndicadorVG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnIndicadorVGActionPerformed(evt);
            }
        });
        mnIndicadorTempo.add(mnIndicadorVG);

        mnItens.add(mnIndicadorTempo);

        Sair.setText("Sair");
        Sair.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SairActionPerformed(evt);
            }
        });

        mnSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnSair.setText("Exit");
        mnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSairActionPerformed(evt);
            }
        });
        Sair.add(mnSair);

        mnItens.add(Sair);

        setJMenuBar(mnItens);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        setSize(new java.awt.Dimension(857, 674));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSairActionPerformed
        // TODO add your handling code here:
        int sairOK = JOptionPane.showConfirmDialog(null,"Você deseja realmente Sair?","ATENÇÂO !!!",JOptionPane.YES_NO_OPTION);
        if (sairOK == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_mnSairActionPerformed

    private void SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SairActionPerformed

    private void mnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUsuarioActionPerformed
        TelaConta tlUsuario = null;

        // Verifica se a tela de Conta já está aberta no desktopPane
        for (javax.swing.JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof TelaConta) {
                tlUsuario = (TelaConta) frame;
                break;
            }
        }

        // Se não estiver aberta, cria uma nova instância e adiciona ao desktopPane
        if (tlUsuario == null) {
            tlUsuario = new TelaConta(this); // Passando 'this' caso seu construtor precise do parent
            desktopPane.add(tlUsuario);

            // Centraliza a janela interna no desktopPane
            int x = (desktopPane.getWidth() - tlUsuario.getWidth()) / 2;
            int y = (desktopPane.getHeight() - tlUsuario.getHeight()) / 2;
            tlUsuario.setLocation(x, y);
        }

        // Exibe e traz a tela para a frente
        tlUsuario.setVisible(true);
        try {
            tlUsuario.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }  
    }//GEN-LAST:event_mnUsuarioActionPerformed

    private void btnNovaTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaTarefaActionPerformed
        // TODO add your handling code here:
        TelaAdicionarTarefa telaCadastro = null;

        for (javax.swing.JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof TelaAdicionarTarefa) {
                telaCadastro = (TelaAdicionarTarefa) frame;
                break;
            }
        }

        if (telaCadastro == null) {
            telaCadastro = new TelaAdicionarTarefa(this);
            desktopPane.add(telaCadastro);
        }

        telaCadastro.setTopicoSelecionado(idTopicoAtual);
        telaCadastro.setVisible(true);
        try {
            telaCadastro.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnNovaTarefaActionPerformed

    private void btnEstudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstudoActionPerformed
        // TODO add your handling code here:
        selecionarTopico(1);
    }//GEN-LAST:event_btnEstudoActionPerformed

    private void btnCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCasaActionPerformed
        // TODO add your handling code here:
        selecionarTopico(2);
    }//GEN-LAST:event_btnCasaActionPerformed

    private void btnProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProjetoActionPerformed
        // TODO add your handling code here:
        selecionarTopico(3);
    }//GEN-LAST:event_btnProjetoActionPerformed

    private void btnRotinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRotinaActionPerformed
        // TODO add your handling code here:
        selecionarTopico(4);
    }//GEN-LAST:event_btnRotinaActionPerformed

    private void btnTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrabalhoActionPerformed
        // TODO add your handling code here:
        selecionarTopico(5);
    }//GEN-LAST:event_btnTrabalhoActionPerformed

    private void btnViagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViagemActionPerformed
        // TODO add your handling code here:
        selecionarTopico(6);
    }//GEN-LAST:event_btnViagemActionPerformed

    private void btnLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLivroActionPerformed
        // TODO add your handling code here:
        selecionarTopico(7);
    }//GEN-LAST:event_btnLivroActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here:
        selecionarTopico(8);
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void btnOutroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutroActionPerformed
        // TODO add your handling code here:
        selecionarTopico(9);
    }//GEN-LAST:event_btnOutroActionPerformed

    private void mnTutorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTutorialActionPerformed
        // TODO add your handling code here:
        tutorial();
    }//GEN-LAST:event_mnTutorialActionPerformed

    private void mnIndicadorVGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnIndicadorVGActionPerformed
        // TODO add your handling code here:
        TelaVisaoGeral tlVisao = null;

        for (javax.swing.JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof TelaVisaoGeral) {
                tlVisao = (TelaVisaoGeral) frame;
                break;
            }
        }

        if (tlVisao == null) {
            tlVisao = new TelaVisaoGeral();
            desktopPane.add(tlVisao);

            int x = (desktopPane.getWidth() - tlVisao.getWidth()) / 2;
            int y = (desktopPane.getHeight() - tlVisao.getHeight()) / 2;
            tlVisao.setLocation(x, y);
        }

        tlVisao.atualizarDashboard(); 
        tlVisao.setVisible(true);

        try {
            tlVisao.setSelected(true); 
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_mnIndicadorVGActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaTarefa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MnConfiguracao;
    private javax.swing.JMenu Sair;
    private javax.swing.JButton btnCasa;
    private javax.swing.JButton btnEstudo;
    private javax.swing.JButton btnLivro;
    private javax.swing.JButton btnNovaTarefa;
    private javax.swing.JButton btnOutro;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnProjeto;
    private javax.swing.JButton btnRotina;
    private javax.swing.JButton btnTrabalho;
    private javax.swing.JButton btnViagem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel lblFrase;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblSelecionarTopico;
    private javax.swing.JMenu mnIndicadorTempo;
    private javax.swing.JMenuItem mnIndicadorVG;
    private javax.swing.JMenuBar mnItens;
    private javax.swing.JMenuItem mnSair;
    private javax.swing.JMenuItem mnTutorial;
    private javax.swing.JMenuItem mnUsuario;
    private javax.swing.JLabel nomeUsuario;
    private javax.swing.JPanel pnlListaTarefas;
    private javax.swing.JScrollPane scrollPainelCards;
    // End of variables declaration//GEN-END:variables
}
