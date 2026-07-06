
package Telas;

import Modelos.SessaoUsuario;
import javax.swing.JOptionPane;

import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;
import javax.swing.JFrame;


    public class TelaLogin extends javax.swing.JFrame {
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
    
        public void logar() {
            String sql = "SELECT id_usuario, nm_usuario, ds_email, id_permissao FROM t_usuario WHERE ds_email = ? AND ds_senha = ?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuario.getText().trim());
                pst.setString(2, new String(txtSenha.getPassword()));
                rs = pst.executeQuery();
                if (rs.next()) {
                    String nomeUsuario = rs.getString("nm_usuario");
                    SessaoUsuario.getInstance().setIdUsuario(rs.getInt("id_usuario"));
                    SessaoUsuario.getInstance().setNomeUsuario(nomeUsuario);
                    SessaoUsuario.getInstance().setEmailUsuario(rs.getString("ds_email"));
                    int idPermissao = 0;
                    try {
                        idPermissao = rs.getInt("id_permissao");
                    } catch (Exception e) {
                        try {
                            idPermissao = rs.getInt("T_PERMISSAO_id_permissao");
                        } catch (Exception e2) {
                            try {
                                idPermissao = rs.getInt("t_permissao_id_permissao");
                            } catch (Exception e3) {
                                System.out.println("Não foi possível encontrar a coluna de permissão.");
                            }
                        }
                    }

                    String perfil = "Usuário Comum";
                    String sqlPermissao = "select ds_permissao from t_permissao where id_permissao = ?";

                    try (PreparedStatement pstP = conexao.prepareStatement(sqlPermissao)) {
                        pstP.setInt(1, idPermissao);
                        try (ResultSet rsP = pstP.executeQuery()) {
                            if (rsP.next()) {
                                perfil = rsP.getString("ds_permissao");
                            }
                        }
                    } catch (Exception e) {
                    }

                    String usuarioDigitado = txtUsuario.getText();
                    Modelos.GerenciadorLocal.verificarEMudarUsuario(usuarioDigitado);

                    JOptionPane.showMessageDialog(this, "Bem-vindo ao Chronos, " + nomeUsuario + "!");

                    TelaTarefa tlPrincipal = new TelaTarefa();

                    if (perfil.equalsIgnoreCase("Administrador")) {
                        System.out.println("Acesso como: Administrador");
                    } else {
                        System.out.println("Acesso como: Usuário Comum");
                    }

                    tlPrincipal.setVisible(true);
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Usuário/Senha INVÁLIDOS!!! Tente outra Vez!");
                    txtUsuario.setText("");
                    txtSenha.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {
                }
            }
        }

    public TelaLogin() {  
        initComponents();
        
        conexao = ModuloDbConecta.connector();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogin = new javax.swing.JLabel();
        lblBemVindo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        lblAplicativo = new javax.swing.JLabel();
        mnEsqueceuSenha = new javax.swing.JLabel();
        lblBemVindo2 = new javax.swing.JLabel();
        btnCadastro = new javax.swing.JButton();
        btnMudarSenha = new javax.swing.JButton();
        txtSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela Login");
        setResizable(false);

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblLogin.setText("Entrar");

        lblBemVindo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBemVindo.setForeground(new java.awt.Color(0, 51, 255));
        lblBemVindo.setText("Olá bem vindo de volta!");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuario.setText("Email:");

        lblSenha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenha.setText("Senha:");

        btnLogin.setBackground(new java.awt.Color(0, 51, 255));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Acessar");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSair.setBackground(new java.awt.Color(255, 255, 204));
        btnSair.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        lblAplicativo.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblAplicativo.setForeground(new java.awt.Color(255, 102, 0));
        lblAplicativo.setText("Chrono$");

        mnEsqueceuSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnEsqueceuSenha.setText("Esqueceu a senha?");

        lblBemVindo2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBemVindo2.setText("Não possui uma conta?");

        btnCadastro.setBackground(new java.awt.Color(255, 102, 0));
        btnCadastro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCadastro.setText("Cadastrar");
        btnCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroActionPerformed(evt);
            }
        });

        btnMudarSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMudarSenha.setText("Mudar Senha");
        btnMudarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMudarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuario)
                    .addComponent(lblSenha))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(mnEsqueceuSenha)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnMudarSenha))
                                .addComponent(txtSenha)
                                .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblBemVindo2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCadastro)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAplicativo)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(326, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLogin)
                        .addGap(434, 434, 434))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblBemVindo)
                        .addGap(375, 375, 375))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(lblAplicativo)
                .addGap(26, 26, 26)
                .addComponent(lblLogin)
                .addGap(57, 57, 57)
                .addComponent(lblBemVindo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSenha))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMudarSenha)
                    .addComponent(mnEsqueceuSenha))
                .addGap(26, 26, 26)
                .addComponent(btnLogin)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBemVindo2)
                    .addComponent(btnCadastro))
                .addGap(86, 86, 86)
                .addComponent(btnSair)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(934, 726));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        logar();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroActionPerformed
        // TODO add your handling code here:
        TelaCadastro tlCadastro = new TelaCadastro();
        tlCadastro.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCadastroActionPerformed

    private void btnMudarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMudarSenhaActionPerformed
        // TODO add your handling code here:
        TelaEsqueceuSenha tlEsqueceuSenha = new TelaEsqueceuSenha();
        tlEsqueceuSenha.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMudarSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastro;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMudarSenha;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel lblAplicativo;
    private javax.swing.JLabel lblBemVindo;
    private javax.swing.JLabel lblBemVindo2;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel mnEsqueceuSenha;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
