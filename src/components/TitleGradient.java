package components;

import app.Theme;

import javax.swing.*;
import java.awt.*;

public class TitleGradient extends JLabel {

    public TitleGradient(String text) {
        super(text);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fm = g2.getFontMetrics(getFont());

        int textWidth = fm.stringWidth(getText());
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 4;

        GradientPaint gradient =
                new GradientPaint(
                        0, 0, Theme.PURPLE,
                        getWidth(), 0, Theme.BLUE);

        g2.setPaint(gradient);
        g2.setFont(getFont());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}
