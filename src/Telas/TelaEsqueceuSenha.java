
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;

public class TelaEsqueceuSenha extends javax.swing.JFrame {
    Connection conexao = null;  
    PreparedStatement pst = null; 
    ResultSet rs = null; 
    
    public void fazLimpar() {
        txtSeuEmail.setText("");
        txtNovaSenha.setText("");
        txtSenhaRepetida.setText("");        
    }
   
    public void fazAlterar(){
 
        
        String txtDescPerTela = txtSeuEmail.getText();
        if  (txtDescPerTela.isEmpty()) {
         
            JOptionPane.showMessageDialog(null," Campos inválidos/não preenchidos na Tela!!!");
        }else {
            String sql = "update t_usuario set ds_senha = ? where ds_email = ?";
            try {
                pst = conexao.prepareStatement(sql);
                    String senha1 = txtNovaSenha.getText();
                    String senha2 = txtSenhaRepetida.getText();
                if (senha1.isEmpty() || senha2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos de senha!");
                }
                
                if (senha1.equals(senha2)) {
                pst.setString(1, senha1);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: As senhas digitadas não são iguais! Favor verificar.");
                    return;
                }
               
                pst.setString(2,txtSeuEmail.getText());
                int fgAltOK = pst.executeUpdate(); 
                if ( fgAltOK > 0 ) { 
                    JOptionPane.showMessageDialog(null," Campos alterados com sucesso!!!");
                    txtSeuEmail.setText("");
                    txtNovaSenha.setText("");
                    txtSenhaRepetida.setText("");
                    txtSeuEmail.requestFocus();
                }
                        
                else{
                    JOptionPane.showMessageDialog(null," ERRO na alteração, favor verificar email!!!!");   
                }

            } catch ( Exception varERRO ) { 
                JOptionPane.showMessageDialog(null," Erro na Alteração Tabela - t_usuario!");
                System.out.println("O ERRO é: " + varERRO.toString()); 
        }
    }    
        
}
    public TelaEsqueceuSenha() {
        initComponents();
 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSeuEmail = new javax.swing.JLabel();
        txtSeuEmail = new javax.swing.JTextField();
        lblNovaSenha = new javax.swing.JLabel();
        txtNovaSenha = new javax.swing.JTextField();
        btnAlterarSenha = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        txtSenhaRepetida = new javax.swing.JTextField();
        lblSenhaRepetida = new javax.swing.JLabel();
        lblLogin2 = new javax.swing.JLabel();
        lblEsqueceuSenha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblSeuEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSeuEmail.setText("Qual é o seu Email?");

        txtSeuEmail.setToolTipText("Digite um email valido");
        txtSeuEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeuEmailActionPerformed(evt);
            }
        });

        lblNovaSenha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaSenha.setText("Nova Senha:");

        txtNovaSenha.setToolTipText("Digite sua nova senha");

        btnAlterarSenha.setBackground(new java.awt.Color(51, 102, 255));
        btnAlterarSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAlterarSenha.setText("Alterar Senha");
        btnAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSenhaActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(255, 255, 204));
        btnVoltar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        txtSenhaRepetida.setToolTipText("Confirme sua senha");
        txtSenhaRepetida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaRepetidaActionPerformed(evt);
            }
        });

        lblSenhaRepetida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenhaRepetida.setText("Repitir Senha:");

        lblLogin2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblLogin2.setForeground(new java.awt.Color(255, 153, 51));
        lblLogin2.setText("Chrono$");

        lblEsqueceuSenha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblEsqueceuSenha.setText("Você não lembra a senha?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNovaSenha)
                            .addComponent(lblSenhaRepetida)
                            .addComponent(lblSeuEmail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSeuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSenhaRepetida, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(150, 150, 150))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEsqueceuSenha)
                            .addComponent(lblLogin2))
                        .addGap(362, 362, 362))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(217, 217, 217))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(265, 265, 265))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogin2)
                .addGap(18, 18, 18)
                .addComponent(lblEsqueceuSenha)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSeuEmail)
                    .addComponent(txtSeuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNovaSenha)
                    .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaRepetida)
                    .addComponent(txtSenhaRepetida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnAlterarSenha)
                .addGap(28, 28, 28)
                .addComponent(btnVoltar)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(701, 471));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarSenhaActionPerformed
        // TODO add your handling code here:
        fazAlterar();
    }//GEN-LAST:event_btnAlterarSenhaActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        TelaLogin tlLogin = new TelaLogin();
        tlLogin.setVisible(true);
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void txtSeuEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeuEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSeuEmailActionPerformed

    private void txtSenhaRepetidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaRepetidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaRepetidaActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(TelaEsqueceuSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEsqueceuSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEsqueceuSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEsqueceuSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEsqueceuSenha().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel lblEsqueceuSenha;
    private javax.swing.JLabel lblLogin2;
    private javax.swing.JLabel lblNovaSenha;
    private javax.swing.JLabel lblSenhaRepetida;
    private javax.swing.JLabel lblSeuEmail;
    private javax.swing.JTextField txtNovaSenha;
    private javax.swing.JTextField txtSenhaRepetida;
    private javax.swing.JTextField txtSeuEmail;
    // End of variables declaration//GEN-END:variables
}
