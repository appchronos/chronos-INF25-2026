
package Telas;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import AcessoDB.ModuloDbConecta;
import Modelos.SessaoUsuario;
import javax.swing.JOptionPane;

public class TelaVisaoGeral extends javax.swing.JInternalFrame {

    public void atualizarDashboard() {
        
        int idUsuario = SessaoUsuario.getInstance().getIdUsuario();
        carregarTempoTotal(idUsuario);
        carregarTarefaRecordista(idUsuario);
        carregarTempoMedio(idUsuario); 
        carregarTabelaHistorico(idUsuario);
        carregarGrafico(idUsuario);
    }
    
    public void exibirTutorialTela() {
        String textoTutorial = "<html>"
                + "<body style='width: 380px; font-family: Segoe UI, sans-serif;'>"
                + "<h2 style='color: #009999; margin-bottom: 5px;'>Guia de Indicadores e Desempenho</h2>"
                + "<p>Esta tela exibe o panorama consolidado do seu rendimento pessoal:</p>"
                + "<hr>"
                + "<ul>"
                + "<li><b>Tempo Total das Tarefas:</b> Exibe a soma absoluta de todo o tempo investido em suas atividades acumuladas.</li>"
                + "<li><b>Tarefa Recordista:</b> Destaca o nome da tarefa que consumiu a maior parcela de tempo até o momento.</li>"
                + "<li><b>Média de Tempo:</b> Apresenta a duração média ponderada calculada em tempo real com base no histórico das suas tarefas executadas.</li>"
                + "<li><b>Gráficos e Histórico:</b> O ranking exibe o Top 5 das atividades mais longas com barras proporcionais de progresso.</li>"
                + "</ul>"
                + "</body>"
                + "</html>";

        JOptionPane.showMessageDialog(this, textoTutorial, "Tutorial - Visão Geral", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void carregarTempoMedio(int idUsuario) {
        String sql = "SELECT Tempo_Medio FROM v_tempo_medio WHERE id_usuario = ?";

        try (Connection conexao = ModuloDbConecta.connector(); PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double tempoMedio = rs.getDouble("Tempo_Medio");
                    int totalMinutos = (int) Math.round(tempoMedio);
                    int h = totalMinutos / 60;
                    int m = totalMinutos % 60;
                    lblTempoMedioGeral.setText(String.format("%dh %02dm", h, m));
                } else {
                    lblTempoMedioGeral.setText("0h 00m");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tempo médio: " + e.getMessage());
        }
    }
    
    private void carregarTempoTotal(int idUsuario) {
        String sql = "SELECT Tempo_Total FROM v_tempo_total WHERE id_usuario = ?";

        try (Connection conexao = ModuloDbConecta.connector(); PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int tempoTotal = rs.getInt("Tempo_Total");
                    int h = tempoTotal / 60;
                    int m = tempoTotal % 60;
                    lblTempoTotalGeral.setText(String.format("%dh %02dm", h, m));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tempo total: " + e.getMessage());
        }
    }
    
    private void carregarTarefaRecordista(int idUsuario) {
        String sql = "SELECT Tarefa FROM v_tempo_tarefa WHERE id_usuario = ? ORDER BY Tempo_Total DESC LIMIT 1";

        try (Connection conexao = ModuloDbConecta.connector(); PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    lblTarefaMTempo.setText(rs.getString("Tarefa"));
                } else {
                    lblTarefaMTempo.setText("Nenhuma atividade");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tarefa recordista: " + e.getMessage());
        }
    }
    
    private void carregarTabelaHistorico(int idUsuario) {
        DefaultTableModel modelo = (DefaultTableModel) tblHistorico.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT F_Topico, Tarefa, Repetida, Tempo_Total_Min, Media_Min FROM v_visao_historico WHERE id_usuario = ? ORDER BY F_Topico ASC, Tarefa ASC";

        try (Connection conexao = ModuloDbConecta.connector(); PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String top = rs.getString("F_Topico");
                    String tar = rs.getString("Tarefa");
                    int rep = rs.getInt("Repetida");
                    int tempoMin = rs.getInt("Tempo_Total_Min");
                    double media = rs.getDouble("Media_Min");

                    String tempoFormatado = String.format("%dh %02dm", tempoMin / 60, tempoMin % 60);
                    modelo.addRow(new Object[]{top, tar, rep, tempoFormatado, String.format("%.1f min", media)});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados: " + e.getMessage());
        }
    }
    
    private void carregarGrafico(int idUsuario) {
        pnlGrafico.removeAll();

        String sql = "SELECT Tarefa, Tempo_Total FROM v_tempo_tarefa WHERE id_usuario = ? ORDER BY Tempo_Total DESC LIMIT 5";

        try (Connection conexao = ModuloDbConecta.connector(); PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {

                java.util.List<Object[]> dadosGrafico = new java.util.ArrayList<>();
                int tempoMaximo = 0;

                while (rs.next()) {
                    String nome = rs.getString("Tarefa");
                    int tempo = rs.getInt("Tempo_Total");
                    if (dadosGrafico.isEmpty()) {
                        tempoMaximo = tempo;
                    }
                    dadosGrafico.add(new Object[]{nome, tempo});
                }

                for (Object[] dado : dadosGrafico) {
                    String nome = (String) dado[0];
                    int tempo = (int) dado[1];
                    double proporcao = tempoMaximo > 0 ? (double) tempo / tempoMaximo : 0;

                    Modelos.ComponenteBarraGrafico barra = new Modelos.ComponenteBarraGrafico(nome, tempo, proporcao);
                    pnlGrafico.add(barra);
                }
            }

            pnlGrafico.revalidate();
            pnlGrafico.repaint();

        } catch (Exception e) {
            System.err.println("Erro ao carregar gráfico: " + e.getMessage());
        }
    }

    
    public TelaVisaoGeral() {
        initComponents();
        lblMensagem.setText("Dica: Pressione Ctrl + T para entender como os gráficos e o Tempo Médio são calculados.");
    
        pnlGrafico.setMinimumSize(new java.awt.Dimension(275, 250));
        pnlGrafico.setPreferredSize(new java.awt.Dimension(275, 350));

        this.pack();
        this.revalidate();
        this.repaint();

        atualizarDashboard();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTempoTotal = new javax.swing.JPanel();
        lblTituloTempo = new javax.swing.JLabel();
        lblTempoTotalGeral = new javax.swing.JLabel();
        pnlRecordista = new javax.swing.JPanel();
        lblTituloRecordista = new javax.swing.JLabel();
        lblTarefaMTempo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorico = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        pnlGrafico = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlTempoMedio = new javax.swing.JPanel();
        lblTituloMedio = new javax.swing.JLabel();
        lblTempoMedioGeral = new javax.swing.JLabel();
        lblMensagem = new javax.swing.JLabel();

        pnlTempoTotal.setBackground(new java.awt.Color(0, 51, 255));

        lblTituloTempo.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloTempo.setText("Tempo Total das Tarefas");

        lblTempoTotalGeral.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTempoTotalGeral.setForeground(new java.awt.Color(255, 255, 255));
        lblTempoTotalGeral.setText("0h 00m");

        javax.swing.GroupLayout pnlTempoTotalLayout = new javax.swing.GroupLayout(pnlTempoTotal);
        pnlTempoTotal.setLayout(pnlTempoTotalLayout);
        pnlTempoTotalLayout.setHorizontalGroup(
            pnlTempoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTempoTotalLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(pnlTempoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTempoTotalLayout.createSequentialGroup()
                        .addComponent(lblTempoTotalGeral)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTempoTotalLayout.createSequentialGroup()
                        .addComponent(lblTituloTempo)
                        .addGap(27, 27, 27))))
        );
        pnlTempoTotalLayout.setVerticalGroup(
            pnlTempoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTempoTotalLayout.createSequentialGroup()
                .addComponent(lblTituloTempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTempoTotalGeral)
                .addContainerGap())
        );

