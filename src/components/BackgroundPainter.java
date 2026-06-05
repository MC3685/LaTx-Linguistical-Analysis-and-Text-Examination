package components;

import app.Theme;

import java.awt.*;

public class BackgroundPainter {

    public static void paintBackground(Graphics g, Component c) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = c.getWidth();
        int h = c.getHeight();

        GradientPaint base = new GradientPaint(
                0, 0, Theme.BACKGROUND_GRADIENT_TOP,
                w, h, Theme.BACKGROUND_GRADIENT_BOTTOM
        );

        g2.setPaint(base);
        g2.fillRect(0, 0, w, h);

        paintOrb(g2, (int) (w * 0.15), (int) (h * 0.18), (int) (w * 0.55), Theme.ORB_PURPLE);

        paintOrb(g2, (int) (w * 0.75), (int) (h * 0.70), (int) (w * 0.50), Theme.ORB_CYAN);

        paintOrb(g2, (int) (w * 0.50), (int) (h * 0.40), (int) (w * 0.35), Theme.ORB_MAGENTA);



        g2.dispose();
    }

    private static void paintOrb(Graphics2D g2, int cx, int cy, int radius, Color color) {

        RadialGradientPaint orb = new RadialGradientPaint(new Point(cx, cy), radius, new float[]{0f, 1f},
                new Color[]{
                        color,
                        new Color(0, 0, 0, 0)
                }
        );

        g2.setPaint(orb);
        g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }
}

