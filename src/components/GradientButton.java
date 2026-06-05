package components;

import app.Theme;
import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton {

    public GradientButton(String text) {

        super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        GradientPaint paint = new GradientPaint(0, 0, Theme.PURPLE, getWidth(), 0, Theme.BLUE);

        g2.setPaint(paint);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);

        g2.dispose();
    }
}