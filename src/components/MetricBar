package components;

import app.Theme;
import javax.swing.*;
import java.awt.*;

public class MetricBar extends JPanel {

    private String metric;
    private int value;
    private Color color;

    public MetricBar(
            String metric,
            int value,
            Color color) {

        this.metric = metric;
        this.value = value;
        this.color = color;

        setPreferredSize(
                new Dimension(300, 35));

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setColor(
                Theme.TEXT);

        g2.drawString(
                metric,
                10,
                20);

        g2.setColor(
                new Color(
                        50,
                        60,
                        90));

        g2.fillRoundRect(
                130,
                8,
                140,
                12,
                12,
                12);

        g2.setColor(color);

        g2.fillRoundRect(
                130,
                8,
                value,
                12,
                12,
                12);

        g2.drawString(
                value+"%",
                280,
                20);
    }
}