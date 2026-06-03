package app;

import components.*;
import analysis.AnalysisResult;


import javax.swing.*;
import java.awt.*;

public class ResultsPanel extends JPanel {

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    private CircularScorePanel scorePanel;
    private JLabel title;

    public ResultsPanel() {

        setBackground(Theme.BACKGROUND);

        setLayout(null);

        buildDashboard();
    }

    public void applyTheme()
    {
        setBackground(Theme.BACKGROUND);
        title.setForeground(Theme.TEXT);

    }

    public void updateResults(
            AnalysisResult result) {

        scorePanel.setScore(
                result.getSimilarity());
    }

    private void buildDashboard() {

        title = new JLabel("ANALYSIS RESULTS");

        title.setFont(new Font("Architype", Font.BOLD, 34));

        title.setForeground(Theme.TEXT);

        title.setBounds(40, 20, 600, 50);

        add(title);

        GlassCard chartCard = new GlassCard();

        chartCard.setBounds(140, 130, 520, 260);

        chartCard.add(new JLabel("Sentence Length Distribution"));

        add(chartCard);

        GlassCard metrics = new GlassCard();

        metrics.setBounds(
                700, 130, 400, 260);

        metrics.setLayout(
                new GridLayout(6, 1, 5, 5));

        metrics.add(
                new MetricBar("Function Words", 93, Theme.PURPLE));

        metrics.add(
                new MetricBar("Sentence Structure", 85, Theme.BLUE));

        metrics.add(
                new MetricBar("Vocabulary", 78, Theme.BLUE));

        metrics.add(
                new MetricBar("Punctuation", 90, Theme.SUCCESS));

        metrics.add(
                new MetricBar("Rhythm", 82, Theme.SUCCESS));

        metrics.add(
                new MetricBar("Common Phrases", 76, Color.YELLOW));

        add(metrics);

        GlassCard score = new GlassCard();

        score.setBounds(140, 420, 320, 300);

        score.setLayout(new BorderLayout());


        scorePanel =
                new CircularScorePanel();

        score.add(scorePanel);

        add(score);

        GlassCard words = new GlassCard();

        words.setBounds(500, 420, 600, 300);

        JTable table =
                new JTable(
                    new Object[][]{{"however",28,26,"96%"}, {"therefore",23,22,"95%"}, {"furthermore",19,18,"95%"}, {"significant",17,16,"94%"}},
                        new Object[]{"Word","Text A", "Text B", "Similarity"});

        words.add(new JScrollPane(table));

        add(words);

        GlassCard conclusion = new GlassCard();

        conclusion.setBounds(140, 760, 960, 90);

        conclusion.add(
                new JLabel("Conclusion: High probability of common authorship."));

        add(conclusion);
    }
}