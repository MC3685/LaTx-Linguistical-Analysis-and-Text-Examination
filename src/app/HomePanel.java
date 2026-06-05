package app;

import components.TitleGradient;
import analysis.AnalysisEngine;
import analysis.AnalysisResult;
import components.*;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;

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

    JLabel LabelA = new JLabel("No file selected");
    JLabel LabelB = new JLabel("No file selected");

    private JLabel subtitle;
    private JLabel lbl;

    private TitleGradient title;

    private final MainFrame frame;

    private static final Font HKModular = FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype = FontLoader.load("Architype-Aubette.ttf", 1f);

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

    public void applyTheme() {

        setBackground(Theme.BACKGROUND);

        subtitle.setForeground(Theme.SUBTITLE);

        textA.setBackground(Theme.BACKGROUND);
        textA.setForeground(Theme.TEXT_AREA_TEXT);
        textA.setCaretColor(Theme.TEXT_AREA_CARET);
        textA.setSelectionColor(Theme.TEXT_AREA_SELECTION);

        textB.setBackground(Theme.BACKGROUND);
        textB.setForeground(Theme.TEXT_AREA_TEXT);
        textB.setCaretColor(Theme.TEXT_AREA_CARET);
        textB.setSelectionColor(Theme.TEXT_AREA_SELECTION);

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BackgroundPainter.paintBackground(g, this);


        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Theme.DIVIDER);
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(40, 140, getWidth() - 40, 140);

    }


    private void buildUI() {

        title = new TitleGradient("LINGUISTIC ANALYSIS AND TEXT EXAMINATION");
        title.setForeground(Theme.TITLE);
        title.setFont(Architype.deriveFont(60f));
        add(title);

        subtitle = new JLabel("DISCOVER THE AUTHOR BEHIND THE WORDS", SwingConstants.CENTER);
        subtitle.setForeground(Theme.SUBTITLE);
        subtitle.setFont(HKModular.deriveFont(15f));
        add(subtitle);

        cardA = createTextCard("TEXT  A");
        cardB = createTextCard("TEXT  B");
        add(cardA);
        add(cardB);

        textA = createTextArea();
        textB = createTextArea();

        scrollA = createStyledScroll(textA);
        scrollB = createStyledScroll(textB);

        cardA.add(scrollA);
        cardB.add(scrollB);

        importA = createImportButton("ADD FILE");
        importB = createImportButton("ADD FILE");

        importA.addActionListener(e -> FilePicker(frame, importA, textA));
        importB.addActionListener(e -> FilePicker(frame, importB, textB));

        cardA.add(importA);
        cardB.add(importB);

        analyzeButton = createAnalyzeButton("RUN ANALYSIS");
        analyzeButton.setFont(HKModular.deriveFont(16f));
        analyzeButton.addActionListener(e -> {
            AnalysisEngine engine = new AnalysisEngine();
            AnalysisResult result = engine.analyze(textA.getText(), textB.getText());
            frame.getResultsPanel().updateResults(result);
            frame.showResults();
        });

        add(analyzeButton);
    }


    private void updateLayout() {

        int w = getWidth(), h = getHeight();
        int topMargin = 42;

        title.setBounds(0, topMargin, w, 52);
        title.setFont(Architype.deriveFont((w*0.04f)));
        subtitle.setBounds(0, topMargin + 58, w, 24);

        int gap = Math.max(24, w / 45);
        int cardWidth = Math.min(500, (w - 280 - gap) / 2);
        int cardHeight = Math.max(360, h - 320);
        int cardY = 158;

        int totalWidth = cardWidth * 2 + gap;
        int startX = (w - totalWidth) / 2;

        cardA.setBounds(startX, cardY, cardWidth, cardHeight);
        cardB.setBounds(startX + cardWidth + gap, cardY, cardWidth, cardHeight);

        int sm = 22;

        scrollA.setBounds(sm, 42, cardWidth - 44, cardHeight - 130);
        scrollB.setBounds(sm, 42, cardWidth - 44, cardHeight - 130);

        importA.setBounds(cardWidth / 2 - 110, cardHeight - 75, 220, 42);
        importB.setBounds(cardWidth / 2 - 110, cardHeight - 75, 220, 42);

        analyzeButton.setBounds(w / 2 - 160, h - 76, 320, 50);
    }


    private JPanel createTextCard(String label) {

        panel = new JPanel(null) {

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

        panel.setOpaque(false);
        panel.setBorder(new RoundedGlowBorder(20, Theme.ACCENT_PURPLE_SOFT, Theme.ACCENT_BLUE_SOFT));

        lbl = new JLabel("<html><body style='letter-spacing:3px'>" + label + "</body></html>", SwingConstants.CENTER);
        lbl.setForeground(Theme.CARD_LABEL);
        lbl.setFont(HKModular.deriveFont(16f));
        lbl.setBounds(0, 12, 400, 20);

        panel.add(lbl);

        return panel;
    }

    private JTextArea createTextArea() {

        area = new JTextArea();
        area.setBackground(new Color(0, 0, 0, 0));
        area.setForeground(Theme.TEXT_AREA_TEXT);
        area.setCaretColor(Theme.TEXT_AREA_CARET);
        area.setSelectionColor(Theme.TEXT_AREA_SELECTION);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);

        return area;
    }

    private JScrollPane createStyledScroll(JTextArea ta) {

        JScrollPane scroll = new JScrollPane(ta);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        scroll.getVerticalScrollBar().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        scroll.getVerticalScrollBar().setUI(new ThinScrollBarUI(Theme.SCROLLBAR_THUMB));

        return scroll;
    }


    private JButton createImportButton(String text) {

        JButton btn = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();
                boolean pressed = getModel().isPressed();

                RoundRectangle2D.Float rr = new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 30, 30);

                if (pressed)
                {
                    g2.setColor(Theme.BUTTON_IMPORT_PRESSED);
                    g2.fill(rr);

                }
                else if (hover)
                {
                    GradientPaint gp = new GradientPaint(0, 0, Theme.BUTTON_IMPORT_GRADIENT_START, getWidth(), getHeight(), Theme.BUTTON_IMPORT_GRADIENT_END);
                    g2.setPaint(gp);
                    g2.fill(rr);
                }
                else
                {
                    g2.setColor(Theme.BUTTON_IMPORT_IDLE);
                    g2.fill(rr);
                }

                g2.setStroke(new BasicStroke(1.2f));

                GradientPaint border = new GradientPaint(0, 0, Theme.BUTTON_IMPORT_BORDER_START, getWidth(), getHeight(), Theme.BUTTON_IMPORT_BORDER_END);

                g2.setPaint(border);
                g2.draw(rr);

                g2.setColor(hover ? Theme.BUTTON_TEXT : Theme.CARD_LABEL);

                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.drawString(getText(), tx, ty);

                g2.dispose();
            }
        };

        btn.setFont(HKModular.deriveFont(12f));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private JButton createAnalyzeButton(String text) {

        JButton btn = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();
                boolean pressed = getModel().isPressed();

                RoundRectangle2D.Float rr = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);

                GradientPaint gp;

                if (pressed)
                {
                    gp = new GradientPaint(0, 0, Theme.BUTTON_ANALYZE_PRESSED_START, getWidth(), getHeight(), Theme.BUTTON_ANALYZE_PRESSED_END);
                }
                else if (hover)
                {
                    gp = new GradientPaint(0, 0, Theme.BUTTON_ANALYZE_HOVER_START, getWidth(), getHeight(), Theme.BUTTON_ANALYZE_HOVER_END);
                }
                else
                {
                    gp = new GradientPaint(0, 0, Theme.BUTTON_ANALYZE_START, getWidth(), getHeight(), Theme.BUTTON_ANALYZE_END);
                }

                g2.setPaint(gp);
                g2.fill(rr);

                GradientPaint shine = new GradientPaint(
                        0, 0,
                        new Color(255, 255, 255, hover ? 40 : 25),
                        0, getHeight() / 2f,
                        new Color(255, 255, 255, 0)
                );

                g2.setPaint(shine);
                g2.fill(rr);

                if (hover) {
                    g2.setStroke(new BasicStroke(2f));
                    g2.setColor(Theme.BUTTON_ANALYZE_GLOW);
                    g2.draw(new RoundRectangle2D.Float(-2, -2, getWidth() + 4, getHeight() + 4, 54, 54));
                }

                g2.setColor(Color.WHITE);

                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.drawString(getText(), tx, ty);

                g2.dispose();
            }
        };

        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }


    public void FilePicker(MainFrame parent, JButton targetButton, JTextArea targetTextArea) {
        FileDialog fileDialog = new FileDialog(parent, "Open Text File", FileDialog.LOAD);
        fileDialog.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".txt"));
        fileDialog.setVisible(true);

        String filename = fileDialog.getFile();
        String directory = fileDialog.getDirectory();
        fileDialog.dispose();

        if (filename == null) return;

        File chosenFile = new File(directory, filename);

        try {
            String content = Files.readString(chosenFile.toPath());
            targetTextArea.setText(content);
            handleSuccessfulImport(chosenFile, targetButton);
        }

        catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read file: ",
                    "Import Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSuccessfulImport(File file, JButton targetButton) {
        JOptionPane.showMessageDialog(this,
                "Successfully imported: " + file.getName(),
                "Import Success",
                JOptionPane.INFORMATION_MESSAGE);

        targetButton.setText(file.getName());
        targetButton.setEnabled(false);
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

    private static class ThinScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {

        private final Color thumbColor;

        ThinScrollBarUI(Color thumbColor) {
            this.thumbColor = thumbColor;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(thumbColor);
            g2.fill(new RoundRectangle2D.Float(r.x + 1, r.y, r.width - 2, r.height, 4, 4));

            g2.dispose();
        }

        @Override
        protected JButton createDecreaseButton(int o) {
            return new JButton() {{ setPreferredSize(new Dimension(0, 0)); }};
        }

        @Override
        protected JButton createIncreaseButton(int o) {
            return new JButton() {{ setPreferredSize(new Dimension(0, 0)); }};
        }
    }
}