        pnlRecordista.setBackground(new java.awt.Color(255, 102, 0));

        lblTituloRecordista.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloRecordista.setText("Tarefa com mais Tempo");

        lblTarefaMTempo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTarefaMTempo.setForeground(new java.awt.Color(255, 255, 255));
        lblTarefaMTempo.setText("nmTarefa");

        javax.swing.GroupLayout pnlRecordistaLayout = new javax.swing.GroupLayout(pnlRecordista);
        pnlRecordista.setLayout(pnlRecordistaLayout);
        pnlRecordistaLayout.setHorizontalGroup(
            pnlRecordistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecordistaLayout.createSequentialGroup()
                .addGroup(pnlRecordistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRecordistaLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(lblTarefaMTempo))
                    .addGroup(pnlRecordistaLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(lblTituloRecordista)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        pnlRecordistaLayout.setVerticalGroup(
            pnlRecordistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecordistaLayout.createSequentialGroup()
                .addComponent(lblTituloRecordista, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTarefaMTempo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Topico", "Tarefa", "Ciclos", "Tempo Total", "Media Ciclos"
            }
        ));
        jScrollPane1.setViewportView(tblHistorico);

        btnVoltar.setBackground(new java.awt.Color(204, 204, 255));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        pnlGrafico.setBackground(new java.awt.Color(153, 153, 153));
        pnlGrafico.setLayout(new javax.swing.BoxLayout(pnlGrafico, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tabela Historico das Tarefas");

        pnlTempoMedio.setBackground(new java.awt.Color(0, 51, 255));

        lblTituloMedio.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloMedio.setText("Tempo Médio das Tarefas");

        lblTempoMedioGeral.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTempoMedioGeral.setForeground(new java.awt.Color(255, 255, 255));
        lblTempoMedioGeral.setText("0h 00m");

        javax.swing.GroupLayout pnlTempoMedioLayout = new javax.swing.GroupLayout(pnlTempoMedio);
        pnlTempoMedio.setLayout(pnlTempoMedioLayout);
        pnlTempoMedioLayout.setHorizontalGroup(
            pnlTempoMedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTempoMedioLayout.createSequentialGroup()
                .addGroup(pnlTempoMedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTempoMedioLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblTituloMedio))
                    .addGroup(pnlTempoMedioLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(lblTempoMedioGeral)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlTempoMedioLayout.setVerticalGroup(
            pnlTempoMedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTempoMedioLayout.createSequentialGroup()
                .addComponent(lblTituloMedio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTempoMedioGeral)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMensagem.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblMensagem.setText("Tutorial Mensagem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlTempoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlRecordista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlTempoMedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMensagem)
                            .addComponent(pnlGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTempoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRecordista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTempoMedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMensagem)
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblTarefaMTempo;
    private javax.swing.JLabel lblTempoMedioGeral;
    private javax.swing.JLabel lblTempoTotalGeral;
    private javax.swing.JLabel lblTituloMedio;
    private javax.swing.JLabel lblTituloRecordista;
    private javax.swing.JLabel lblTituloTempo;
    private javax.swing.JPanel pnlGrafico;
    private javax.swing.JPanel pnlRecordista;
    private javax.swing.JPanel pnlTempoMedio;
    private javax.swing.JPanel pnlTempoTotal;
    private javax.swing.JTable tblHistorico;
    // End of variables declaration//GEN-END:variables

}
