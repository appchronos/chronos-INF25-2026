
package Telas;

import Modelos.SessaoUsuario;
import javax.swing.JOptionPane;
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;

public class TelaAdicionarTarefa extends javax.swing.JInternalFrame {
    
    private TelaTarefa telaPrincipal;
    private Connection conexao = null;

    private void preencherComboTopicos() {
        String sql = "SELECT id_topico, nm_topico FROM t_topico ORDER BY id_topico ASC";

        cbTopico.setModel(new javax.swing.DefaultComboBoxModel<>());

        try (PreparedStatement pstLocal = conexao.prepareStatement(sql); ResultSet rsLocal = pstLocal.executeQuery()) {

            cbTopico.removeAllItems();

            while (rsLocal.next()) {
                
                Modelos.Topico topico = new Modelos.Topico(rsLocal.getInt("id_topico"), rsLocal.getString("nm_topico"));
                ((javax.swing.DefaultComboBoxModel) cbTopico.getModel()).addElement(topico);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tópicos: " + e.getMessage());
        }
    }
    
    public void cadastroTarefa() {
        if (txtNome.getText().trim().isEmpty() || cbTopico.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Nome da tarefa e selecione um Tópico!");
            return;
        }

        Modelos.Topico topicoSelecionado = (Modelos.Topico) cbTopico.getSelectedItem();
        int idTopico = topicoSelecionado.getId();

        double valor = 0.0;
        String valorTexto = txtValor.getText().trim();
        if (!valorTexto.isEmpty()) {
            try {
                valor = Double.parseDouble(valorTexto.replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Formato de valor numérico inválido! Use apenas números e ponto/vírgula.");
                txtValor.requestFocus();
                return;
            }
        }

        String sql = "INSERT INTO t_tarefa (id_usuario, id_topico, nm_tarefa, ds_tarefa, vl_tarefa, dt_criacao) VALUES (?, ?, ?, ?, ?, CURDATE())";

        try (PreparedStatement pstLocal = conexao.prepareStatement(sql)) {
            int idLogado = SessaoUsuario.getInstance().getIdUsuario();
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();

            pstLocal.setInt(1, idLogado);
            pstLocal.setInt(2, idTopico);
            pstLocal.setString(3, nome);
            pstLocal.setString(4, descricao);
            pstLocal.setDouble(5, valor);

            pstLocal.executeUpdate();

            JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!");

            if (telaPrincipal != null) {
                telaPrincipal.carregarTarefasDoBanco(idTopico);
            }

            this.dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa no banco de dados: " + e.getMessage());
        }
    }
    
    void setTopicoSelecionado(int idTopicoAtual) {
        for (int i = 0; i < cbTopico.getItemCount(); i++) {
            Object item = cbTopico.getItemAt(i);
            if (item instanceof Modelos.Topico) {
                Modelos.Topico t = (Modelos.Topico) item;
                if (t.getId() == idTopicoAtual) {
                    cbTopico.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    public void exibirTutorialTela() {
        String textoTutorial = "<html>"
                + "<body style='width: 360px; font-family: Segoe UI, sans-serif;'>"
                + "<h2 style='color: #FF9933; margin-bottom: 5px;'>Como Cadastrar uma Nova Tarefa</h2>"
                + "<p>Siga o passo a passo abaixo para adicionar um novo registro:</p>"
                + "<hr>"
                + "<ol>"
                + "<li><b>Nome da Tarefa:</b> Insira um título claro e direto para a atividade que pretende executar.</li>"
                + "<li><b>Descrição:</b> Detalhe os objetivos ou notas complementares sobre a tarefa (opcional).</li>"
                + "<li><b>Valor/Peso:</b> Defina uma métrica de importância numérica para o seu controle individual.</li>"
                + "<li><b>Tópico Automático:</b> O sistema pré-seleciona a categoria com base no botão clicado na tela anterior.</li>"
                + "</ol>"
                + "</body>"
                + "</html>";

        JOptionPane.showMessageDialog(this, textoTutorial, "Tutorial - Criar Tarefa", JOptionPane.INFORMATION_MESSAGE);
    }

    public TelaAdicionarTarefa(TelaTarefa telaPrincipal) {
        initComponents();

        lblMensagem.setText("Dica: Dúvidas sobre os campos? Pressione Ctrl + T para ver o guia de cadastro.");
        
        this.telaPrincipal = telaPrincipal;

        if (this.telaPrincipal != null) {
            this.conexao = this.telaPrincipal.getConexao();
        } else {
            this.conexao = ModuloDbConecta.connector();
        }

        preencherComboTopicos();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblObservacao = new javax.swing.JLabel();
        lblAdicionarTarefa = new javax.swing.JLabel();
        btnAdicionarTarefa = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        lblDescricaoTarefa = new javax.swing.JLabel();
        lblCadPermissoes = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        lblValor = new javax.swing.JLabel();
        lblSelecionarTopico = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnFechar = new javax.swing.JButton();
        cbTopico = new javax.swing.JComboBox<>();
        lblMensagem = new javax.swing.JLabel();

        lblObservacao.setText("(*) = Campos Obrigatórios.");

        lblAdicionarTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAdicionarTarefa.setText("(*)Adicionar Tarefa:");

        btnAdicionarTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdicionarTarefa.setText("Adicionar Tarefa");
        btnAdicionarTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarTarefaActionPerformed(evt);
            }
        });

        lblDescricaoTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDescricaoTarefa.setText("Descrição da Tarefa:");

        lblCadPermissoes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCadPermissoes.setText("Nova Tarefa");

        lblValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblValor.setText("Valor das Tarefa:");

        lblSelecionarTopico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSelecionarTopico.setText("(*)Selecionar Tópico:");

        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFechar.setText("Cancelar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        cbTopico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9" }));

        lblMensagem.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblMensagem.setText("Tutorial Mensagem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(216, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblObservacao)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblAdicionarTarefa)
                                            .addComponent(lblDescricaoTarefa)
                                            .addComponent(lblValor)))
                                    .addComponent(lblSelecionarTopico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTopico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdicionarTarefa)))
                            .addComponent(lblMensagem))
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnFechar)
                        .addGap(367, 367, 367))))
            .addGroup(layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(lblCadPermissoes)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelecionarTopico)
                    .addComponent(cbTopico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdicionarTarefa)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoTarefa)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValor))
                .addGap(48, 48, 48)
                .addComponent(btnAdicionarTarefa)
                .addGap(18, 18, 18)
                .addComponent(btnFechar)
                .addGap(18, 18, 18)
                .addComponent(lblObservacao)
                .addGap(18, 18, 18)
                .addComponent(lblMensagem)
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarTarefaActionPerformed
        // TODO add your handling code here:
        cadastroTarefa();
    }//GEN-LAST:event_btnAdicionarTarefaActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();  
    }//GEN-LAST:event_btnFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarTarefa;
    private javax.swing.JButton btnFechar;
    private javax.swing.JComboBox<String> cbTopico;
    private javax.swing.JLabel lblAdicionarTarefa;
    private javax.swing.JLabel lblCadPermissoes;
    private javax.swing.JLabel lblDescricaoTarefa;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblObservacao;
    private javax.swing.JLabel lblSelecionarTopico;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

}
