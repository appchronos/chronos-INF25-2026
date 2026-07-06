
package Telas;
import Modelos.SessaoUsuario;
import javax.swing.JOptionPane;
import java.sql.*;

public class TelaConta extends javax.swing.JInternalFrame {  
    java.sql.Connection con = null;
    java.sql.PreparedStatement pst = null;
    private int idUsuarioLogado;
    private TelaTarefa telaPrincipal;

    public void alterarNome() {
        if (txtUsuNome.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!");
            return;
        }

        String sql = "UPDATE t_usuario SET nm_usuario=? WHERE id_usuario=?";
        try {
            con = AcessoDB.ModuloDbConecta.connector();
            pst = con.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText().trim());
            pst.setInt(2, idUsuarioLogado);

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                SessaoUsuario.getInstance().setNomeUsuario(txtUsuNome.getText().trim());
                if (telaPrincipal != null) {
                    telaPrincipal.atualizarNomeUsuario();
                }
                javax.swing.JOptionPane.showMessageDialog(null, "Nome do usuário alterado com sucesso!");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao alterar nome: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void alterarEmail() {
        if (txtUsuEmail.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "O campo Email não pode estar vazio!");
            return;
        }

        String sql = "UPDATE t_usuario SET ds_email=? WHERE id_usuario=?";
        try {
            con = AcessoDB.ModuloDbConecta.connector();
            pst = con.prepareStatement(sql);
            pst.setString(1, txtUsuEmail.getText().trim());
            pst.setInt(2, idUsuarioLogado);

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                SessaoUsuario.getInstance().setEmailUsuario(txtUsuEmail.getText().trim());
                javax.swing.JOptionPane.showMessageDialog(null, "E-mail do usuário alterado com sucesso!");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao alterar e-mail: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void excluir() {
        String sqlAcoes = "DELETE t_acao FROM t_acao INNER JOIN t_tarefa ON t_acao.id_tarefa = t_tarefa.id_tarefa WHERE t_tarefa.id_usuario = ?";
        String sqlTarefas = "DELETE FROM t_tarefa WHERE id_usuario = ?";
        String sqlUsuario = "DELETE FROM t_usuario WHERE id_usuario = ?";

        int confirma = javax.swing.JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário e todas as suas tarefas?", "Atenção", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirma == javax.swing.JOptionPane.YES_OPTION) {
            try {
                con = AcessoDB.ModuloDbConecta.connector();
                con.setAutoCommit(false);
                pst = con.prepareStatement(sqlAcoes);
                pst.setInt(1, idUsuarioLogado);
                pst.executeUpdate();
                pst.close();
                pst = con.prepareStatement(sqlTarefas);
                pst.setInt(1, idUsuarioLogado);
                pst.executeUpdate();
                pst.close();
                pst = con.prepareStatement(sqlUsuario);
                pst.setInt(1, idUsuarioLogado);
                int apagado = pst.executeUpdate();
                con.commit();

                if (apagado > 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
                    SessaoUsuario.getInstance().limparSessao();

                    if (telaPrincipal != null) {
                        telaPrincipal.dispose();
                    }

                    TelaLogin telaLogin = new TelaLogin();
                    telaLogin.setVisible(true);
                    this.dispose();
                }
            } catch (Exception e) {
                try {
                    if (con != null) {
                        con.rollback();
                    }
                } catch (SQLException ex) {
                }
                javax.swing.JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
            } finally {
                try {
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public TelaConta(TelaTarefa telaPrincipal) {
        initComponents();
        this.telaPrincipal = telaPrincipal;
        this.idUsuarioLogado = SessaoUsuario.getInstance().getIdUsuario();

        txtUsuNome.setText(SessaoUsuario.getInstance().getNomeUsuario());
        txtUsuEmail.setText(SessaoUsuario.getInstance().getEmailUsuario());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsuNome = new javax.swing.JTextField();
        txtUsuEmail = new javax.swing.JTextField();
        lblCadPermissoes = new javax.swing.JLabel();
        lblCodEmail = new javax.swing.JLabel();
        lblDescNome = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        btnAlterarNome = new javax.swing.JButton();
        btnAlterarEmail = new javax.swing.JButton();
        bntVoltar = new javax.swing.JButton();

        lblCadPermissoes.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblCadPermissoes.setForeground(new java.awt.Color(0, 51, 255));
        lblCadPermissoes.setText("Minha Conta");

        lblCodEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodEmail.setText("Email:");

        lblDescNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDescNome.setText("Nome:");

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExcluir.setText("Excluir conta");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAlterarNome.setBackground(new java.awt.Color(0, 51, 255));
        btnAlterarNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAlterarNome.setForeground(new java.awt.Color(255, 255, 255));
        btnAlterarNome.setText("Alterar nome");
        btnAlterarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarNomeActionPerformed(evt);
            }
        });

        btnAlterarEmail.setBackground(new java.awt.Color(0, 51, 255));
        btnAlterarEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAlterarEmail.setForeground(new java.awt.Color(255, 255, 255));
        btnAlterarEmail.setText("Alterar email");
        btnAlterarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarEmailActionPerformed(evt);
            }
        });

        bntVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntVoltar.setForeground(new java.awt.Color(0, 51, 255));
        bntVoltar.setText("Voltar");
        bntVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodEmail)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescNome)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCadPermissoes)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAlterarNome)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAlterarEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bntVoltar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUsuNome)
                                .addComponent(txtUsuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(300, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescNome)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAlterarNome)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodEmail)
                    .addComponent(txtUsuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAlterarEmail)
                .addGap(41, 41, 41)
                .addComponent(btnExcluir)
                .addGap(75, 75, 75)
                .addComponent(bntVoltar)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarNomeActionPerformed
        // TODO add your handling code here:
        alterarNome();
    }//GEN-LAST:event_btnAlterarNomeActionPerformed

    private void btnAlterarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarEmailActionPerformed
        // TODO add your handling code here:
        alterarEmail();
    }//GEN-LAST:event_btnAlterarEmailActionPerformed

    private void bntVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntVoltarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_bntVoltarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntVoltar;
    private javax.swing.JButton btnAlterarEmail;
    private javax.swing.JButton btnAlterarNome;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JLabel lblCadPermissoes;
    private javax.swing.JLabel lblCodEmail;
    private javax.swing.JLabel lblDescNome;
    private javax.swing.JTextField txtUsuEmail;
    private javax.swing.JTextField txtUsuNome;
    // End of variables declaration//GEN-END:variables
}
