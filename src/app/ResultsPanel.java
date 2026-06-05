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

    private JScrollPane scrollPane;
    private JPanel contentPanel;

    private CircularScorePanel scorePanel;
    private JLabel title;

    private JLabel colHeaderA, colHeaderB;

    private JPanel statsCardA,  statsCardB;
    private JPanel wordsCardA,  wordsCardB;
    private JPanel sentCardA,   sentCardB;
    private JPanel conchCardA,   conchCardB;
    private JPanel scoreCard;

    private final JLabel[] statNameA = new JLabel[6];
    private final JLabel[] statValA  = new JLabel[6];
    private final JLabel[] statNameB = new JLabel[6];
    private final JLabel[] statValB  = new JLabel[6];

    private final JLabel[] wordRankA = new JLabel[10];
    private final JLabel[] wordTextA = new JLabel[10];
    private final JLabel[] wordRankB = new JLabel[10];
    private final JLabel[] wordTextB = new JLabel[10];

    private JLabel sNamePosA, sValPosA, sNameNeuA, sValNeuA, sNameNegA, sValNegA;
    private JLabel sNamePosB, sValPosB, sNameNeuB, sValNeuB, sNameNegB, sValNegB;

    private JLabel conchTextA, conchTextB; //conclusion A and B

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

    public void applyTheme() {
        setBackground(Theme.BACKGROUND);
        title.setForeground(Theme.TITLE);
        contentPanel.repaint();
        repaint();
    }

    public void updateResults(AnalysisResult result) {
        scorePanel.setScore(result.getSimilarity());

        // Update statistics
        analysis.TextProfile profileA = result.getProfileA();
        analysis.TextProfile profileB = result.getProfileB();

        String[] statsA = {
            String.valueOf(profileA.wordCount),
            String.valueOf(profileA.wordCount / Math.max(1, profileA.avgSentenceLength)),
            String.valueOf((int)(profileA.wordCount * profileA.avgWordLength)),
            String.format("%.1f */.", profileA.punctuationDensity * 100),
            String.format("%.1f", profileA.avgSentenceLength),
            String.format("%.1f", profileA.avgWordLength)
        };

        String[] statsB = {
            String.valueOf(profileB.wordCount),
            String.valueOf(profileB.wordCount / Math.max(1, profileB.avgSentenceLength)),
            String.valueOf((int)(profileB.wordCount * profileB.avgWordLength)),
            String.format("%.1f */.", profileB.punctuationDensity * 100),
            String.format("%.1f", profileB.avgSentenceLength),
            String.format("%.1f", profileB.avgWordLength)
        };

        for (int i = 0; i < 6; i++) {
            statValA[i].setText(statsA[i]);
            statValB[i].setText(statsB[i]);
        }

        // Update top 10 words
        Object[][] topWords = result.getTopWords();
        for (int i = 0; i < 10; i++) {
            if (i < topWords.length) {
                wordTextA[i].setText((String) topWords[i][0] + " (" + topWords[i][1] + ")");
                wordTextB[i].setText((String) topWords[i][0] + " (" + topWords[i][2] + ")");
            } else {
                wordTextA[i].setText("—");
                wordTextB[i].setText("—");
            }
        }

        // Update conclusion
        conchTextA.setText(result.getConclusion());
        conchTextB.setText(result.getConclusion());

        contentPanel.repaint();
        repaint();
    }

    private JScrollPane createStyledScroll(JPanel pa) {

        JScrollPane spa = new JScrollPane(pa);
        spa.setOpaque(false);
        spa.getViewport().setOpaque(false);
        spa.setBorder(BorderFactory.createEmptyBorder());

        spa.getVerticalScrollBar().setOpaque(false);
        spa.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        spa.getVerticalScrollBar().setUI(new HomePanel.ThinScrollBarUI(Theme.SCROLLBAR_THUMB));

        return spa;
    }

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

    private void buildDashboard() {

        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();

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

        colHeaderA = lbl("TEXT  A", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        colHeaderB = lbl("TEXT  B", Theme.CARD_LABEL, 13f, SwingConstants.CENTER);
        // Letter-spacing via HTML (same trick used in HomePanel card labels)
        colHeaderA.setText("<html><body style='letter-spacing:4px'>TEXT A</body></html>");
        colHeaderB.setText("<html><body style='letter-spacing:4px'>TEXT B</body></html>");
        contentPanel.add(colHeaderA);
        contentPanel.add(colHeaderB);

        statsCardA = glassCard("STATISTICS");
        statsCardB = glassCard("STATISTICS");
        contentPanel.add(statsCardA);
        contentPanel.add(statsCardB);
        for (int i = 0; i < 6; i++) {
            statNameA[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT, 11f, SwingConstants.LEFT);
            statValA[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statNameB[i] = lbl(STAT_NAMES[i], Theme.SUBTEXT,        11f, SwingConstants.LEFT);
            statValB[i]  = lbl("—",           Theme.TEXT_AREA_TEXT, 11f, SwingConstants.RIGHT);
            statsCardA.add(statNameA[i]); statsCardA.add(statValA[i]);
            statsCardB.add(statNameB[i]); statsCardB.add(statValB[i]);
        }

        wordsCardA = glassCard("TOP 10 WORDS");
        wordsCardB = glassCard("TOP 10 WORDS");
        contentPanel.add(wordsCardA);
        contentPanel.add(wordsCardB);
        for (int i = 0; i < 10; i++) {
            wordRankA[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 13f, SwingConstants.RIGHT);
            wordTextA[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 14f, SwingConstants.LEFT);
            wordRankB[i] = lbl(String.valueOf(i + 1), Theme.ACCENT_PURPLE, 13f, SwingConstants.RIGHT);
            wordTextB[i] = lbl("—",                  Theme.TEXT_AREA_TEXT, 14f, SwingConstants.LEFT);
            wordsCardA.add(wordRankA[i]); wordsCardA.add(wordTextA[i]);
            wordsCardB.add(wordRankB[i]); wordsCardB.add(wordTextB[i]);
        }

        sentCardA = glassCard("TONE");
        sentCardB = glassCard("TONE");
        contentPanel.add(sentCardA);
        contentPanel.add(sentCardB);

        sNamePosA = lbl("- Positive", Theme.SUCCESS,  18f, SwingConstants.LEFT);
        sValPosA  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sNameNeuA = lbl("- Neutral",  Theme.SUBTEXT,  18f, SwingConstants.LEFT);
        sValNeuA  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sNameNegA = lbl("- Negative", Theme.NEGATIVE, 18f, SwingConstants.LEFT);
        sValNegA  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sentCardA.add(sNamePosA); sentCardA.add(sValPosA);
        sentCardA.add(sNameNeuA); sentCardA.add(sValNeuA);
        sentCardA.add(sNameNegA); sentCardA.add(sValNegA);

        sNamePosB = lbl("● Positive", Theme.SUCCESS,  18f, SwingConstants.LEFT);
        sValPosB  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sNameNeuB = lbl("● Neutral",  Theme.SUBTEXT,  18f, SwingConstants.LEFT);
        sValNeuB  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sNameNegB = lbl("● Negative", Theme.NEGATIVE, 18f, SwingConstants.LEFT);
        sValNegB  = lbl("—*/.",         Theme.TEXT_AREA_TEXT, 18f, SwingConstants.RIGHT);
        sentCardB.add(sNamePosB); sentCardB.add(sValPosB);
        sentCardB.add(sNameNeuB); sentCardB.add(sValNeuB);
        sentCardB.add(sNameNegB); sentCardB.add(sValNegB);

        conchCardA = glassCard("CONCLUSION");
        conchCardB = glassCard("CONCLUSION");
        contentPanel.add(conchCardA);
        contentPanel.add(conchCardB);

        conchTextA = lbl("Awaiting analysis…", Theme.TEXT_AREA_TEXT, 13f, SwingConstants.LEFT);
        conchTextB = lbl("Awaiting analysis…", Theme.TEXT, 13f, SwingConstants.LEFT);
        conchTextB.setForeground(Theme.TEXT_AREA_TEXT);


        conchTextA.setVerticalAlignment(SwingConstants.TOP);
        conchTextB.setVerticalAlignment(SwingConstants.TOP);
        conchCardA.add(conchTextA);
        conchCardB.add(conchTextB);

        scoreCard  = glassCard("SIMILARITY SCORE");
        scorePanel = new CircularScorePanel();
        scoreCard.add(scorePanel);
        contentPanel.add(scoreCard);

        scrollPane = createStyledScroll(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(2);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateLayout() {
        int w = getWidth();
        if (w < 100) return;

        int side  = Math.max(32, w / 22);
        int gap   = Math.max(14, w / 55);
        int cardW = (w - 2 * side - gap) / 2;
        if (cardW < 80) return;

        int topArea   = 128;
        int btmPad    = 28;
        int interGap  = 14;

        int sH  = 180;
        int wH  = 330;
        int seH = 150;
        int cH  = 200;
        int scH = 300;

        // Total content height
        int contentHeight = topArea + sH + wH + seH + cH + scH + 4 * interGap + btmPad;

        contentPanel.setPreferredSize(new Dimension(w, contentHeight));

        title.setBounds(0, 25, w, 52);
        colHeaderA.setBounds(side,              88, cardW, 26);
        colHeaderB.setBounds(side + cardW + gap, 88, cardW, 26);

        int sY  = topArea;
        int wY  = sY  + sH  + interGap;
        int seY = wY  + wH  + interGap;
        int cY  = seY + seH + interGap;
        int scY = cY  + cH  + interGap;

        //for setting sizes
        pair(statsCardA, statsCardB, side, sY,  cardW, sH,  gap);
        pair(wordsCardA, wordsCardB, side, wY,  cardW, wH,  gap);
        pair(sentCardA,  sentCardB,  side, seY, cardW, seH, gap);
        pair(conchCardA, conchCardB,  side, cY,  cardW, cH,  gap);
        scoreCard.setBounds(side, scY, 2 * cardW + gap, scH);

        int p = 12; // here for inner padding (of card contents)

        int rowH = Math.max(1, (sH - 38) / 6);
        int half = cardW / 2;
        for (int i = 0; i < 6; i++) {
            int ry = 36 + i * rowH;
            statNameA[i].setBounds(p,    ry, half - p,      rowH);
            statValA[i] .setBounds(half, ry, half - p,      rowH);
            statNameB[i].setBounds(p,    ry, half - p,      rowH);
            statValB[i] .setBounds(half, ry, half - p,      rowH);
        }

        int wRowH = Math.max(1, (wH - 38) / 10);
        for (int i = 0; i < 10; i++) {
            int ry = 36 + i * wRowH;
            wordRankA[i].setBounds(p,       ry, 22,               wRowH);
            wordTextA[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
            wordRankB[i].setBounds(p,       ry, 22,               wRowH);
            wordTextB[i].setBounds(p + 24,  ry, cardW - p - 26,  wRowH);
        }


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

        conchTextA.setBounds(p, 36, cardW - 2 * p, cH - 48);
        conchTextB.setBounds(p, 36, cardW - 2 * p, cH - 48);

        int fullW  = 2 * cardW + gap;
        int spSize = Math.max(scH - 30, 90);
        scorePanel.setBounds(fullW / 2 - spSize / 2, (scH - spSize) / 2, spSize, spSize);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void pair(JPanel a, JPanel b, int x, int y, int w, int h, int gap) {
        a.setBounds(x,y,w,h);
        b.setBounds(x + w + gap, y, w, h);
    }

    private JPanel glassCard(final String sectionTitle) {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cw = getWidth(), ch = getHeight();

                g2.setColor(Theme.GLASS_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch, 18, 18));

                g2.setPaint(new GradientPaint(
                        0, 0,           Theme.GLASS_HIGHLIGHT,
                        0, ch / 3f,     new Color(255, 255, 255, 0)));
                g2.fill(new RoundRectangle2D.Float(0, 0, cw, ch / 3f, 18, 18));

                g2.setColor(Theme.CARD_LABEL);
                g2.setFont(f(HKModular, 10f));
                g2.drawString(sectionTitle, 14, 22);

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

    private JLabel lbl(String text, Color fg, float size, int align) {
        JLabel l = new JLabel(text, align);
        l.setForeground(fg);
        l.setFont(f(HKModular, size));
        return l;
    }

    private Font f(Font base, float size) {
        return base != null ? base.deriveFont(size) : new Font("SansSerif", Font.PLAIN, (int) size);
    }

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


