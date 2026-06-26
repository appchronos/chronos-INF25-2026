
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;

public class TelaAdicionarTarefa extends javax.swing.JInternalFrame {
    
    private TelaTarefa telaPrincipal;
    
    // Variáveis necessárias à conexão
    Connection conexao = null;  
    PreparedStatement pst = null; 
    ResultSet rs = null; 

    public void fazLimpar() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
        txtNome.requestFocus();   
    }

    public TelaAdicionarTarefa(TelaTarefa telaPrincipal) {
        initComponents();
        this.telaPrincipal = telaPrincipal; // Inicialização da referência corrigida!
        
        conexao = ModuloDbConecta.connector();
        if (conexao != null) {
            lblMensagens.setText("Conexão OK!!!");
            lblMensagens.setForeground(Color.blue);
            
            // Carrega os tópicos reais cadastrados no banco de dados
            preencherComboTopicos();
        } else {
            lblMensagens.setText("ERRO - NÃO CONECTADO!");
            lblMensagens.setForeground(Color.red);
        }
    }
    
    // Método para buscar os tópicos no banco e colocar no ComboBox
    private void preencherComboTopicos() {
    String sql = "SELECT id_topico, nm_topico FROM t_topico ORDER BY id_topico ASC";
    
    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();
        
        cbTopico.removeAllItems();
        
        while (rs.next()) {
            // Cria o objeto com o ID e Nome vindos do banco de dados
            Modelos.Topico topico = new Modelos.Topico(rs.getInt("id_topico"), rs.getString("nm_topico"));
            
            // Adiciona o objeto inteiro dentro do ComboBox!
            cbTopico.addItem(topico.toString()); 
            // NOTA: Se o seu combo no NetBeans estiver tipado como JComboBox<Topico>, 
            // você usaria cbTopico.addItem(topico);
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

        String sql = "INSERT INTO t_tarefa (id_usuario, id_topico, nm_tarefa, ds_tarefa, vl_tarefa, dt_criacao) VALUES (?, ?, ?, ?, ?, CURDATE())";

        try {
            pst = conexao.prepareStatement(sql);
            int idLogado = SessaoUsuario.getInstance().getIdUsuario();
            
            // O ID do tópico continua mapeado pelo índice selecionado + 1
            String nomeTopicoSelecionado = cbTopico.getSelectedItem().toString();
            int idTopico = 1; // Padrão de segurança
            String sqlBuscarId = "SELECT id_topico FROM t_topico WHERE nm_topico = ?";
            try (PreparedStatement pstId = conexao.prepareStatement(sqlBuscarId)) {
                pstId.setString(1, nomeTopicoSelecionado);
                try (ResultSet rsId = pstId.executeQuery()) {
                    if (rsId.next()) {
                        idTopico = rsId.getInt("id_topico"); // Captura o ID real e seguro do banco!
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erro ao mapear ID do tópico: " + e.getMessage());
            }

            pst.setInt(2, idTopico);

            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            double valor = txtValor.getText().isEmpty() ? 0.0 : Double.parseDouble(txtValor.getText().replace(",", "."));

            pst.setInt(1, idLogado);
            pst.setInt(2, idTopico);
            pst.setString(3, nome);
            pst.setString(4, descricao);
            pst.setDouble(5, valor);

            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!");
            
            // Recarrega o painel da tela principal para exibir a nova tarefa instantaneamente
            if (telaPrincipal != null) {
                telaPrincipal.carregarTarefasDoBanco(idTopico);
            }
            
            this.dispose(); 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa: " + e.getMessage());
        }
    }

    // Método que sincroniza o combo com a aba que o usuário já estava navegando
    void setTopicoSelecionado(int idTopicoAtual) {
        if (idTopicoAtual >= 1 && idTopicoAtual <= cbTopico.getItemCount()) {
            cbTopico.setSelectedIndex(idTopicoAtual - 1);
        }
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
        lblMensagens = new javax.swing.JLabel();
        cbTopico = new javax.swing.JComboBox<>();

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

        lblMensagens.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMensagens.setText("Mensagens....");

        cbTopico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(lblMensagens, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68))
            .addGroup(layout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(btnFechar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(214, 214, 214)
                            .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(158, 158, 158)
                            .addComponent(lblObservacao)))
                    .addContainerGap(350, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
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
                .addGap(96, 96, 96)
                .addComponent(lblMensagens)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(422, 422, 422)
                    .addComponent(lblObservacao)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarTarefaActionPerformed
        // TODO add your handling code here:
        cadastroTarefa();
    }//GEN-LAST:event_btnAdicionarTarefaActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();  // Fechara Tela corrente/atual!
    }//GEN-LAST:event_btnFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarTarefa;
    private javax.swing.JButton btnFechar;
    private javax.swing.JComboBox<String> cbTopico;
    private javax.swing.JLabel lblAdicionarTarefa;
    private javax.swing.JLabel lblCadPermissoes;
    private javax.swing.JLabel lblDescricaoTarefa;
    private javax.swing.JLabel lblMensagens;
    private javax.swing.JLabel lblObservacao;
    private javax.swing.JLabel lblSelecionarTopico;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

}
