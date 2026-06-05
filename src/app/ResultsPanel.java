/*package app;

import components.*;
import analysis.AnalysisResult;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class ResultsPanel extends JPanel {

    private static final Font HKModular = FontLoader.load("HKModular-Bold.otf", 1f);
    private static final Font Architype = FontLoader.load("Architype-Aubette.ttf", 1f);

    private static final String[] STAT_NAMES = {
            "Word Count", "Sentence Count", "Char Count",
            "Punct. Rate", "Avg Sent. Len", "Avg Word Len"
    };

    // ── kept infrastructure ───────────────────────────────────────────────────
    private CircularScorePanel scorePanel;
    private JLabel title;

    // ── column headers (span the top of all card rows) ────────────────────────
    private JLabel colHeaderA, colHeaderB;

    // ── glass section cards ───────────────────────────────────────────────────
    private JPanel statsCardA,  statsCardB;
    private JPanel wordsCardA,  wordsCardB;
    private JPanel sentCardA,   sentCardB;
    private JPanel concCardA,   concCardB;
    private JPanel scoreCard;

    // ── stats labels  [6 rows × A/B × name/value] ────────────────────────────
    private final JLabel[] statNameA = new JLabel[6];
    private final JLabel[] statValA  = new JLabel[6];
    private final JLabel[] statNameB = new JLabel[6];
    private final JLabel[] statValB  = new JLabel[6];

    // ── top-10 word labels  [10 rows × A/B × rank/text] ──────────────────────
    private final JLabel[] wordRankA = new JLabel[10];
    private final JLabel[] wordTextA = new JLabel[10];
    private final JLabel[] wordRankB = new JLabel[10];
    private final JLabel[] wordTextB = new JLabel[10];

    // ── sentiment labels  [3 rows × A/B × name/value] ────────────────────────
    private JLabel sNamePosA, sValPosA, sNameNeuA, sValNeuA, sNameNegA, sValNegA;
    private JLabel sNamePosB, sValPosB, sNameNeuB, sValNeuB, sNameNegB, sValNegB;

    // ── conclusion text ───────────────────────────────────────────────────────
    private JLabel concTextA, concTextB;

    // ─────────────────────────────────────────────────────────────────────────
    public ResultsPanel() {
        setBackground(Theme.BACKGROUND);
        setLayout(null);
        buildDashboard();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { updateLayout(); }
        });
        SwingUtilities.invokeLater(this::updateLayout);
    }

    // ── kept method signatures ────────────────────────────────────────────────
    public void applyTheme() {
        setBackground(Theme.BACKGROUND);
        title.setForeground(Theme.TITLE);
        repaint();
    }

    private JScrollPane createStyledScroll(JTextArea ta) {

        JScrollPane sp = new JScrollPane(ta);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(BorderFactory.createEmptyBorder());

        sp.getVerticalScrollBar().setOpaque(false);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        sp.getVerticalScrollBar().setUI(new HomePanel.ThinScrollBarUI(Theme.SCROLLBAR_THUMB));

        return sp;
    }

    public void updateResults(AnalysisResult result) {
        scorePanel.setScore(result.getSimilarity());
        // Wire additional AnalysisResult getters here as the API expands
        repaint();
    }

    // ── aurora gradient background (matches HomePanel) ────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setPaint(new GradientPaint(
                0, 0, Theme.BACKGROUND_GRADIENT_TOP,
                w, h, Theme.BACKGROUND_GRADIENT_BOTTOM));
        g2.fillRect(0, 0, w, h);

        // Orb positions shifted from HomePanel so the two panels feel related but distinct
        orb(g2, (int)(w * 0.85), (int)(h * 0.12), (int)(w * 0.45), Theme.ORB_PURPLE);
        orb(g2, (int)(w * 0.12), (int)(h * 0.78), (int)(w * 0.40), Theme.ORB_CYAN);
        orb(g2, (int)(w * 0.52), (int)(h * 0.48), (int)(w * 0.32), Theme.ORB_MAGENTA);

        g2.setColor(Theme.DIVIDER);
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(40, 115, w - 40, 115);

        g2.dispose();
    }

    private void orb(Graphics2D g2, int cx, int cy, int r, Color c) {
        g2.setPaint(new RadialGradientPaint(new Point(cx, cy), r,
                new float[]{0f, 1f},
                new Color[]{c, new Color(0, 0, 0, 0)}));
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    // ── dashboard construction ────────────────────────────────────────────────
    private void buildDashboard() {

        title = new TitleGradient("ANALYSIS RESULTS");
        title.setFont(f(Architype, 38f));
        title.setForeground(Theme.TITLE);
        add(title);

        // Column headers sit above every card row as persistent orientation labels
        colHeaderA = lbl("TEXT  A", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        colHeaderB = lbl("TEXT  B", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        // Letter-spacing via HTML (same trick used in HomePanel card labels)
        colHeaderA.setText("<html><body style='letter-spacing:4px'>TEXT A</body></html>");
        colHeaderB.setText("<html><body style='letter-spacing:4px'>TEXT B</body></html>");
        add(colHeaderA);
        add(colHeaderB);

        // ── Statistics ────────────────────────────────────────────────────────
        statsCardA = glassCard("STATISTICS");
        statsCardB = glassCard("STATISTICS");
        add(statsCardA); add(statsCardB);
        for (int i = 0; i < 6; i++) {
            statNameA[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT,        11f, SwingConstants.LEFT);
            statValA[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statNameB[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT,        11f, SwingConstants.LEFT);
            statValB[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statsCardA.add(statNameA[i]); statsCardA.add(statValA[i]);
            statsCardB.add(statNameB[i]); statsCardB.add(statValB[i]);
        }

        // ── Top 10 Words ──────────────────────────────────────────────────────
        wordsCardA = glassCard("TOP 10 WORDS");
        wordsCardB = glassCard("TOP 10 WORDS");
        add(wordsCardA); add(wordsCardB);
        for (int i = 0; i < 10; i++) {
            wordRankA[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 10f, SwingConstants.RIGHT);
            wordTextA[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 11f, SwingConstants.LEFT);
            wordRankB[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 10f, SwingConstants.RIGHT);
            wordTextB[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 11f, SwingConstants.LEFT);
            wordsCardA.add(wordRankA[i]); wordsCardA.add(wordTextA[i]);
            wordsCardB.add(wordRankB[i]); wordsCardB.add(wordTextB[i]);
        }

        // ── Sentiment ─────────────────────────────────────────────────────────
        sentCardA = glassCard("SENTIMENT");
        sentCardB = glassCard("SENTIMENT");
        add(sentCardA); add(sentCardB);

        sNamePosA = lbl("● Positive", Theme.SUCCESS,  11f, SwingConstants.LEFT);
        sValPosA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNeuA = lbl("● Neutral",  Theme.SUBTEXT,  11f, SwingConstants.LEFT);
        sValNeuA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNegA = lbl("● Negative", Theme.NEGATIVE, 11f, SwingConstants.LEFT);
        sValNegA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sentCardA.add(sNamePosA); sentCardA.add(sValPosA);
        sentCardA.add(sNameNeuA); sentCardA.add(sValNeuA);
        sentCardA.add(sNameNegA); sentCardA.add(sValNegA);

        sNamePosB = lbl("● Positive",Theme.SUCCESS,  11f, SwingConstants.LEFT);
        sValPosB  = lbl("—%",Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNeuB = lbl("● Neutral",Theme.SUBTEXT,  11f, SwingConstants.LEFT);
        sValNeuB  = lbl("—%",Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNegB = lbl("● Negative",Theme.NEGATIVE, 11f, SwingConstants.LEFT);
        sValNegB  = lbl("—%",Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sentCardB.add(sNamePosB); sentCardB.add(sValPosB);
        sentCardB.add(sNameNeuB); sentCardB.add(sValNeuB);
        sentCardB.add(sNameNegB); sentCardB.add(sValNegB);

        // ── Conclusion ────────────────────────────────────────────────────────
        concCardA = glassCard("CONCLUSION");
        concCardB = glassCard("CONCLUSION");
        add(concCardA); add(concCardB);

        concTextA = lbl("<html>Awaiting analysis…</html>", Theme.TEXT, 11f, SwingConstants.LEFT);
        concTextB = lbl("<html>Awaiting analysis…</html>", Theme.TEXT, 11f, SwingConstants.LEFT);
        concTextA.setVerticalAlignment(SwingConstants.TOP);
        concTextB.setVerticalAlignment(SwingConstants.TOP);
        concCardA.add(concTextA);
        concCardB.add(concTextB);

        // ── Similarity Score (full-width) ─────────────────────────────────────
        scoreCard  = glassCard("SIMILARITY SCORE");
        scorePanel = new CircularScorePanel();
        scoreCard.add(scorePanel);
        add(scoreCard);
    }

    // ── layout ────────────────────────────────────────────────────────────────
    private void updateLayout() {
        int w = getWidth(), h = getHeight();
        if (w < 100 || h < 100) return;

        // Derived metrics
        int side  = Math.max(32, w / 22);
        int gap   = Math.max(14, w / 55);
        int cardW = (w - 2 * side - gap) / 2;
        if (cardW < 80) return;

        // ── Header area ───────────────────────────────────────────────────────
        title.setBounds(0, 25, w, 52);
        colHeaderA.setBounds(side,              88, cardW, 26);
        colHeaderB.setBounds(side + cardW + gap, 88, cardW, 26);

        // ── Section heights (proportional, with minimums) ─────────────────────
        int topArea   = 128;
        int btmPad    = 28;
        int interGap  = 14;
        int avail     = Math.max(0, h - topArea - btmPad - 4 * interGap);

        int sH  = Math.max(150, (int)(avail * 0.21));             // stats
        int wH  = Math.max(210, (int)(avail * 0.27));             // words
        int seH = Math.max(140, (int)(avail * 0.18));             // sentiment
        int cH  = Math.max(130, (int)(avail * 0.18));
        int scH = Math.max(110, avail - sH - wH - seH - cH);

        // ── Card bounds ───────────────────────────────────────────────────────
        int sY  = topArea;
        int wY  = sY  + sH  + interGap;
        int seY = wY  + wH  + interGap;
        int cY  = seY + seH + interGap;
        int scY = cY  + cH  + interGap;

        pair(statsCardA, statsCardB, side, sY,  cardW, sH,  gap);
        pair(wordsCardA, wordsCardB, side, wY,  cardW, wH,  gap);
        pair(sentCardA,  sentCardB,  side, seY, cardW, seH, gap);
        pair(concCardA,  concCardB,  side, cY,  cardW, cH,  gap);
        scoreCard.setBounds(side, scY, 2 * cardW + gap, scH);

        int p = 12; // inner padding for card contents

        // ── Stats rows ────────────────────────────────────────────────────────
        int rowH = Math.max(1, (sH - 38) / 6);
        int half = cardW / 2;
        for (int i = 0; i < 6; i++) {
            int ry = 36 + i * rowH;
            statNameA[i].setBounds(p,    ry, half - p,      rowH);
            statValA[i] .setBounds(half, ry, half - p,      rowH);
            statNameB[i].setBounds(p,    ry, half - p,      rowH);
            statValB[i] .setBounds(half, ry, half - p,      rowH);
        }

        // ── Word rows ─────────────────────────────────────────────────────────
        int wRowH = Math.max(1, (wH - 38) / 10);
        for (int i = 0; i < 10; i++) {
            int ry = 36 + i * wRowH;
            wordRankA[i].setBounds(p,       ry, 22,               wRowH);
            wordTextA[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
            wordRankB[i].setBounds(p,       ry, 22,               wRowH);
            wordTextB[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
        }

        // ── Sentiment rows ────────────────────────────────────────────────────
        JLabel[] snA = {sNamePosA, sNameNeuA, sNameNegA};
        JLabel[] svA = {sValPosA,  sValNeuA,  sValNegA };
        JLabel[] snB = {sNamePosB, sNameNeuB, sNameNegB};
        JLabel[] svB = {sValPosB,  sValNeuB,  sValNegB };
        int seRowH    = Math.max(1, (seH - 38) / 3);
        int two3      = cardW * 2 / 3;
        for (int i = 0; i < 3; i++) {
            int ry = 36 + i * seRowH;
            snA[i].setBounds(p,      ry, two3 - p,            seRowH);
            svA[i].setBounds(two3,   ry, cardW - two3 - p,   seRowH);
            snB[i].setBounds(p,      ry, two3 - p,            seRowH);
            svB[i].setBounds(two3,   ry, cardW - two3 - p,   seRowH);
        }

        // ── Conclusion text ───────────────────────────────────────────────────
        concTextA.setBounds(p, 36, cardW - 2 * p, cH - 48);
        concTextB.setBounds(p, 36, cardW - 2 * p, cH - 48);

        // ── Score panel: centred inside full-width card ───────────────────────
        int fullW  = 2 * cardW + gap;
        int spSize = Math.min(scH - 30, 90);
        scorePanel.setBounds(fullW / 2 - spSize / 2, (scH - spSize) / 2, spSize, spSize);
    }

    private void pair(JPanel a, JPanel b, int x, int y, int w, int h, int gap) {
        a.setBounds(x,         y, w, h);
        b.setBounds(x + w + gap, y, w, h);
    }

    // ── glass card factory ────────────────────────────────────────────────────
    /**
     * Creates a glass-morphism card whose section title is rendered via
     * paintComponent (no extra JLabel component needed).

    private JPanel glassCard(final String sectionTitle) {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cw = getWidth(), ch = getHeight();

                // Frosted glass fill
                g2.setColor(Theme.GLASS_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch, 18, 18));

                // Top-edge shine
                g2.setPaint(new GradientPaint(
                        0, 0,           Theme.GLASS_HIGHLIGHT,
                        0, ch / 3f,     new Color(255, 255, 255, 0)));
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch / 3f, 18, 18));

                // Section title text
                g2.setColor(Theme.CARD_LABEL);
                g2.setFont(f(HKModular, 10f));
                g2.drawString(sectionTitle, 14, 22);

                // Hairline divider below title
                g2.setColor(Theme.DIVIDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(12, 30, cw - 12, 30);

                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new RoundedGlowBorder(18, Theme.ACCENT_PURPLE_SOFT, Theme.ACCENT_BLUE_SOFT));
        return p;
    }

    // ── label factory ─────────────────────────────────────────────────────────
    private JLabel lbl(String text, Color fg, float size, int align) {
        JLabel l = new JLabel(text, align);
        l.setForeground(fg);
        l.setFont(f(HKModular, size));
        return l;
    }

    private Font f(Font base, float size) {
        return base != null ? base.deriveFont(size) : new Font("SansSerif", Font.PLAIN, (int) size);
    }

    // ── RoundedGlowBorder (mirrors HomePanel) ─────────────────────────────────
    private static class RoundedGlowBorder extends AbstractBorder {
        private final int   radius;
        private final Color inner, outer;

        RoundedGlowBorder(int radius, Color inner, Color outer) {
            this.radius = radius;
            this.inner  = inner;
            this.outer  = outer;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Outer glow
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(outer);
            g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, radius + 2, radius + 2));

            // Inner crisp rim
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
}*/

