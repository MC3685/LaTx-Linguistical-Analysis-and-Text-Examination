package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlowEffect {

    private final JComponent component;

    private float alpha = 0.0f;

    private boolean hovering = false;

    public GlowEffect(JComponent component) {
        this.component = component;
        install();
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
}