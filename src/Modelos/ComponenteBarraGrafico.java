package Modelos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class ComponenteBarraGrafico extends JComponent {
    
    private String nomeTarefa;
    private int tempoMinutos;
    private double porcentagem;

    public ComponenteBarraGrafico(String nomeTarefa, int tempoMinutos, double porcentagem) {
        
        this.nomeTarefa = (nomeTarefa != null) ? nomeTarefa : "Tarefa sem nome";
        this.tempoMinutos = tempoMinutos;
        this.porcentagem = Math.max(0.0, Math.min(1.0, porcentagem));

        setMinimumSize(new Dimension(150, 45));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setPreferredSize(new Dimension(350, 45));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int larguraTotal = getWidth();
        int espacoTextoTempo = 80;

        int larguraBarraMaxima = larguraTotal - espacoTextoTempo - 20;
        if (larguraBarraMaxima < 10) {
            larguraBarraMaxima = 10;
        }

        int larguraBarraAtual = (int) (larguraBarraMaxima * porcentagem);
        if (larguraBarraAtual < 5) {
            larguraBarraAtual = 5; 
        }

        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.setColor(new Color(50, 50, 50));

        FontMetrics fm = g2.getFontMetrics();
        String nomeExibicao = nomeTarefa;

        if (fm.stringWidth(nomeExibicao) > larguraBarraMaxima) {
            while (fm.stringWidth(nomeExibicao + "...") > larguraBarraMaxima && nomeExibicao.length() > 1) {
                nomeExibicao = nomeExibicao.substring(0, nomeExibicao.length() - 1);
            }
            nomeExibicao += "...";
        }
        g2.drawString(nomeExibicao, 10, 18);

        g2.setColor(new Color(230, 235, 240));
        g2.fillRoundRect(10, 24, larguraBarraMaxima, 12, 8, 8);

        g2.setColor(new Color(43, 87, 154));
        g2.fillRoundRect(10, 24, larguraBarraAtual, 12, 8, 8);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2.setColor(new Color(100, 100, 100));

        int horas = tempoMinutos / 60;
        int minutos = tempoMinutos % 60;
        String textoTempo = horas > 0 ? horas + "h " + minutos + "m" : minutos + "m";

        g2.drawString(textoTempo, 15 + larguraBarraMaxima, 35);
    }
}
