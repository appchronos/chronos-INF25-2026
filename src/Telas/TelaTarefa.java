
package Telas;
import java.sql.*;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import AcessoDB.ModuloDbConecta;
import java.awt.Component;
import javax.swing.JToggleButton;
import java.awt.Color;


public class TelaTarefa extends javax.swing.JFrame {
    // Variáveis de conexão do Banco de Dados
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // Controla qual o tópico selecionado no momento (Padrão: Tópico 1)
    private int idTopicoAtual = 1;
    

    public TelaTarefa() {
        initComponents();
        conexao = ModuloDbConecta.connector();
        
        // Garante que o painel de tarefas use o BoxLayout vertical via código por segurança
        pnlListaTarefas.setLayout(new BoxLayout(pnlListaTarefas, BoxLayout.Y_AXIS));
        
        // Carrega as tarefas do Tópico 1 assim que o programa abre
        carregarTarefasDoBanco(idTopicoAtual);
        // 3 - Fazer/executar a conexão ao banco de Dados com 
        // o retorno na variável "conexao"
        conexao = ModuloDbConecta.connector();
        if (conexao != null) {
            lblMensagem.setText("Conexão OK!!!");
            lblMensagem.setForeground(Color.blue);
        }else {
            lblMensagem.setText("ERRO - NÃO CONECTADO!");
            lblMensagem.setForeground(Color.red);
        }
    }

// Método responsável por buscar no banco e renderizar os cards dinamicamente
    public void carregarTarefasDoBanco(int idTopico) {
        this.idTopicoAtual = idTopico;
        
        // 1. Limpa todos os cards antigos que estão na tela
        pnlListaTarefas.removeAll();
        
        // 2. Query filtrando pelo Usuário Logado na Sessão e pelo Tópico clicado
        String sql = "SELECT * FROM t_tarefa WHERE id_usuario = ? AND id_topico = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, SessaoUsuario.getInstance().getIdUsuario());
            pst.setInt(2, idTopico);
            rs = pst.executeQuery();
            
            // 3. Varre o banco criando um objeto CardTarefa para cada linha encontrada
            while (rs.next()) {
                CardTarefa card = new CardTarefa();
                
                // Passa os dados do banco para dentro do objeto do card
                card.setIdTarefa(rs.getInt("id"));
                card.setExibirDados(
                    rs.getString("nm_tarefa"),
                    rs.getString("ds_tarefa"),
                    rs.getDouble("vl_tarefa")
                );
                
                // Adiciona o card físico dentro do painel vertical da tela
                pnlListaTarefas.add(card);
            }
            
            // 4. Força o Java Swing a recalcular o layout e redesenhar os elementos na hora
            pnlListaTarefas.revalidate();
            pnlListaTarefas.repaint();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas do tópico: " + e.getMessage());
        }
    }
    
    // Método auxiliar associado aos eventos de clique dos 9 botões de tópicos
    private void selecionarTopico(int numeroTopico) {
        this.idTopicoAtual = numeroTopico;
        carregarTarefasDoBanco(numeroTopico);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMensagens = new javax.swing.JLabel();
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
        btnNovaTarefa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nomeUsuario = new javax.swing.JLabel();
        lblMensagem = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnUsuario = new javax.swing.JMenuItem();
        Sair = new javax.swing.JMenu();
        mnSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minhas Tarefas");
        setResizable(false);

        lblMensagens.setText("Mensagem...");

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

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPainelCards, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnEstudo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCasa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProjeto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRotina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTrabalho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnViagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLivro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOutro)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEstudo)
                    .addComponent(btnCasa)
                    .addComponent(btnProjeto)
                    .addComponent(btnRotina)
                    .addComponent(btnTrabalho)
                    .addComponent(btnViagem)
                    .addComponent(btnLivro)
                    .addComponent(btnPesquisa)
                    .addComponent(btnOutro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPainelCards, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        btnNovaTarefa.setText("Criar Tarefa");
        btnNovaTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaTarefaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Selecione um Tópico:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Seja bem vindo(a)");

        nomeUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomeUsuario.setText("usuário");

        lblMensagem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMensagem.setText("Mensagem...");

        jMenu1.setText("Configurações");

        mnUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        mnUsuario.setText("Usuário");
        mnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUsuarioActionPerformed(evt);
            }
        });
        jMenu1.add(mnUsuario);

        jMenuBar1.add(jMenu1);

        Sair.setText("Sair");
        Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SairActionPerformed(evt);
            }
        });

        mnSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        mnSair.setText("Exit");
        mnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSairActionPerformed(evt);
            }
        });
        Sair.add(mnSair);

        jMenuBar1.add(Sair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(btnNovaTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(356, 356, 356)
                        .addComponent(lblMensagens, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomeUsuario)
                        .addGap(173, 173, 173)
                        .addComponent(jLabel1)
                        .addGap(175, 175, 175)
                        .addComponent(lblMensagem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(nomeUsuario))
                    .addComponent(jLabel1)
                    .addComponent(lblMensagem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(desktopPane)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblMensagens)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNovaTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        btnNovaTarefa.getAccessibleContext().setAccessibleName("Clique aqui para Criar Tarefa");

        setSize(new java.awt.Dimension(857, 674));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSairActionPerformed
        // TODO add your handling code here:
                // Verificar se realmente deseja sair e Encerrar o sistema.
        int sairOK = JOptionPane.showConfirmDialog(null,"Você deseja realmente Sair?","ATENÇÂO !!!",JOptionPane.YES_NO_OPTION);
        if (sairOK == JOptionPane.YES_OPTION) {
            // Se sair for YES
            System.exit(0);
        }
    }//GEN-LAST:event_mnSairActionPerformed

    private void SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SairActionPerformed

    private void mnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUsuarioActionPerformed
        // TODO add your handling code here:
        TelaUsuario tlUsuario = new TelaUsuario();
        // Com a Classe/TelaPrincipal na memória, devemos fazê-la visível!
        tlUsuario.setVisible(true);  
    }//GEN-LAST:event_mnUsuarioActionPerformed

    private void btnNovaTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaTarefaActionPerformed
        // TODO add your handling code here:
// Instancia a tela passando 'this' (esta própria TelaTarefa) como referência de controle
        TelaAdicionarTarefa telaCadastro = new TelaAdicionarTarefa(this);
        
        // Adiciona o JInternalFrame dentro do JDesktopPane principal da tela
        desktopPane.add(telaCadastro);
        
        // Pré-seleciona no ComboBox o tópico que o usuário já estava visualizando
        telaCadastro.setTopicoSelecionado(idTopicoAtual);
        
        // Torna visível na interface
        telaCadastro.setVisible(true);
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

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaTarefa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblMensagens;
    private javax.swing.JMenuItem mnSair;
    private javax.swing.JMenuItem mnUsuario;
    private javax.swing.JLabel nomeUsuario;
    private javax.swing.JPanel pnlListaTarefas;
    private javax.swing.JScrollPane scrollPainelCards;
    // End of variables declaration//GEN-END:variables
}
