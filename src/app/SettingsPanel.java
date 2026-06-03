package app;

import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SettingsPanel extends JPanel {


    private JLabel title;
    private JLabel subtitle;

    private JPanel settingsCard;

    private JLabel modeLabelLeft;
    private JLabel modeLabelRight;

    private JToggleButton themeToggle;

    private JButton exitButton;
    private JLabel footer;

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    public SettingsPanel() {

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

    private void applyTheme() {

        setBackground(Theme.BACKGROUND);

        settingsCard.setBackground(Theme.CARD);

        modeLabelLeft.setForeground(Theme.TEXT);
        modeLabelRight.setForeground(Theme.TEXT);

        footer.setForeground(Theme.SUBTEXT);

        title.setForeground(Theme.PURPLE);

        repaint();
    }

    private void buildUI() {

        title = new JLabel(
                "LINGUISTIC ANALYSIS AND TEXT EXAMINATION",
                SwingConstants.CENTER
        );
        title.setForeground(Theme.PURPLE);
        title.setFont(Architype.deriveFont(40f));
        add(title);

        subtitle = new JLabel(
                "SETTINGS",
                SwingConstants.CENTER
        );
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        add(subtitle);

        settingsCard = new JPanel(null);
        settingsCard.setBackground(Theme.CARD);
        settingsCard.setBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 2)
        );
        add(settingsCard);

        modeLabelLeft = new JLabel("DARK MODE");
        modeLabelLeft.setForeground(Theme.TEXT);
        modeLabelLeft.setFont(HKModular.deriveFont(18f));
        settingsCard.add(modeLabelLeft);

        modeLabelRight = new JLabel("LIGHT MODE");
        modeLabelRight.setForeground(Theme.TEXT);
        modeLabelRight.setFont(HKModular.deriveFont(18f));
        settingsCard.add(modeLabelRight);


        themeToggle = new JToggleButton("Dark") {
            @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            GradientPaint paint =
                    new GradientPaint(
                            0,
                            0,
                            Theme.PURPLE,
                            getWidth(),
                            0,
                            Theme.BLUE);

            g2.setPaint(paint);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    20,
                    20);

            super.paintComponent(g);

            g2.dispose();
        }};
        //themeToggle.setFocusPainted(false);
        //themeToggle.setBorderPainted(false);
        themeToggle.setFont(Architype.deriveFont(10f));
        themeToggle.setBackground(Theme.BACKGROUND);

        themeToggle.addActionListener(e -> {

            boolean light = themeToggle.isSelected();

            if (light) {
                Theme.setLightTheme();
                themeToggle.setText("Light");

            } else {
                Theme.setDarkTheme();
                themeToggle.setText("Dark");


            }

            applyTheme();
        });

        settingsCard.add(themeToggle);

        exitButton = new GradientButton("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(HKModular.deriveFont(28f));
        exitButton.setBorderPainted(false);

        exitButton.setBackground(new Color(240,240,240));

        exitButton.addActionListener(e -> { System.exit(0);});

        settingsCard.add(exitButton);

        footer = new JLabel(
                "CLICK TO EXIT THE APPLICATION  •  RATE US WHEN YOU GO!",
                SwingConstants.CENTER
        );
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

        themeToggle.setBounds(
                cardW / 2 - 35,
                40,
                70,
                30
        );

        exitButton.setBounds(
                cardW / 2 - 150,
                110,
                300,
                60
        );

        footer.setBounds(
                0,
                cardY + cardH + 20,
                w,
                30
        );
    }
}