
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;

public class CardTarefa extends javax.swing.JPanel {
    
    private Connection conexao = null; 
    private int segundos = 0, minutos = 0, horas = 0;
    private javax.swing.Timer cronometro;
    private int idTarefa;
    
    public interface OnTarefaIniciadaListener {

        void onTarefaIniciada(CardTarefa card);
    }
    private OnTarefaIniciadaListener listener;

    public void setOnTarefaIniciadaListener(OnTarefaIniciadaListener listener) {
        this.listener = listener;
    }
    
    private void verificarEConfigurarEstadoAtual() {

        if (Modelos.GerenciadorLocal.tarefaConcluidaLocalmente(this.idTarefa)) {
            chkConcluido.setSelected(true);
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);
            lblCronometro.setText("00:00:00");
            return;
        }

        // Se não estiver no cache local, busca o estado padrão no banco
        String sql = "SELECT tp_acao, dt_registro_acao FROM t_acao WHERE id_tarefa = ? ORDER BY id_acao DESC LIMIT 1";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, this.idTarefa);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String ultimaAcao = rs.getString("tp_acao");
                    Timestamp dataRegistro = rs.getTimestamp("dt_registro_acao");

                    if ("Iniciada".equals(ultimaAcao) && dataRegistro != null) {
                        java.time.LocalDateTime inicio = dataRegistro.toLocalDateTime();
                        java.time.LocalDateTime agora = java.time.LocalDateTime.now();
                        java.time.Duration diferenca = java.time.Duration.between(inicio, agora);

                        long totalSegundos = diferenca.getSeconds();
                        if (totalSegundos < 0) {
                            totalSegundos = 0;
                        }

                        this.horas = (int) (totalSegundos / 3600);
                        this.minutos = (int) ((totalSegundos % 3600) / 60);
                        this.segundos = (int) (totalSegundos % 60);

                        lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));

                        cronometro.start();
                        btnIniciar.setEnabled(false);
                        btnFinalizar.setEnabled(true);
                        chkConcluido.setSelected(false);

                    } else if ("Finalizada".equals(ultimaAcao)) {
                        cronometro.stop();
                        lblCronometro.setText("00:00:00");
                        btnIniciar.setEnabled(true);
                        btnFinalizar.setEnabled(false);
                        chkConcluido.setSelected(true);
                    }
                } else {
                    btnIniciar.setEnabled(true);
                    btnFinalizar.setEnabled(false);
                    chkConcluido.setSelected(false);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar estado da tarefa: " + e.getMessage());
        }
    } 
    
    public void IniciarTarefa() {
        this.segundos = 0;
        this.minutos = 0;
        this.horas = 0;
        lblCronometro.setText("00:00:00");

        Modelos.GerenciadorLocal.desmarcarComoConcluida(this.idTarefa);
        chkConcluido.setSelected(false);
        cronometro.start();

        btnIniciar.setEnabled(false);
        btnFinalizar.setEnabled(true);

        String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Iniciada', NOW())";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, this.idTarefa);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar início no banco: " + e.getMessage());
        }

        if (this.listener != null) {
            this.listener.onTarefaIniciada(this);
        }
    }
    
    public void FinalizarTarefa() {
        if (cronometro.isRunning()) {
            cronometro.stop();
        }

        Modelos.GerenciadorLocal.marcarComoConcluida(this.idTarefa); 
        chkConcluido.setSelected(true);
        btnIniciar.setEnabled(true);
        btnFinalizar.setEnabled(false);

        String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Finalizada', NOW())";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, this.idTarefa);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar a conclusão no banco: " + e.getMessage());
        }
    }
    
    private void TarefaConcluida() {
        if (chkConcluido.isSelected()) {
            
            if (cronometro.isRunning()) {
                cronometro.stop();
            }
            Modelos.GerenciadorLocal.marcarComoConcluida(this.idTarefa);
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);

            String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Finalizada', NOW())";
            try (PreparedStatement pst = conexao.prepareStatement(sql)) {
                pst.setInt(1, this.idTarefa);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao registrar conclusão automática: " + e.getMessage());
            }
        } else {
            
            Modelos.GerenciadorLocal.desmarcarComoConcluida(this.idTarefa);
            lblCronometro.setText("00:00:00");
            this.segundos = 0;
            this.minutos = 0;
            this.horas = 0;
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);

        }
    }
    
    private void AlterarTarefa() {
        String nomeAtual = lblNome.getText();
        String descAtual = lblDescricao.getText();

        String valorTexto = lblValor.getText().replace("R$", "").replace(".", "").replace(",", ".").trim();

        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome da tarefa:", nomeAtual);
        if (novoNome == null || novoNome.trim().isEmpty()) {
            return;
        }

        String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição (ou deixe vazio):", descAtual);
        if (novaDescricao == null) {
            return;
        }

        String novoValorStr = JOptionPane.showInputDialog(this, "Digite o novo valor (ou deixe vazio):", valorTexto);
        if (novoValorStr == null) {
            return;
        }

        double novoValor = 0.0;
        try {
            if (!novoValorStr.trim().isEmpty()) {
                novoValor = Double.parseDouble(novoValorStr.replace(".", "").replace(",", "."));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de valor inválido! Use apenas números, ponto ou vírgula.");
            return;
        }

        String sql = "UPDATE t_tarefa SET nm_tarefa = ?, ds_tarefa = ?, vl_tarefa = ? WHERE id_tarefa = ?";

        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, novoNome);
            pst.setString(2, novaDescricao);
            pst.setDouble(3, novoValor);
            pst.setInt(4, this.idTarefa);

            pst.executeUpdate();

            lblNome.setText(novoNome);
            lblDescricao.setText(novaDescricao);
            lblValor.setText("R$ " + String.format("%.2f", novoValor));

            JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar a tarefa: " + e.getMessage());
        }
    }
     
    private void ExcluirTarefa() {                                            
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir esta tarefa de forma permanente? ATENÇÃO Essa ação vai remover todo o histórico de tempo!", 
            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            String sqlAcoes = "DELETE FROM t_acao WHERE id_tarefa = ?";
            String sqlTarefa = "DELETE FROM t_tarefa WHERE id_tarefa = ?";

            try {
                conexao.setAutoCommit(false); 

                try (PreparedStatement stmtAcoes = conexao.prepareStatement(sqlAcoes);
                     PreparedStatement stmtTarefa = conexao.prepareStatement(sqlTarefa)) {

                    stmtAcoes.setInt(1, this.idTarefa);
                    stmtAcoes.executeUpdate();

                    stmtTarefa.setInt(1, this.idTarefa);
                    stmtTarefa.executeUpdate();

                    conexao.commit();

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
                } finally {
                    conexao.setAutoCommit(true); 
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir do banco de dados: " + e.getMessage());
            }
        }
    }
    
    public void setExibirDados(String nome, String descricao, double valor) {
        lblNome.setText(nome);
        lblDescricao.setText(descricao);
        lblValor.setText("R$ " + String.format("%.2f", valor));
    }
    
    public CardTarefa(Connection conexaoPai, int idTarefa) {
        initComponents(); 
    
        this.conexao = conexaoPai;
        this.idTarefa = idTarefa;
    
        chkConcluido.setEnabled(true); 
    
        cronometro = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segundos++;
                if (segundos == 60) { segundos = 0; minutos++; }
                if (minutos == 60) { minutos = 0; horas++; }
                lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
            }
        });

        verificarEConfigurarEstadoAtual();
    }
    
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
        btnFinalizar = new javax.swing.JButton();

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

        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(lblValor)
                        .addGap(79, 79, 79)))
                .addComponent(lblCronometro)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAlterar)
                        .addGap(10, 10, 10)
                        .addComponent(btnExcluir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFinalizar)))
                .addGap(40, 40, 40))
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
                        .addComponent(lblValor)
                        .addComponent(btnFinalizar)))
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
        TarefaConcluida();
    }//GEN-LAST:event_chkConcluidoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        AlterarTarefa();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        ExcluirTarefa();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // TODO add your handling code here:
        if (cronometro.isRunning()) {
            FinalizarTarefa();
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa clicar em 'Iniciar' antes de finalizar a tarefa!");
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JCheckBox chkConcluido;
    private javax.swing.JLabel lblCronometro;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblValor;
    // End of variables declaration//GEN-END:variables

}