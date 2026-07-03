
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
        String sql = "SELECT id_usuario, nm_usuario, id_permissao FROM t_usuario WHERE ds_email = ? AND ds_senha = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuario.getText());
            pst.setString(2, txtSenha.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                String nomeUsuario = rs.getString("nm_usuario");
                SessaoUsuario.getInstance().setIdUsuario(rs.getInt("id_usuario"));
                SessaoUsuario.getInstance().setNomeUsuario(nomeUsuario);
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
        lblBemVindo.setForeground(new java.awt.Color(51, 102, 255));
        lblBemVindo.setText("Olá bem vindo de volta!");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuario.setText("Email:");

        lblSenha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenha.setText("Senha:");

        btnLogin.setBackground(new java.awt.Color(51, 102, 255));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        lblAplicativo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblAplicativo.setForeground(new java.awt.Color(255, 153, 51));
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSenha)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSair)
                                    .addGap(79, 79, 79))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(mnEsqueceuSenha)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnMudarSenha)))
                            .addGap(264, 264, 264))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBemVindo)
                                    .addComponent(lblLogin)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblBemVindo2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCadastro))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(219, 219, 219)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAplicativo)
                        .addGap(173, 173, 173))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(lblAplicativo)
                .addGap(57, 57, 57)
                .addComponent(lblLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBemVindo)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnEsqueceuSenha)
                    .addComponent(btnMudarSenha))
                .addGap(26, 26, 26)
                .addComponent(btnLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBemVindo2)
                    .addComponent(btnCadastro))
                .addGap(18, 18, 18)
                .addComponent(btnSair)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(823, 719));
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

    public static void main(String args[]) {
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
