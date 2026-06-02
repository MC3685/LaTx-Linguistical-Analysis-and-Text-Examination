package components;

import app.Theme;

import javax.swing.*;
import java.awt.*;

public class GlassCard extends JPanel {

    public GlassCard() {

        setOpaque(false);
        setLayout(new BorderLayout(10,10));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Theme.CARD);

        g2.fillRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                30,
                30);

        g2.setColor(Theme.BORDER);

        g2.drawRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                30,
                30);

        g2.dispose();

        super.paintComponent(g);
    }
}