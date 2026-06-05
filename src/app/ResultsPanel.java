package app;

import analysis.AnalysisResult;
import components.*;

import javax.swing.*;
import java.awt.*;

public class ResultsPanel extends JPanel {

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    // ===== UI ROOT =====
    private final JPanel contentPanel;

    // ===== Dynamic components =====
    private CircularScorePanel scorePanel;
    private JLabel title;

    private JLabel conclusionLabel;

    private JTable wordTable;

    public ResultsPanel() {

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        // =========================
        // Scrollable container
        // =========================
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Theme.BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);

        add(scrollPane, BorderLayout.CENTER);

        buildUI();
    }

    // =========================================================
    // PUBLIC API
    // =========================================================

    public void updateResults(AnalysisResult result) {

        if (scorePanel != null) {
            scorePanel.setScore(result.getSimilarity());
        }

        if (wordTable != null) {
            updateWordTable(result);
        }

        if (conclusionLabel != null) {
            conclusionLabel.setText(
                    "Conclusion: " + result.getConclusion()
            );
        }

        revalidate();
        repaint();
    }

    public void applyTheme() {

        setBackground(Theme.BACKGROUND);
        contentPanel.setBackground(Theme.BACKGROUND);

        if (title != null) {
            title.setForeground(Theme.TEXT);
        }
    }

    // =========================================================
    // UI BUILD
    // =========================================================

    private void buildUI() {

        contentPanel.add(buildTitlePane());
        contentPanel.add(buildScorePane());
        contentPanel.add(buildMetricsPane());
        contentPanel.add(buildChartPane());
        contentPanel.add(buildWordPane());
        contentPanel.add(buildConclusionPane());

        contentPanel.add(Box.createVerticalGlue());
    }

    // =========================================================
    // PANES
    // =========================================================

    private JComponent buildTitlePane() {

        title = new TitleGradient("ANALYSIS RESULTS");
        title.setFont(HKModular.deriveFont(34f));
        title.setForeground(Theme.TEXT);

        return wrap(title);
    }

    private JComponent buildScorePane() {

        GlassCard card = new GlassCard();
        card.setLayout(new BorderLayout());

        scorePanel = new CircularScorePanel();

        card.add(scorePanel, BorderLayout.CENTER);

        return wrap(card);
    }

    private JComponent buildMetricsPane() {

        GlassCard card = new GlassCard();
        card.setLayout(new GridLayout(6, 1, 5, 5));

        // placeholder values (can be replaced in updateResults later)
        card.add(new MetricBar("Function Words", 0, Theme.PURPLE));
        card.add(new MetricBar("Sentence Structure", 0, Theme.BLUE));
        card.add(new MetricBar("Vocabulary", 0, Theme.BLUE));
        card.add(new MetricBar("Punctuation", 0, Theme.SUCCESS));
        card.add(new MetricBar("Rhythm", 0, Theme.SUCCESS));
        card.add(new MetricBar("Common Phrases", 0, Color.YELLOW));

        return wrap(card);
    }

    private JComponent buildChartPane() {

        GlassCard card = new GlassCard();
        card.add(new JLabel("Sentence Length Distribution"));

        return wrap(card);
    }

    private JComponent buildWordPane() {

        GlassCard card = new GlassCard();

        wordTable = new JTable(
                new Object[][]{
                        {"-", "-", "-", "-"}
                },
                new Object[]{"Word", "Text A", "Text B", "Similarity"}
        );

        card.add(new JScrollPane(wordTable));

        return wrap(card);
    }

    private JComponent buildConclusionPane() {

        GlassCard card = new GlassCard();

        conclusionLabel = new JLabel("Conclusion: Awaiting analysis...");
        conclusionLabel.setForeground(Theme.TEXT);

        card.add(conclusionLabel);

        return wrap(card);
    }

    // =========================================================
    // UPDATE HELPERS
    // =========================================================

    private void updateWordTable(AnalysisResult result) {

        Object[][] data = result.getTopWords(); // assumed structure

        wordTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new Object[]{"Word", "Text A", "Text B", "Similarity"}
        ));
    }

    // =========================================================
    // UTIL
    // =========================================================

    private JPanel wrap(JComponent comp) {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BACKGROUND);

        wrapper.add(comp, BorderLayout.CENTER);

        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return wrapper;
    }
}