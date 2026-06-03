package components;

import javax.swing.JToggleButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

public class RoundedToggle extends JToggleButton {
    private int cornerRadius;

    public RoundedToggle(String text, int radius) {
        super(text);
        this.cornerRadius = radius;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isSelected()) {
            g2.setColor(new Color(60, 63, 65)); // Dark theme color
        } else {
            g2.setColor(new Color(230, 230, 230)); // Light theme color
        }

        // Paint the rounded background rectangle
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();

        // Let Swing paint the text/foreground on top
        super.paintComponent(g);
    }
}