package app;

import components.*;
import analysis.AnalysisResult;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class ResultsPanel extends JPanel {

    private static final Font HKModular = FontLoader.load("HKModular-Bold.otf", 1f);
    private static final Font Architype = FontLoader.load("Architype-Aubette.ttf", 1f);

    private static final String[] STAT_NAMES = {
            "Word Count", "Sentence Count", "Char Count",
            "Punct. Rate", "Avg Sent. Len", "Avg Word Len"
    };

    // ── scrolling infrastructure ──────────────────────────────────────────────
    private JScrollPane scrollPane;
    private JPanel contentPanel;

    // ── kept infrastructure ───────────────────────────────────────────────────
    private CircularScorePanel scorePanel;
    private JLabel title;

    // ── column headers (span the top of all card rows) ────────────────────────
    private JLabel colHeaderA, colHeaderB;

    // ── glass section cards ───────────────────────────────────────────────────
    private JPanel statsCardA,  statsCardB;
    private JPanel wordsCardA,  wordsCardB;
    private JPanel sentCardA,   sentCardB;
    private JPanel concCardA,   concCardB;
    private JPanel scoreCard;

    // ── stats labels  [6 rows × A/B × name/value] ────────────────────────────
    private final JLabel[] statNameA = new JLabel[6];
    private final JLabel[] statValA  = new JLabel[6];
    private final JLabel[] statNameB = new JLabel[6];
    private final JLabel[] statValB  = new JLabel[6];

    // ── top-10 word labels  [10 rows × A/B × rank/text] ──────────────────────
    private final JLabel[] wordRankA = new JLabel[10];
    private final JLabel[] wordTextA = new JLabel[10];
    private final JLabel[] wordRankB = new JLabel[10];
    private final JLabel[] wordTextB = new JLabel[10];

    // ── sentiment labels  [3 rows × A/B × name/value] ────────────────────────
    private JLabel sNamePosA, sValPosA, sNameNeuA, sValNeuA, sNameNegA, sValNegA;
    private JLabel sNamePosB, sValPosB, sNameNeuB, sValNeuB, sNameNegB, sValNegB;

    // ── conclusion text ───────────────────────────────────────────────────────
    private JLabel concTextA, concTextB;

    // ─────────────────────────────────────────────────────────────────────────
    public ResultsPanel() {
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout());
        buildDashboard();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { updateLayout(); }
        });
        SwingUtilities.invokeLater(this::updateLayout);
    }

    // ── kept method signatures ────────────────────────────────────────────────
    public void applyTheme() {
        setBackground(Theme.BACKGROUND);
        title.setForeground(Theme.TITLE);
        contentPanel.repaint();
        repaint();
    }

    public void updateResults(AnalysisResult result) {
        scorePanel.setScore(result.getSimilarity());
        // Wire additional AnalysisResult getters here as the API expands
        contentPanel.repaint();
        repaint();
    }

    // ── aurora gradient background (matches HomePanel) ────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setPaint(new GradientPaint(
                0, 0, Theme.BACKGROUND_GRADIENT_TOP,
                w, h, Theme.BACKGROUND_GRADIENT_BOTTOM));
        g2.fillRect(0, 0, w, h);

        // Orb positions shifted from HomePanel so the two panels feel related but distinct
        orb(g2, (int)(w * 0.85), (int)(h * 0.12), (int)(w * 0.45), Theme.ORB_PURPLE);
        orb(g2, (int)(w * 0.12), (int)(h * 0.78), (int)(w * 0.40), Theme.ORB_CYAN);
        orb(g2, (int)(w * 0.52), (int)(h * 0.48), (int)(w * 0.32), Theme.ORB_MAGENTA);

        g2.dispose();
    }

    private void orb(Graphics2D g2, int cx, int cy, int r, Color c) {
        g2.setPaint(new RadialGradientPaint(new Point(cx, cy), r,
                new float[]{0f, 1f},
                new Color[]{c, new Color(0, 0, 0, 0)}));
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    // ── dashboard construction ────────────────────────────────────────────────
    private void buildDashboard() {

        // ── Content panel (scrollable inner panel) ────────────────────────────
        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                // Transparent so outer gradient shows through
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();

                // Hairline divider below title
                g2.setColor(Theme.DIVIDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(40, 115, w - 40, 115);

                g2.dispose();
            }
        };
        contentPanel.setOpaque(false);

        title = new TitleGradient("ANALYSIS RESULTS");
        title.setFont(f(Architype, 38f));
        title.setForeground(Theme.TITLE);
        contentPanel.add(title);

        // Column headers sit above every card row as persistent orientation labels
        colHeaderA = lbl("TEXT  A", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        colHeaderB = lbl("TEXT  B", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        // Letter-spacing via HTML (same trick used in HomePanel card labels)
        colHeaderA.setText("<html><body style='letter-spacing:4px'>TEXT A</body></html>");
        colHeaderB.setText("<html><body style='letter-spacing:4px'>TEXT B</body></html>");
        contentPanel.add(colHeaderA);
        contentPanel.add(colHeaderB);

        // ── Statistics ────────────────────────────────────────────────────────
        statsCardA = glassCard("STATISTICS");
        statsCardB = glassCard("STATISTICS");
        contentPanel.add(statsCardA);
        contentPanel.add(statsCardB);
        for (int i = 0; i < 6; i++) {
            statNameA[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT,        11f, SwingConstants.LEFT);
            statValA[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statNameB[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT,        11f, SwingConstants.LEFT);
            statValB[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statsCardA.add(statNameA[i]); statsCardA.add(statValA[i]);
            statsCardB.add(statNameB[i]); statsCardB.add(statValB[i]);
        }

        // ── Top 10 Words ──────────────────────────────────────────────────────
        wordsCardA = glassCard("TOP 10 WORDS");
        wordsCardB = glassCard("TOP 10 WORDS");
        contentPanel.add(wordsCardA);
        contentPanel.add(wordsCardB);
        for (int i = 0; i < 10; i++) {
            wordRankA[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 10f, SwingConstants.RIGHT);
            wordTextA[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 11f, SwingConstants.LEFT);
            wordRankB[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 10f, SwingConstants.RIGHT);
            wordTextB[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 11f, SwingConstants.LEFT);
            wordsCardA.add(wordRankA[i]); wordsCardA.add(wordTextA[i]);
            wordsCardB.add(wordRankB[i]); wordsCardB.add(wordTextB[i]);
        }

        // ── Sentiment ─────────────────────────────────────────────────────────
        sentCardA = glassCard("SENTIMENT");
        sentCardB = glassCard("SENTIMENT");
        contentPanel.add(sentCardA);
        contentPanel.add(sentCardB);

        sNamePosA = lbl("● Positive", Theme.SUCCESS,  11f, SwingConstants.LEFT);
        sValPosA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNeuA = lbl("● Neutral",  Theme.SUBTEXT,  11f, SwingConstants.LEFT);
        sValNeuA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNegA = lbl("● Negative", Theme.NEGATIVE, 11f, SwingConstants.LEFT);
        sValNegA  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sentCardA.add(sNamePosA); sentCardA.add(sValPosA);
        sentCardA.add(sNameNeuA); sentCardA.add(sValNeuA);
        sentCardA.add(sNameNegA); sentCardA.add(sValNegA);

        sNamePosB = lbl("● Positive", Theme.SUCCESS,  11f, SwingConstants.LEFT);
        sValPosB  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNeuB = lbl("● Neutral",  Theme.SUBTEXT,  11f, SwingConstants.LEFT);
        sValNeuB  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sNameNegB = lbl("● Negative", Theme.NEGATIVE, 11f, SwingConstants.LEFT);
        sValNegB  = lbl("—%",         Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
        sentCardB.add(sNamePosB); sentCardB.add(sValPosB);
        sentCardB.add(sNameNeuB); sentCardB.add(sValNeuB);
        sentCardB.add(sNameNegB); sentCardB.add(sValNegB);

        // ── Conclusion ────────────────────────────────────────────────────────
        concCardA = glassCard("CONCLUSION");
        concCardB = glassCard("CONCLUSION");
        contentPanel.add(concCardA);
        contentPanel.add(concCardB);

        concTextA = lbl("<html>Awaiting analysis…</html>", Theme.TEXT, 11f, SwingConstants.LEFT);
        concTextB = lbl("<html>Awaiting analysis…</html>", Theme.TEXT, 11f, SwingConstants.LEFT);
        concTextA.setVerticalAlignment(SwingConstants.TOP);
        concTextB.setVerticalAlignment(SwingConstants.TOP);
        concCardA.add(concTextA);
        concCardB.add(concTextB);

        // ── Similarity Score (full-width) ─────────────────────────────────────
        scoreCard  = glassCard("SIMILARITY SCORE");
        scorePanel = new CircularScorePanel();
        scoreCard.add(scorePanel);
        contentPanel.add(scoreCard);

        // ── Scroll pane wrapping content ──────────────────────────────────────
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Style the scrollbar to match the theme
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        add(scrollPane, BorderLayout.CENTER);
    }

    // ── layout ────────────────────────────────────────────────────────────────
    private void updateLayout() {
        int w = getWidth();
        if (w < 100) return;

        // Derived metrics
        int side  = Math.max(32, w / 22);
        int gap   = Math.max(14, w / 55);
        int cardW = (w - 2 * side - gap) / 2;
        if (cardW < 80) return;

        // ── Fixed section heights (minimum sizes for scrollable content) ──────
        int topArea   = 128;
        int btmPad    = 28;
        int interGap  = 14;

        int sH  = 150;   // stats
        int wH  = 240;   // words (10 rows need more space)
        int seH = 120;   // sentiment
        int cH  = 130;   // conclusion
        int scH = 140;   // score

        // Total content height
        int contentHeight = topArea + sH + wH + seH + cH + scH + 4 * interGap + btmPad;

        // Set the preferred size of content panel for scrolling
        contentPanel.setPreferredSize(new Dimension(w, contentHeight));

        // ── Header area ───────────────────────────────────────────────────────
        title.setBounds(0, 25, w, 52);
        colHeaderA.setBounds(side,              88, cardW, 26);
        colHeaderB.setBounds(side + cardW + gap, 88, cardW, 26);

        // ── Card bounds ───────────────────────────────────────────────────────
        int sY  = topArea;
        int wY  = sY  + sH  + interGap;
        int seY = wY  + wH  + interGap;
        int cY  = seY + seH + interGap;
        int scY = cY  + cH  + interGap;

        pair(statsCardA, statsCardB, side, sY,  cardW, sH,  gap);
        pair(wordsCardA, wordsCardB, side, wY,  cardW, wH,  gap);
        pair(sentCardA,  sentCardB,  side, seY, cardW, seH, gap);
        pair(concCardA,  concCardB,  side, cY,  cardW, cH,  gap);
        scoreCard.setBounds(side, scY, 2 * cardW + gap, scH);

        int p = 12; // inner padding for card contents

        // ── Stats rows ────────────────────────────────────────────────────────
        int rowH = Math.max(1, (sH - 38) / 6);
        int half = cardW / 2;
        for (int i = 0; i < 6; i++) {
            int ry = 36 + i * rowH;
            statNameA[i].setBounds(p,    ry, half - p,      rowH);
            statValA[i] .setBounds(half, ry, half - p,      rowH);
            statNameB[i].setBounds(p,    ry, half - p,      rowH);
            statValB[i] .setBounds(half, ry, half - p,      rowH);
        }

        // ── Word rows ─────────────────────────────────────────────────────────
        int wRowH = Math.max(1, (wH - 38) / 10);
        for (int i = 0; i < 10; i++) {
            int ry = 36 + i * wRowH;
            wordRankA[i].setBounds(p,       ry, 22,               wRowH);
            wordTextA[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
            wordRankB[i].setBounds(p,       ry, 22,               wRowH);
            wordTextB[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
        }

        // ── Sentiment rows ────────────────────────────────────────────────────
        JLabel[] snA = {sNamePosA, sNameNeuA, sNameNegA};
        JLabel[] svA = {sValPosA,  sValNeuA,  sValNegA };
        JLabel[] snB = {sNamePosB, sNameNeuB, sNameNegB};
        JLabel[] svB = {sValPosB,  sValNeuB,  sValNegB };
        int seRowH    = Math.max(1, (seH - 38) / 3);
        int two3      = cardW * 2 / 3;
        for (int i = 0; i < 3; i++) {
            int ry = 36 + i * seRowH;
            snA[i].setBounds(p,      ry, two3 - p,            seRowH);
            svA[i].setBounds(two3,   ry, cardW - two3 - p,   seRowH);
            snB[i].setBounds(p,      ry, two3 - p,            seRowH);
            svB[i].setBounds(two3,   ry, cardW - two3 - p,   seRowH);
        }

        // ── Conclusion text ───────────────────────────────────────────────────
        concTextA.setBounds(p, 36, cardW - 2 * p, cH - 48);
        concTextB.setBounds(p, 36, cardW - 2 * p, cH - 48);

        // ── Score panel: centred inside full-width card ───────────────────────
        int fullW  = 2 * cardW + gap;
        int spSize = Math.min(scH - 30, 90);
        scorePanel.setBounds(fullW / 2 - spSize / 2, (scH - spSize) / 2, spSize, spSize);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void pair(JPanel a, JPanel b, int x, int y, int w, int h, int gap) {
        a.setBounds(x,         y, w, h);
        b.setBounds(x + w + gap, y, w, h);
    }

    // ── glass card factory ────────────────────────────────────────────────────
    private JPanel glassCard(final String sectionTitle) {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cw = getWidth(), ch = getHeight();

                // Frosted glass fill
                g2.setColor(Theme.GLASS_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch, 18, 18));

                // Top-edge shine
                g2.setPaint(new GradientPaint(
                        0, 0,           Theme.GLASS_HIGHLIGHT,
                        0, ch / 3f,     new Color(255, 255, 255, 0)));
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch / 3f, 18, 18));

                // Section title text
                g2.setColor(Theme.CARD_LABEL);
                g2.setFont(f(HKModular, 10f));
                g2.drawString(sectionTitle, 14, 22);

                // Hairline divider below title
                g2.setColor(Theme.DIVIDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(12, 30, cw - 12, 30);

                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new RoundedGlowBorder(18, Theme.ACCENT_PURPLE_SOFT, Theme.ACCENT_BLUE_SOFT));
        return p;
    }

    // ── label factory ─────────────────────────────────────────────────────────
    private JLabel lbl(String text, Color fg, float size, int align) {
        JLabel l = new JLabel(text, align);
        l.setForeground(fg);
        l.setFont(f(HKModular, size));
        return l;
    }

    private Font f(Font base, float size) {
        return base != null ? base.deriveFont(size) : new Font("SansSerif", Font.PLAIN, (int) size);
    }

    // ── RoundedGlowBorder (mirrors HomePanel) ─────────────────────────────────
    private static class RoundedGlowBorder extends AbstractBorder {
        private final int   radius;
        private final Color inner, outer;

        RoundedGlowBorder(int radius, Color inner, Color outer) {
            this.radius = radius;
            this.inner  = inner;
            this.outer  = outer;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Outer glow
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(outer);
            g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, radius + 2, radius + 2));

            // Inner crisp rim
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

    // ── Modern styled scrollbar UI ────────────────────────────────────────────
    private static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(255, 255, 255, 60);
            this.trackColor = new Color(0, 0, 0, 0);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            return btn;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fill(new RoundRectangle2D.Float(
                    thumbBounds.x + 2, thumbBounds.y,
                    thumbBounds.width - 4, thumbBounds.height,
                    8, 8));
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // Transparent track
        }
    }
}


