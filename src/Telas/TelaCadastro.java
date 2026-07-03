
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;
import java.awt.HeadlessException;
import java.text.SimpleDateFormat;

public class TelaCadastro extends javax.swing.JFrame {
    Connection conexao = null;  
    PreparedStatement pst = null; 
    ResultSet rs = null; 

    public void fazLimpar() {
        txtNome.setText("");
        dataSelecionada.setDate(null);
        txtEmail.setText("");
        txtSenha1.setText("");
        txtSenha2.setText("");
        txtNome.requestFocus();
    }
    
    public void cadastro() {
        String txtNomeTela = txtNome.getText().trim();
        String txtEmailTela = txtEmail.getText().trim();
        int idPermissao = 2;
        String dataFormatada = "";
        if (dataSelecionada.getDate() != null) {
            java.util.Date dataSelecionadaDoc = dataSelecionada.getDate();
            SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
            dataFormatada = formatoSaida.format(dataSelecionadaDoc);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione sua Data de Nascimento!");
            return;
        }
        if (txtNomeTela.isEmpty() || txtEmailTela.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos inválidos/não preenchidos na Tela!!!");
            return;
        }
        String senha1 = new String(txtSenha1.getPassword());
        String senha2 = new String(txtSenha2.getPassword());
        if (senha1.isEmpty() || senha2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos de senha!");
            return;
        }
        if (!senha1.equals(senha2)) {
            JOptionPane.showMessageDialog(null, "ERRO: As senhas digitadas não são iguais! Favor verificar.");
            return;
        }
        String sql = "INSERT INTO T_USUARIO(nm_usuario, dt_nascimento, ds_email, ds_senha, id_permissao) values(?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeTela);
            pst.setString(2, dataFormatada);
            pst.setString(3, txtEmailTela);
            pst.setString(4, senha1);
            String chaveAdmin = JOptionPane.showInputDialog(null, "Se você for Administrador, digite a Chave de Ativação (ou deixe em branco para Usuário Comum):", "Validação de Administrador", JOptionPane.QUESTION_MESSAGE);
            if (chaveAdmin != null && chaveAdmin.equals("adm123")) {
                idPermissao = 1;
                JOptionPane.showMessageDialog(null, "Acesso de Administrador confirmado!");
            } else {
                idPermissao = 2;
            }
            pst.setInt(5, idPermissao);
            int fgInsOK = pst.executeUpdate();
            if (fgInsOK > 0) {
                JOptionPane.showMessageDialog(this, "Registro incluído com sucesso!!!");
                fazLimpar();
                TelaLogin tlLogin = new TelaLogin();
                tlLogin.setVisible(true);
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no banco: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar statement: " + ex.getMessage());
            }
        }
    }
    
    public TelaCadastro() {
        initComponents();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2000, java.util.Calendar.JANUARY, 1);
        dataSelecionada.setCalendar(cal);
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
        btncadastro = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblSenha1 = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblUsuario2 = new javax.swing.JLabel();
        txtSenha1 = new javax.swing.JPasswordField();
        txtSenha2 = new javax.swing.JPasswordField();
        dataSelecionada = new com.toedter.calendar.JDateChooser();

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

        dataSelecionada.setDoubleBuffered(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(btncadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(353, 353, 353))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblSenha)
                                    .addComponent(lblUsuario)
                                    .addComponent(lblSenha1)
                                    .addComponent(lblUsuario2)
                                    .addComponent(lblUsuario1))
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblLogin)
                                    .addComponent(lblBemVindo)
                                    .addComponent(lblLogin2))
                                .addGap(70, 70, 70)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSenha1)
                            .addComponent(txtSenha2)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataSelecionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail))
                        .addGap(194, 194, 194))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario1)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataSelecionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario2))
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
                .addGap(164, 164, 164))
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
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

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
    private com.toedter.calendar.JDateChooser dataSelecionada;
    private javax.swing.JLabel lblBemVindo;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogin2;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSenha1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JLabel lblUsuario2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha1;
    private javax.swing.JPasswordField txtSenha2;
    // End of variables declaration//GEN-END:variables
}
