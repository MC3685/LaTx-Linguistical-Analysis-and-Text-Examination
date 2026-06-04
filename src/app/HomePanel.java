package app;

import components.TitleGradient;
import analysis.AnalysisEngine;
import analysis.AnalysisResult;
import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomePanel extends JPanel {

    private JPanel panel;

    private JTextArea area;

    private JTextArea textA;
    private JTextArea textB;

    private JScrollPane scrollA;
    private JScrollPane scrollB;

    private JButton importA;
    private JButton importB;

    private JButton analyzeButton;

    private JPanel cardA;
    private JPanel cardB;

    private JLabel subtitle;
    private JLabel lbl;

    private TitleGradient title;

    private final MainFrame frame;

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    public HomePanel(MainFrame frame) {

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

    public void applyTheme()
    {
        setBackground(Theme.BACKGROUND);
        subtitle.setForeground(Color.BLACK);
        textA.setBackground(Theme.CARD);
        textA.setForeground(Theme.TEXT);
        textA.setCaretColor(Theme.TEXT);

        textB.setBackground(Theme.CARD);
        textB.setForeground(Theme.TEXT);
        textB.setCaretColor(Theme.TEXT);

        cardA.setBackground(Theme.CARD);
        cardB.setBackground(Theme.CARD);

    }


    private void buildUI() {

        title = new TitleGradient(
                "LINGUISTIC ANALYSIS AND TEXT EXAMINATION");

        title.setForeground(Theme.PURPLE);
        title.setFont(Architype.deriveFont(40f));

        add(title);

        subtitle = new JLabel(
                "DISCOVER THE AUTHOR BEHIND THE WORDS",
                SwingConstants.CENTER);

        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

        add(subtitle);

        cardA = createTextCard("TEXT A");
        cardB = createTextCard("TEXT B");

        add(cardA);
        add(cardB);

        textA = createTextArea();
        textB = createTextArea();

        scrollA = new JScrollPane(textA);
        scrollB = new JScrollPane(textB);

        cardA.add(scrollA);
        cardB.add(scrollB);

        importA = new JButton("ADD FILE");
        importB = new JButton("ADD FILE");

        cardA.add(importA);
        cardB.add(importB);


        analyzeButton = new GradientButton("RUN ANALYSIS");
        analyzeButton.setFont(HKModular.deriveFont(20f));

        analyzeButton.addActionListener(e -> {

            AnalysisEngine engine =
                    new AnalysisEngine();

            AnalysisResult result = engine.analyze(textA.getText(), textB.getText());

            frame.getResultsPanel().updateResults(result);

            frame.showResults();
        });

        add(analyzeButton);
    }

    private void updateLayout() {

        int w = getWidth();
        int h = getHeight();

        int topMargin = 40;

        title.setBounds(0, topMargin, w, 50);

        subtitle.setBounds(0, topMargin + 55, w, 30);

        int gap = Math.max(20, w / 50);

        int cardWidth = Math.min(500, (w - 300 - gap) / 2);

        int cardHeight = Math.max(350, h - 340);

        int cardY = 160;

        int totalWidth = cardWidth * 2 + gap;

        int startX = (w - totalWidth) / 2;

        cardA.setBounds(startX, cardY, cardWidth, cardHeight);

        cardB.setBounds(startX + cardWidth + gap, cardY, cardWidth, cardHeight);

        int scrollMargin = 25;

        scrollA.setBounds(scrollMargin, 30, cardWidth - 50, cardHeight - 170);

        scrollB.setBounds(scrollMargin, 30, cardWidth - 50, cardHeight - 170);

        importA.setBounds(cardWidth / 2 - 125, cardHeight - 110, 250, 45);

        importB.setBounds(cardWidth / 2 - 125, cardHeight - 110, 250, 45);


        analyzeButton.setBounds(w / 2 - 150, h - 80, 300, 50);
    }

    private JPanel createTextCard(String label) {

        panel = new JPanel(null);

        panel.setBackground(Theme.CARD);

        panel.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 2));

        lbl = new JLabel(label);

        lbl.setForeground(Theme.SUBTEXT);

        lbl.setHorizontalAlignment(
                SwingConstants.CENTER);

        lbl.setBounds(0, 0, 200, 25);

        panel.add(lbl);

        return panel;
    }

    private JTextArea createTextArea() {

        area = new JTextArea();

        area.setBackground(Theme.BACKGROUND);

        area.setForeground(Theme.TEXT);

        area.setCaretColor(Theme.TEXT);


        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        return area;
    }
}