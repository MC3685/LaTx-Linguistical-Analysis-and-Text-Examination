package app;

import analysis.AnalysisEngine;
import analysis.AnalysisResult;
import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

    private JLabel title;
    private JLabel subtitle;
    private JLabel lbl;

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
        area.setCaretColor(Theme.TEXT);
        area.setForeground(Theme.TEXT);
        area.setBackground(Theme.BACKGROUND);
        lbl.setForeground(Theme.SUBTEXT);
        panel.setBackground(Theme.CARD);

    }


    private void buildUI() {

        title = new JLabel(
                "LINGUISTIC ANALYSIS AND TEXT EXAMINATION",
                SwingConstants.CENTER);

        title.setForeground(Theme.PURPLE);
        title.setFont(Architype.deriveFont(40f));

        add(title);

        subtitle = new JLabel(
                "DISCOVER THE AUTHOR BEHIND THE WORDS",
                SwingConstants.CENTER);

        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.ITALIC, 20));

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

        // Pass the respective button and text area targets to the unified file picker
        importA = new JButton("ADD FILE");
        importA.addActionListener(e -> FilePicker(frame, importA, textA));

        importB = new JButton("ADD FILE");
        importB.addActionListener(e -> FilePicker(frame, importB, textB));

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

    /**
     * Reusable File Dialog targeting specific buttons and text areas.
     */
    public void FilePicker(MainFrame parent, JButton targetButton, JTextArea targetTextArea) {
        FileDialog fileDialog = new FileDialog(parent, "Open Text File", FileDialog.LOAD);

        // Only accept .txt files
        fileDialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        fileDialog.setVisible(true);

        String filename = fileDialog.getFile();
        String directory = fileDialog.getDirectory();
        fileDialog.dispose();

        // Check if user cancelled selection
        if (filename == null) {
            return;
        }

        File chosenFile = new File(directory, filename);

        // Read file contents into target JTextArea and update the target button
        try {
            String content = Files.readString(chosenFile.toPath());
            targetTextArea.setText(content);

            handleSuccessfulImport(chosenFile, targetButton);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read file: " + ex.getMessage(),
                    "Import Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSuccessfulImport(File file, JButton targetButton) {
        JOptionPane.showMessageDialog(this,
                "Successfully imported: " + file.getName(),
                "Import Success",
                JOptionPane.INFORMATION_MESSAGE);

        // Update button parameters as requested
        targetButton.setText(file.getName());
        targetButton.setEnabled(false);
    }
}
