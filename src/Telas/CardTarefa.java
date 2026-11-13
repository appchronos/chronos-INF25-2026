
package Telas;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CardTarefa extends javax.swing.JPanel {
    
    private Connection conexao = null;
    private int segundos = 0, minutos = 0, horas = 0;
    private javax.swing.Timer cronometro;
    private int idTarefa;
    private double valorTarefa = 0.0;

    private LocalDateTime dtCriacao = null;
    private LocalDateTime dtPrevisao = null;
    private LocalDateTime prazoCicloAtual = null;
    private LocalDateTime dtFinalizacao = null;
    private Duration duracaoPrevista = null;
    private final Color corPadraoNome;
    private final Color corPadraoCronometro;

    public interface OnTarefaIniciadaListener {

        void onTarefaIniciada(CardTarefa card);
    }
    private OnTarefaIniciadaListener listener;

    public void setOnTarefaIniciadaListener(OnTarefaIniciadaListener listener) {
        this.listener = listener;
    }

    private void atualizarValorTempo() {
        long totalMinutos = (this.horas * 60L) + this.minutos;

        if (totalMinutos <= 0 || this.valorTarefa <= 0) {
            lblValorT.setText("R$ 0,00/min");
            return;
        }

        if (totalMinutos < 60) {
            double valorPorMinuto = this.valorTarefa / (double) totalMinutos;
            lblValorT.setText(String.format("R$ %.2f/min", valorPorMinuto));
        } else {
            double totalHoras = totalMinutos / 60.0;
            double valorPorHora = this.valorTarefa / totalHoras;
            lblValorT.setText(String.format("R$ %.2f/h", valorPorHora));
        }
    }

    public void setProgramacao(Timestamp tsCriacao, Timestamp tsPrevisao) {
        this.dtCriacao = (tsCriacao != null) ? tsCriacao.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES) : null;
        this.dtPrevisao = (tsPrevisao != null) ? tsPrevisao.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES) : null;

        if (this.dtPrevisao == null) {
            this.duracaoPrevista = null;
            this.prazoCicloAtual = null;
            lblPrevisao.setText("Sem prazo");
            lblPrevisao.setToolTipText("Tarefa sem prazo programado");
        } else {
            Duration duracaoEmCache = Modelos.GerenciadorLocal.obterDuracaoPrevista(this.idTarefa);

            if (duracaoEmCache != null) {
                this.duracaoPrevista = duracaoEmCache;
            } else if (this.dtCriacao != null) {
                long totalMinutos = Math.max(0, Duration.between(this.dtCriacao, this.dtPrevisao).toMinutes());
                this.duracaoPrevista = Duration.ofMinutes(totalMinutos);
                Modelos.GerenciadorLocal.salvarDuracaoPrevista(this.idTarefa, this.duracaoPrevista);
            } else {
                this.duracaoPrevista = null;
            }

            if (this.duracaoPrevista != null) {
                long totalSegundos = Math.max(0, this.duracaoPrevista.getSeconds());
                long totalHoras = totalSegundos / 3600;
                long totalMinutos = (totalSegundos % 3600) / 60;
                long totalSegs = totalSegundos % 60;
                lblPrevisao.setText(String.format("%02d:%02d:%02d", totalHoras, totalMinutos, totalSegs));
            } else {
                lblPrevisao.setText(this.dtPrevisao.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            }
            lblPrevisao.setToolTipText("Prazo final: " + this.dtPrevisao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }

        verificarEConfigurarEstadoAtual();
    }

    private void resetarCorStatus() {
        lblNome.setForeground(corPadraoNome);
        lblCronometro.setForeground(corPadraoCronometro);
    }

    private void atualizarStatusPrazo() {
        // Aplica verde ou vermelho no nome e no cronômetro SOMENTE se o usuário utilizou o recurso Programar tarefa
        if (this.dtPrevisao == null) {
            resetarCorStatus();
            return;
        }

        // Se acabou de ser iniciada/resetada (00:00:00) e ainda não concluída, mantém a cor resetada
        if (!chkConcluido.isSelected() && cronometro.isRunning() && horas == 0 && minutos == 0 && segundos == 0) {
            resetarCorStatus();
            return;
        }

        LocalDateTime limiteReferencia = (this.prazoCicloAtual != null) ? this.prazoCicloAtual : this.dtPrevisao;

        LocalDateTime momentoComparacao;
        if (chkConcluido.isSelected() && this.dtFinalizacao != null) {
            momentoComparacao = this.dtFinalizacao;
        } else {
            momentoComparacao = LocalDateTime.now();
        }

        boolean foraDoPrazo = momentoComparacao.isAfter(limiteReferencia);

        if (foraDoPrazo) {
            Color corVermelha = new Color(204, 0, 0); // Vermelho (fora do prazo)
            lblNome.setForeground(corVermelha);
            lblCronometro.setForeground(corVermelha);
        } else {
            Color corVerde = new Color(0, 153, 0); // Verde (dentro do prazo)
            lblNome.setForeground(corVerde);
            lblCronometro.setForeground(corVerde);
        }
    }

    private void verificarEConfigurarEstadoAtual() {
        boolean concluidaLocal = Modelos.GerenciadorLocal.tarefaConcluidaLocalmente(this.idTarefa);

        // Busca as últimas ações no banco para restaurar cronômetro e estado com precisão
        String sql = "SELECT tp_acao, dt_registro_acao FROM t_acao WHERE id_tarefa = ? ORDER BY id_acao DESC LIMIT 2";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, this.idTarefa);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String ultimaAcao = rs.getString("tp_acao");
                    Timestamp dataRegistro = rs.getTimestamp("dt_registro_acao");

                    if (!concluidaLocal && "Iniciada".equals(ultimaAcao) && dataRegistro != null) {
                        LocalDateTime inicio = dataRegistro.toLocalDateTime();
                        LocalDateTime agora = LocalDateTime.now();
                        Duration diferenca = Duration.between(inicio, agora);

                        long totalSegundos = Math.max(0, diferenca.getSeconds());
                        this.horas = (int) (totalSegundos / 3600);
                        this.minutos = (int) ((totalSegundos % 3600) / 60);
                        this.segundos = (int) (totalSegundos % 60);

                        lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));

                        if (this.dtPrevisao != null) {
                            if (inicio.isBefore(this.dtPrevisao)) {
                                this.prazoCicloAtual = this.dtPrevisao;
                            } else if (this.duracaoPrevista != null && !this.duracaoPrevista.isZero()) {
                                this.prazoCicloAtual = inicio.plus(this.duracaoPrevista);
                            } else {
                                this.prazoCicloAtual = this.dtPrevisao;
                            }
                        }

                        cronometro.start();
                        btnIniciar.setEnabled(false);
                        btnFinalizar.setEnabled(true);
                        chkConcluido.setSelected(false);
                        this.dtFinalizacao = null;

                    } else if ("Finalizada".equals(ultimaAcao) || concluidaLocal) {
                        cronometro.stop();
                        btnIniciar.setEnabled(true);
                        btnFinalizar.setEnabled(false);
                        chkConcluido.setSelected(true);

                        LocalDateTime fim = (dataRegistro != null) ? dataRegistro.toLocalDateTime() : LocalDateTime.now();
                        this.dtFinalizacao = fim;

                        // Preserva o tempo decorrido da última execução finalizada sem zerar indevidamente
                        if ("Finalizada".equals(ultimaAcao) && rs.next()) {
                            String acaoAnterior = rs.getString("tp_acao");
                            Timestamp dataInicioAnterior = rs.getTimestamp("dt_registro_acao");
                            if ("Iniciada".equals(acaoAnterior) && dataInicioAnterior != null) {
                                LocalDateTime inicioAnterior = dataInicioAnterior.toLocalDateTime();
                                long totalSegundos = Math.max(0, Duration.between(inicioAnterior, fim).getSeconds());
                                this.horas = (int) (totalSegundos / 3600);
                                this.minutos = (int) ((totalSegundos % 3600) / 60);
                                this.segundos = (int) (totalSegundos % 60);
                                lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));

                                if (this.dtPrevisao != null) {
                                    if (inicioAnterior.isBefore(this.dtPrevisao)) {
                                        this.prazoCicloAtual = this.dtPrevisao;
                                    } else if (this.duracaoPrevista != null && !this.duracaoPrevista.isZero()) {
                                        this.prazoCicloAtual = inicioAnterior.plus(this.duracaoPrevista);
                                    }
                                }
                            } else {
                                this.horas = 0;
                                this.minutos = 0;
                                this.segundos = 0;
                                lblCronometro.setText("00:00:00");
                            }
                        } else if (!"Finalizada".equals(ultimaAcao)) {
                            this.horas = 0;
                            this.minutos = 0;
                            this.segundos = 0;
                            lblCronometro.setText("00:00:00");
                        }
                    }
                } else {
                    btnIniciar.setEnabled(true);
                    btnFinalizar.setEnabled(false);
                    chkConcluido.setSelected(concluidaLocal);
                    this.dtFinalizacao = concluidaLocal ? LocalDateTime.now() : null;
                    if (concluidaLocal) {
                        this.horas = 0;
                        this.minutos = 0;
                        this.segundos = 0;
                        lblCronometro.setText("00:00:00");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar estado da tarefa: " + e.getMessage());
        }

        atualizarStatusPrazo();
        atualizarValorTempo();
    }

    public void IniciarTarefa() {
        // Reseta o tempo, o ganho/tempo e a cor do status ao iniciar novamente
        this.segundos = 0;
        this.minutos = 0;
        this.horas = 0;
        this.dtFinalizacao = null;
        lblCronometro.setText("00:00:00");
        resetarCorStatus();
        atualizarValorTempo();

        if (this.dtPrevisao != null) {
            LocalDateTime agora = LocalDateTime.now();
            if (agora.isBefore(this.dtPrevisao)) {
                this.prazoCicloAtual = this.dtPrevisao;
            } else if (this.duracaoPrevista != null && !this.duracaoPrevista.isZero()) {
                this.prazoCicloAtual = agora.plus(this.duracaoPrevista);
            } else {
                this.prazoCicloAtual = this.dtPrevisao;
            }
        }

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

        this.dtFinalizacao = LocalDateTime.now();
        Modelos.GerenciadorLocal.marcarComoConcluida(this.idTarefa);
        chkConcluido.setSelected(true);
        btnIniciar.setEnabled(true);
        btnFinalizar.setEnabled(false);
        atualizarStatusPrazo();
        atualizarValorTempo();

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
            this.dtFinalizacao = LocalDateTime.now();
            Modelos.GerenciadorLocal.marcarComoConcluida(this.idTarefa);
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);
            atualizarStatusPrazo();
            atualizarValorTempo();

            String sql = "INSERT INTO t_acao (id_tarefa, tp_acao, dt_registro_acao) VALUES (?, 'Finalizada', NOW())";
            try (PreparedStatement pst = conexao.prepareStatement(sql)) {
                pst.setInt(1, this.idTarefa);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao registrar conclusão automática: " + e.getMessage());
            }
        } else {
            this.dtFinalizacao = null;
            this.prazoCicloAtual = this.dtPrevisao;
            Modelos.GerenciadorLocal.desmarcarComoConcluida(this.idTarefa);
            lblCronometro.setText("00:00:00");
            this.segundos = 0;
            this.minutos = 0;
            this.horas = 0;
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);
            resetarCorStatus();
            atualizarValorTempo();
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

            this.valorTarefa = novoValor;
            lblNome.setText(novoNome);
            lblDescricao.setText(novaDescricao);
            lblValor.setText("R$ " + String.format("%.2f", novoValor));
            atualizarValorTempo();

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

                try (PreparedStatement stmtAcoes = conexao.prepareStatement(sqlAcoes); PreparedStatement stmtTarefa = conexao.prepareStatement(sqlTarefa)) {

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
        this.valorTarefa = valor;
        lblNome.setText(nome);
        lblDescricao.setText(descricao);
        lblValor.setText("R$ " + String.format("%.2f", valor));
        atualizarValorTempo();
    }
    
    public CardTarefa(Connection conexaoPai, int idTarefa) {
        initComponents();
        this.corPadraoNome = lblNome.getForeground();
        this.corPadraoCronometro = lblCronometro.getForeground();
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 210, 210)));

        this.conexao = conexaoPai;
        this.idTarefa = idTarefa;

        chkConcluido.setEnabled(true);

        cronometro = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segundos++;
                if (segundos == 60) {
                    segundos = 0;
                    minutos++;
                }
                if (minutos == 60) {
                    minutos = 0;
                    horas++;
                }
                lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
                atualizarStatusPrazo();
                if (segundos == 0) {
                    atualizarValorTempo();
                }
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
        lblPrevisao = new javax.swing.JLabel();
        lblValorT = new javax.swing.JLabel();

        lblCronometro.setText("00:00:00");
        lblCronometro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnIniciar.setText(" Iniciar");
        btnIniciar.setBackground(new java.awt.Color(0, 153, 255));
        btnIniciar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
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
        btnAlterar.setBackground(new java.awt.Color(255, 153, 51));
        btnAlterar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        lblNome.setText("Nome da Tarefa");

        lblDescricao.setText("Descrição da tarefa");

        lblValor.setText("Valor Tarefa");

        btnFinalizar.setText("Finalizar");
        btnFinalizar.setBackground(new java.awt.Color(0, 153, 102));
        btnFinalizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        lblPrevisao.setText("00:00:00");
        lblPrevisao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblValorT.setText("Valor Tempo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(chkConcluido)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescricao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblValor)
                    .addComponent(lblValorT))
                .addGap(149, 149, 149)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCronometro)
                    .addComponent(lblPrevisao))
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAlterar)
                        .addGap(10, 10, 10)
                        .addComponent(btnExcluir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFinalizar)))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkConcluido)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnIniciar)
                                .addComponent(lblCronometro)
                                .addComponent(lblNome)
                                .addComponent(btnFinalizar)
                                .addComponent(lblValor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAlterar)
                            .addComponent(btnExcluir)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblValorT)
                            .addComponent(lblPrevisao)
                            .addComponent(lblDescricao))
                        .addGap(2, 2, 2)))
                .addContainerGap(34, Short.MAX_VALUE))
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
    private javax.swing.JLabel lblPrevisao;
    private javax.swing.JLabel lblValor;
    private javax.swing.JLabel lblValorT;
    // End of variables declaration//GEN-END:variables

}