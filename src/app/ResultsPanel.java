package app;

import analysis.AnalysisResult;
import app.widgets.AnalysisWidget;
import components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultsPanel extends JPanel {

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    // ===== Core containers =====
    private final JPanel contentPanel;
    private final JScrollPane scrollPane;

    // ===== Widget registry =====
    private final List<AnalysisWidget> widgets = new ArrayList<>();

    // ===== Header =====
    private JLabel title;

    public ResultsPanel() {

        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        // =========================================================
        // Scrollable content container
        // =========================================================
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Theme.BACKGROUND);

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);

        add(scrollPane, BorderLayout.CENTER);

        buildDashboard();
    }

    // =============================================================
    // PUBLIC API
    // =============================================================

    public void updateResults(AnalysisResult result) {

        for (AnalysisWidget widget : widgets) {
            widget.update(result);
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

        repaint();
    }

    // =============================================================
    // DASHBOARD CONSTRUCTION
    // =============================================================

    private void buildDashboard() {

        // ===== Title =====
        title = new TitleGradient("ANALYSIS RESULTS");
        title.setFont(HKModular.deriveFont(34f));
        title.setForeground(Theme.TEXT);

        contentPanel.add(pad(title));

        // =========================================================
        // Register widgets (order = UI order)
        // =========================================================

        widgets.add(new SentenceDistributionWidget());
        widgets.add(new MetricsWidget());
        widgets.add(new ScoreWidget());
        widgets.add(new WordTableWidget());
        widgets.add(new ConclusionWidget());

        // =========================================================
        // Add widgets to UI + set loading state
        // =========================================================

        for (AnalysisWidget widget : widgets) {

            JComponent comp = widget.getComponent();

            comp.setAlignmentX(Component.LEFT_ALIGNMENT);
            comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, comp.getPreferredSize().height));

            contentPanel.add(pad(comp));

            widget.setPlaceholderState();
        }

        // spacer at bottom (prevents tight clipping)
        contentPanel.add(Box.createVerticalGlue());
    }

    // =============================================================
    // UI HELPERS
    // =============================================================

    private JPanel pad(JComponent component) {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BACKGROUND);
        wrapper.add(component, BorderLayout.CENTER);

        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return wrapper;
    }
}