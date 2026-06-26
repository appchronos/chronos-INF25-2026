
package Telas;
import javax.swing.JOptionPane;

// 1 - Importar as bibliotecas
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;

public class CardTarefa extends javax.swing.JPanel {

    // 2 - criar as variáveis necessárias à conexão
    Connection conexao = null;  // É a variável que retorna a conexao
    PreparedStatement pst = null; // É variável com o comando SQL
    ResultSet rs = null; // Variável com o resultado do comando executado
    
    private int segundos = 0, minutos = 0, horas = 0;
    private javax.swing.Timer cronometro;
    private int idTarefa; // Recebido ao instanciar o card
    
    public void IniciarTarefa() {
    // 1. Inicia o cronômetro na tela
    cronometro.start();
    btnIniciar.setEnabled(false);

    // 2. String SQL para registrar o início
    String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Iniciada', NOW())";

    try {
        pst = conexao.prepareStatement(sql);
        
        pst.setInt(1, this.idTarefa); // ID da tarefa deste card específico
        pst.executeUpdate();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao registrar início no banco: " + e.getMessage());
  }
    
}
    
    public void FinalizarTarefa() {
    if (chkConcluido.isSelected()) {
        // 1. Para o cronômetro na tela
        cronometro.stop();
        btnIniciar.setEnabled(false);

        // 2. String SQL para registrar o fim
        String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Finalizada', NOW())";

        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, this.idTarefa);
            pst.executeUpdate();
            
            // Opcional: desativa o checkbox para não clicar de novo
            chkConcluido.setEnabled(false); 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar a conclusão no banco: " + e.getMessage());
        }
    }
    
}
 
     private void AlterarTarefa() {                                           
    // 1. Resgata os textos que já estão aparecendo na tela atualmente
    String nomeAtual = lblNome.getText();
    String descAtual = lblDescricao.getText();
    String valorAtual = lblValor.getText().replace("R$ ", "").trim();

    // 2. Abre caixas de diálogo simples para o usuário editar
    String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome da tarefa:", nomeAtual);
    // Se o usuário cancelar ou deixar o nome vazio (que é obrigatório), cancela a operação
    if (novoNome == null || novoNome.trim().isEmpty()) {
        return; 
    }

    String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição(ou deixe vazio):", descAtual);
    if (novaDescricao == null) return; 

    String novoValorStr = JOptionPane.showInputDialog(this, "Digite o novo valor (ou deixe vazio):", valorAtual);
    if (novoValorStr == null) return;

    // Converte o valor digitado para double
    double novoValor = 0.0;
    try {
        if (!novoValorStr.trim().isEmpty()) {
            novoValor = Double.parseDouble(novoValorStr.replace(",", "."));
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Valor inválido! Operação cancelada.");
        return;
    }

    // 3. String SQL para atualizar os dados no MySQL
    String sql = "UPDATE t_tarefa SET nome = ?, descricao = ?, valor = ? WHERE id = ?";

    try {
        pst = conexao.prepareStatement(sql);

        pst.setString(1, novoNome);
        pst.setString(2, novaDescricao);
        pst.setDouble(3, novoValor);
        pst.setInt(4, this.idTarefa); // Altera apenas a tarefa deste card

        pst.executeUpdate();

        // 4. ATUALIZAÇÃO DA TELA: Altera os JLabels do próprio card imediatamente
        lblNome.setText(novoNome);
        lblDescricao.setText(novaDescricao);
        lblValor.setText("R$ " + String.format("%.2f", novoValor));

        JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao atualizar no banco: " + e.getMessage());
    }
}
     
     private void ExcluirTarefa() {                                           
int resposta = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir esta tarefa de forma permanente?", 
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            
            String sqlAcoes = "DELETE FROM t_acao WHERE id_tarefa = ?";
            String sqlTarefa = "DELETE FROM t_tarefa WHERE id = ?";

            try {
                // CORREÇÃO: Removida a declaração duplicada 'Connection conexao = null;' que gerava erro
                conexao.setAutoCommit(false); 

                try (PreparedStatement stmtAcoes = conexao.prepareStatement(sqlAcoes);
                     PreparedStatement stmtTarefa = conexao.prepareStatement(sqlTarefa)) {

                    stmtAcoes.setInt(1, this.idTarefa);
                    stmtAcoes.executeUpdate();

                    stmtTarefa.setInt(1, this.idTarefa);
                    stmtTarefa.executeUpdate();

                    conexao.commit();

                    // ATUALIZAÇÃO FLUIDA DA TELA
                    java.awt.Container painelPai = this.getParent(); 
                    if (painelPai != null) {
                        painelPai.remove(this);     
                        painelPai.revalidate(); 
                        painelPai.repaint();    
                    }

                    JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");

                } catch (SQLException e) {
                    conexao.rollback(); 
                    throw e;
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir do banco de dados: " + e.getMessage());
            }
        }
}

    public CardTarefa() {
        initComponents(); 
        
        // CORREÇÃO: Inicializa a conexão com o banco de dados para este card
        conexao = AcessoDB.ModuloDbConecta.connector();
        
        cronometro = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segundos++;
                if (segundos == 60) { segundos = 0; minutos++; }
                if (minutos == 60) { minutos = 0; horas++; }
                lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
            }
        });
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCronometro = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        chkConcluido = new javax.swing.JCheckBox();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();

        lblCronometro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCronometro.setText("00:00:00");

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        chkConcluido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConcluidoActionPerformed(evt);
            }
        });

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        lblNome.setText("Nome da Tarefa");

        lblDescricao.setText("Descrição da tarefa");

        lblValor.setText("Valor da Tarefa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(chkConcluido)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescricao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addComponent(lblValor)
                        .addGap(79, 79, 79)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(btnAlterar)
                        .addGap(10, 10, 10)
                        .addComponent(btnExcluir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCronometro)
                        .addGap(68, 68, 68)
                        .addComponent(btnIniciar)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkConcluido)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnIniciar)
                        .addComponent(lblCronometro)
                        .addComponent(lblNome)
                        .addComponent(lblValor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(lblDescricao))
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        IniciarTarefa();
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void chkConcluidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConcluidoActionPerformed
        // TODO add your handling code here:
        FinalizarTarefa();
    }//GEN-LAST:event_chkConcluidoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        AlterarTarefa();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        ExcluirTarefa();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JCheckBox chkConcluido;
    private javax.swing.JLabel lblCronometro;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblValor;
    // End of variables declaration//GEN-END:variables

    void setIdTarefa(int id) {
        this.idTarefa = id;
    }

    void setExibirDados(String nome, String descricao, double valor) {
        lblNome.setText(nome);
        lblDescricao.setText(descricao);
        lblValor.setText("R$ " + String.format("%.2f", valor));
    }
}
