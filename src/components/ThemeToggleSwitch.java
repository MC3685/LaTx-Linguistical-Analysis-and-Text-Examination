package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemeToggleSwitch extends JComponent {

    private boolean enabled = false;

    private float anim = 0f;
    private boolean hovering = false;

    public interface ToggleListener {
        void onToggle(boolean isLightMode);
    }

    private ToggleListener listener;

    public ThemeToggleSwitch() {

        setPreferredSize(new Dimension(70, 30));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                enabled = !enabled;

                if (listener != null) {
                    listener.onToggle(enabled);
                }

                animate();
            }
        });

        animate();
    }

    public void setToggleListener(ToggleListener l) {
        this.listener = l;
    }

    private void animate() {

        new Timer(15, e -> {

            float target = enabled ? 1f : 0f;

            anim += (target - anim) * 0.2f;

            repaint();

            if (Math.abs(anim - target) < 0.01f) {
                anim = target;
                ((Timer) e.getSource()).stop();
            }

        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // track
        Color trackOff = new Color(60, 60, 80);
        Color trackOn = new Color(90, 70, 200);

        Color track = blend(trackOff, trackOn, anim);

        g2.setColor(track);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // knob
        int knobSize = getHeight() - 6;

        int x = (int) (3 + anim * (getWidth() - knobSize - 6));

        g2.setColor(Color.WHITE);
        g2.fillOval(x, 3, knobSize, knobSize);

        // glow on hover
        if (hovering) {
            g2.setColor(new Color(120, 80, 255, 60));
            g2.fillRoundRect(-4, -4, getWidth() + 8, getHeight() + 8, 40, 40);
        }

        g2.dispose();
    }

    private Color blend(Color c1, Color c2, float t) {

        return new Color(
                (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t),
                (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t),
                (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t)
        );
    }

    public boolean isLightMode() {
        return enabled;
    }
}