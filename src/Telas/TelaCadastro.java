
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;
import java.awt.HeadlessException;

public class TelaCadastro extends javax.swing.JFrame {
    Connection conexao = null;  
    PreparedStatement pst = null; 
    ResultSet rs = null; 

    public void fazLimpar() {
        txtNome.setText(""); 
        txtDataNasc.setText("");
        txtEmail.setText("");
        txtSenha1.setText("");
        txtSenha2.setText("");
        txtNome.requestFocus();    
    }
    
    public void cadastro() {
        String txtNomeTela = txtNome.getText();
        int idPermissao = 2; 

    String dataFormatada = "";
    try {
        String dataDigitada = txtDataNasc.getText(); 
        java.text.SimpleDateFormat formatoEntrada = new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.text.SimpleDateFormat formatoSaida = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date data = formatoEntrada.parse(dataDigitada);
        dataFormatada = formatoSaida.format(data);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Formato de data inválido! Use DD/MM/AAAA");
        return; 
    }      
        if  (txtNomeTela.isEmpty()) {          
            JOptionPane.showMessageDialog(null," Campos inválidos/não preenchidos na Tela!!!");
        }else {            
            String sql = "insert INTO T_USUARIO(nm_usuario, dt_nascimento, ds_email, ds_senha, id_permissao) values(?, ?, ?, ?, ?)";
         
            try {                
                pst = conexao.prepareStatement(sql);

    pst.setString(1, txtNome.getText());       
    pst.setString(2, dataFormatada);         
    pst.setString(3, txtEmail.getText());      
        String senha1 = txtSenha1.getText();
        String senha2 = txtSenha2.getText();

        String chaveAdmin = JOptionPane.showInputDialog(null, "Se você for Administrador, digite a Chave de Ativação (ou deixe em branco para Usuário Comum):", "Validação de Administrador", JOptionPane.QUESTION_MESSAGE);

        if (chaveAdmin != null && chaveAdmin.equals("adm123")) { 
            pst.setInt(5, idPermissao = 1);
            JOptionPane.showMessageDialog(null, "Acesso de Administrador confirmado!");
        }else{
            pst.setInt(5, idPermissao = 2);
        }
        if (senha1.isEmpty() || senha2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos de senha!");
        }else if (senha1.equals(senha2)) {
                pst.setString(4, senha1);
        int fgInsOK = pst.executeUpdate();
        if (fgInsOK > 0) {
            JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!!!");
                fazLimpar();
    }
} 
        else {
            JOptionPane.showMessageDialog(null, "ERRO: As senhas digitadas não são iguais! Favor verificar.");
}
            } catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Erro real: " + e.getMessage());
}        
        }           
    }
    
    public TelaCadastro() {
        initComponents();
        lblMensagens.setVisible(false);
        conexao = ModuloDbConecta.connector();
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogin2 = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblBemVindo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenha1 = new javax.swing.JTextField();
        btncadastro = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        txtSenha2 = new javax.swing.JTextField();
        lblSenha1 = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblUsuario2 = new javax.swing.JLabel();
        lblMensagens = new javax.swing.JLabel();
        txtDataNasc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Cadastro");
        setResizable(false);

        lblLogin2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblLogin2.setForeground(new java.awt.Color(255, 153, 51));
        lblLogin2.setText("Chrono$");

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLogin.setText("Faça o seu Cadastro");

        lblBemVindo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBemVindo.setForeground(new java.awt.Color(51, 102, 255));
        lblBemVindo.setText("Seja bem vindo!");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuario.setText("Email:");

        lblSenha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenha.setText("Senha:");

        btncadastro.setBackground(new java.awt.Color(51, 102, 255));
        btncadastro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncadastro.setText("Cadastrar");
        btncadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncadastroActionPerformed(evt);
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

        txtSenha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenha2ActionPerformed(evt);
            }
        });

        lblSenha1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSenha1.setText("Repitir Senha:");

        lblUsuario1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuario1.setText("Nome:");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        lblUsuario2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuario2.setText("Data de Nascimento:");

        lblMensagens.setText("Mensagem...");
        lblMensagens.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblMensagensComponentHidden(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(btncadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblUsuario2)
                                    .addComponent(lblUsuario1)
                                    .addComponent(lblLogin)
                                    .addComponent(lblBemVindo)
                                    .addComponent(lblLogin2))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblSenha)
                                    .addComponent(lblUsuario)
                                    .addComponent(lblSenha1))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(txtSenha2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                    .addComponent(txtSenha1))))
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(353, 353, 353))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblMensagens, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblLogin2)
                .addGap(37, 37, 37)
                .addComponent(lblLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBemVindo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario1)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario2)
                    .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha1)
                    .addComponent(txtSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(btncadastro)
                .addGap(18, 18, 18)
                .addComponent(btnVoltar)
                .addGap(54, 54, 54)
                .addComponent(lblMensagens)
                .addGap(94, 94, 94))
        );

        setSize(new java.awt.Dimension(821, 718));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btncadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncadastroActionPerformed
        // TODO add your handling code here:       
        cadastro();   
    }//GEN-LAST:event_btncadastroActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        TelaLogin tlLogin = new TelaLogin();
        tlLogin.setVisible(true);   
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void lblMensagensComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblMensagensComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMensagensComponentHidden

    private void txtSenha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenha2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenha2ActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton btncadastro;
    private javax.swing.JLabel lblBemVindo;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogin2;
    private javax.swing.JLabel lblMensagens;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSenha1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JLabel lblUsuario2;
    private javax.swing.JTextField txtDataNasc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha1;
    private javax.swing.JTextField txtSenha2;
    // End of variables declaration//GEN-END:variables
}
