package components;

import app.Theme;
import javax.swing.*;
import java.awt.*;

public class CircularScorePanel extends JPanel {

    private double score = 1;

    public CircularScorePanel() {

        setOpaque(false);
    }

    public void setScore(double score) {

        this.score = score;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 40;

        int x = (getWidth()-size)/2;
        int y = (getHeight()-size)/2;

        g2.setStroke(new BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(50,50,70));
        g2.drawArc(x, y, size, size, 0, 360);
        GradientPaint gp = new GradientPaint(x, y, Theme.PURPLE, x+size, y+size, Theme.BLUE);
        g2.setPaint(gp);

        int angle = (int)(360*(score/100));

        g2.drawArc(x, y, size, size, 90, -angle);
        String txt = (int)score + "%";

        g2.setColor(Theme.TEXT);
        g2.setFont(new Font("Arial", Font.BOLD, 42));

        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(txt, getWidth()/2-fm.stringWidth(txt)/2, getHeight()/2+15);
    }
}