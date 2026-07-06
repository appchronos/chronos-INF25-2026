
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
                    JOptionPane.showMessageDialog(this, "\"Senha alterada com sucesso!!!\"");
                    TelaLogin tlLogin = new TelaLogin();
                    tlLogin.setVisible(true);
                    dispose();
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
        
        conexao = ModuloDbConecta.connector();  
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSeuEmail = new javax.swing.JLabel();
        txtSeuEmail = new javax.swing.JTextField();
        lblNovaSenha = new javax.swing.JLabel();
        btnAlterarSenha = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblSenhaRepetida = new javax.swing.JLabel();
        lblLogin2 = new javax.swing.JLabel();
        lblEsqueceuSenha = new javax.swing.JLabel();
        txtNovaSenha = new javax.swing.JPasswordField();
        txtSenhaRepetida = new javax.swing.JPasswordField();

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

        btnAlterarSenha.setBackground(new java.awt.Color(0, 51, 255));
        btnAlterarSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAlterarSenha.setForeground(new java.awt.Color(255, 255, 255));
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

        lblSenhaRepetida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenhaRepetida.setText("Repitir Senha:");

        lblLogin2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblLogin2.setForeground(new java.awt.Color(255, 102, 0));
        lblLogin2.setText("Chrono$");

        lblEsqueceuSenha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblEsqueceuSenha.setText("Você não lembra a senha?");

        txtNovaSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNovaSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNovaSenha)
                            .addComponent(lblSenhaRepetida)
                            .addComponent(lblSeuEmail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSeuEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(txtNovaSenha)
                            .addComponent(txtSenhaRepetida))
                        .addGap(218, 218, 218))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLogin2)
                        .addGap(304, 304, 304))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(310, 310, 310))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(365, 365, 365))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblEsqueceuSenha)
                        .addGap(274, 274, 274))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(lblLogin2)
                .addGap(35, 35, 35)
                .addComponent(lblEsqueceuSenha)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSeuEmail)
                    .addComponent(txtSeuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNovaSenha)
                    .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaRepetida)
                    .addComponent(txtSenhaRepetida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnVoltar)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(834, 707));
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
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void txtSeuEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeuEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSeuEmailActionPerformed

    private void txtNovaSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNovaSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNovaSenhaActionPerformed

    
    public static void main(String args[]) {
        
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
    private javax.swing.JPasswordField txtNovaSenha;
    private javax.swing.JPasswordField txtSenhaRepetida;
    private javax.swing.JTextField txtSeuEmail;
    // End of variables declaration//GEN-END:variables
}
