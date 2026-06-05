package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlowEffect {

    private final JComponent component;

    private Color glowColor = new Color(120, 80, 255);
    private int glowSize = 12;
    private float alpha = 0.0f;

    private boolean hovering = false;

    public GlowEffect(JComponent component) {
        this.component = component;
        install();
    }

    public GlowEffect setGlowColor(Color color) {
        this.glowColor = color;
        return this;
    }

    public GlowEffect setGlowSize(int size) {
        this.glowSize = size;
        return this;
    }

    private void install() {

        component.setOpaque(false);

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                animateIn();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                animateOut();
            }
        });
    }

    private void animateIn() {

        new Timer(15, e -> {

            if (!hovering) {
                ((Timer) e.getSource()).stop();
                return;
            }

            alpha = Math.min(0.35f, alpha + 0.05f);
            component.repaint();

        }).start();
    }

    private void animateOut() {

        new Timer(15, e -> {

            if (hovering) {
                ((Timer) e.getSource()).stop();
                return;
            }

            alpha = Math.max(0.0f, alpha - 0.05f);
            component.repaint();

        }).start();
    }

    public void paintGlow(Graphics2D g, JComponent c) {

        if (alpha <= 0f) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(
                glowColor.getRed(),
                glowColor.getGreen(),
                glowColor.getBlue(),
                (int) (alpha * 255)
        ));

        int inset = glowSize;
        g2.fillRoundRect(
                -inset,
                -inset,
                c.getWidth() + inset * 2,
                c.getHeight() + inset * 2,
                30,
                30
        );

        g2.dispose();
    }
}