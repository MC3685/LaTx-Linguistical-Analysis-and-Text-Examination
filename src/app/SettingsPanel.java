package app;

import components.*;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class SettingsPanel extends JPanel {


    private TitleGradient title;
    private JLabel subtitle;

    private JPanel settingsCard;

    private JLabel modeLabelLeft;
    private JLabel modeLabelRight;

    private JToggleButton themeToggle;

    private JButton exitButton;
    private JLabel footer;
    private MainFrame frame;

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    public SettingsPanel(MainFrame frame) {

        this.frame = frame;
        setBackground(Theme.BACKGROUND);
        setLayout(null);

        buildUI();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        SwingUtilities.invokeLater(this::updateLayout);
    }

    public void applyTheme() {

        setBackground(Theme.BACKGROUND);

        settingsCard.setBackground(Theme.CARD);

        modeLabelLeft.setForeground(Theme.TEXT);
        modeLabelRight.setForeground(Theme.TEXT);

        footer.setForeground(Theme.SUBTEXT);

        subtitle.setForeground(Theme.TEXT);

        title.setForeground(Theme.PURPLE);



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BackgroundPainter.paintBackground(g, this);
    }

    private static class RoundedGlowBorder extends AbstractBorder {

        private final int radius;
        private final Color inner, outer;

        RoundedGlowBorder(int radius, Color inner, Color outer) {
            this.radius = radius;
            this.inner = inner;
            this.outer = outer;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setStroke(new BasicStroke(3f));
            g2.setColor(outer);
            g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, radius + 2, radius + 2));

            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(inner);
            g2.draw(new RoundRectangle2D.Float(x + 2, y + 2, w - 4, h - 4, radius, radius));

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
    }

    private void buildUI() {

        title = new TitleGradient("LINGUISTIC ANALYSIS AND TEXT EXAMINATION");
        title.setFont(Architype.deriveFont(40f));

        add(title);

        subtitle = new JLabel("SETTINGS", SwingConstants.CENTER);
        subtitle.setForeground(Theme.TEXT);
        subtitle.setFont(HKModular.deriveFont(18f));

        add(subtitle);

        settingsCard = new JPanel(null) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Theme.GLASS_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));

                GradientPaint shine = new GradientPaint(0, 0, Theme.GLASS_HIGHLIGHT, 0, getHeight() / 3f, new Color(0, 0, 0, 0));

                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 3f, 20, 20));

                g2.dispose();
            }
        };

        settingsCard.setOpaque(false);
        settingsCard.setBackground(Theme.CARD);
        settingsCard.setBorder(new SettingsPanel.RoundedGlowBorder(20, Theme.ACCENT_PURPLE_SOFT, Theme.ACCENT_BLUE_SOFT));
        add(settingsCard);

        modeLabelLeft = new JLabel("DARK MODE");
        modeLabelLeft.setForeground(Theme.TEXT);
        modeLabelLeft.setFont(HKModular.deriveFont(17f));
        settingsCard.add(modeLabelLeft);

        modeLabelRight = new JLabel("LIGHT MODE");
        modeLabelRight.setForeground(Theme.TEXT);
        modeLabelRight.setFont(HKModular.deriveFont(17f));
        settingsCard.add(modeLabelRight);


        themeToggle = new JToggleButton("Dark") {
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();


                GradientPaint paint = new GradientPaint(
                        0, 0, Theme.PURPLE,
                        getWidth(), 0, Theme.BLUE);

                g2.setPaint(paint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g);

                g2.dispose();
            }};
        //themeToggle.setFocusPainted(false);
        themeToggle.setBorderPainted(false);
        themeToggle.setFont(Architype.deriveFont(10f));
        themeToggle.setBackground(Theme.BACKGROUND);

        themeToggle.addActionListener(e -> {

            boolean light = themeToggle.isSelected();

            if (light) {
                Theme.setLightTheme();
                themeToggle.setText("Light");

            } else
            {
                Theme.setDarkTheme();
                themeToggle.setText("Dark");

            }

            frame.refreshTheme();
        });

        settingsCard.add(themeToggle);

        exitButton = new GradientButton("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(HKModular.deriveFont(28f));
        exitButton.setBorderPainted(false);


        exitButton.addActionListener(e -> System.exit(0));

        settingsCard.add(exitButton);

        footer = new JLabel("CLICK TO EXIT THE APPLICATION  •  RATE US WHEN YOU GO!", SwingConstants.CENTER);
        footer.setForeground(Theme.SUBTEXT);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        add(footer);
    }

    private void updateLayout() {

        int w = getWidth();
        int h = getHeight();

        int topMargin = 40;

        title.setBounds(0, topMargin, w, 50);
        subtitle.setBounds(0, topMargin + 55, w, 30);

        int cardW = Math.min(520, w - 200);
        int cardH = 260;

        int cardX = (w - cardW) / 2;
        int cardY = 160;

        settingsCard.setBounds(cardX, cardY, cardW, cardH);

        int pad = 30;

        modeLabelLeft.setBounds(pad, 40, 150, 30);
        modeLabelRight.setBounds(cardW - 160, 40, 150, 30);

        exitButton.setBounds(cardW / 2 - 150, 110, 300, 60);

        themeToggle.setBounds(cardW / 2 - 50, 40, 100, 30);

        exitButton.setBounds(cardW / 2 - 150, 130, 300, 60); //centers the button so it doesn't look awkward

        footer.setBounds(0, cardY + cardH + 20, w, 30);
    }
}