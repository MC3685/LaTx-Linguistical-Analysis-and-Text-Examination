package app;

import javax.swing.*;
import java.awt.*;

import analysis.AnalysisEngine;
import analysis.AnalysisResult;
import components.FontLoader;


public class HomePanel extends JPanel {

    private JTextArea textA;
    private JTextArea textB;

    private JTextField titleA;
    private JTextField titleB;

    private MainFrame frame;

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);


    public HomePanel(MainFrame frame) {

        this.frame = frame;

        setBackground(Theme.BACKGROUND);

        setLayout(null);

        buildUI();
    }

    private void buildUI() {

        JLabel title = new JLabel(
                "LINGUISTIC ANALYSIS AND TEXT EXAMINATION");

        title.setBounds(300,40,900,50);

        title.setForeground(Theme.PURPLE);

        title.setFont(Architype.deriveFont(40f));



        add(title);

        JLabel subtitle = new JLabel(
                "DISCOVER THE AUTHOR BEHIND THE WORDS");

        subtitle.setBounds(400,95,600,30);

        subtitle.setForeground(Color.WHITE);

        subtitle.setFont(
                new Font("Arial",
                        Font.ITALIC,
                        20));

        add(subtitle);

        JPanel cardA = createTextCard("TEXT A");
        cardA.setBounds(220,180,420,520);

        JPanel cardB = createTextCard("TEXT B");
        cardB.setBounds(700,180,420,520);

        add(cardA);
        add(cardB);

        textA = createTextArea();
        textB = createTextArea();

        JScrollPane scrollA =
                new JScrollPane(textA);

        JScrollPane scrollB =
                new JScrollPane(textB);

        scrollA.setBounds(25,30,360,350);
        scrollB.setBounds(25,30,360,350);

        cardA.add(scrollA);
        cardB.add(scrollB);

        JButton importA =
                new JButton("ADD FILE");

        JButton importB =
                new JButton("ADD FILE");

        importA.setBounds(80,420,250,50);
        importB.setBounds(80,420,250,50);

        cardA.add(importA);
        cardB.add(importB);

        titleA = new JTextField();
        titleB = new JTextField();

        titleA.setBounds(370,750,250,45);
        titleB.setBounds(850,750,250,45);

        add(titleA);
        add(titleB);

        JButton analyze =
                new JButton("RUN ANALYSIS");

        analyze.setBounds(520,830,300,50);

        analyze.addActionListener(e -> {

            AnalysisEngine engine =
                    new AnalysisEngine();

            AnalysisResult result =
                    engine.analyze(
                            textA.getText(),
                            textB.getText());

            frame.showResults();
        });

        add(analyze);
    }

    private JPanel createTextCard(String label) {

        JPanel panel = new JPanel(null);

        panel.setBackground(Theme.CARD);

        panel.setBorder(
                BorderFactory.createLineBorder(
                        Theme.BORDER,2));

        JLabel lbl = new JLabel(label);

        lbl.setForeground(
                Theme.SUBTEXT);

        lbl.setBounds(160,0,120,25);

        panel.add(lbl);

        return panel;
    }

    private JTextArea createTextArea() {

        JTextArea area = new JTextArea();

        area.setBackground(
                Theme.BACKGROUND);

        area.setForeground(
                Theme.TEXT);

        area.setCaretColor(
                Theme.TEXT);

        area.setLineWrap(true);

        return area;
    }
}