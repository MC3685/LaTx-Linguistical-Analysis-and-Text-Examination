package app;

import analysis.AnalysisEngine;
import analysis.AnalysisResult;
import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;


public class HomePanel extends JPanel {

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

        importA = new JButton("ADD FILE");
        importA.addActionListener(e -> openTextFilePicker());

        importB = new JButton("ADD FILE");

        cardA.add(importA);
        cardB.add(importB);


        analyzeButton = new GradientButton("RUN ANALYSIS");
        analyzeButton.setFont(HKModular.deriveFont(20f));

        analyzeButton.addActionListener(e -> {

            AnalysisEngine engine =
                    new AnalysisEngine();

            AnalysisResult result =
                    engine.analyze(
                            textA.getText(),
                            textB.getText());

            frame.getResultsPanel()
                    .updateResults(result);

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

        int gap =
                Math.max(20, w / 50);

        int cardWidth =
                Math.min(
                        500,
                        (w - 300 - gap) / 2);

        int cardHeight =
                Math.max(
                        350,
                        h - 340);

        int cardY = 160;

        int totalWidth =
                cardWidth * 2 + gap;

        int startX =
                (w - totalWidth) / 2;

        cardA.setBounds(
                startX,
                cardY,
                cardWidth,
                cardHeight);

        cardB.setBounds(
                startX + cardWidth + gap,
                cardY,
                cardWidth,
                cardHeight);

        int scrollMargin = 25;

        scrollA.setBounds(
                scrollMargin,
                30,
                cardWidth - 50,
                cardHeight - 170);

        scrollB.setBounds(
                scrollMargin,
                30,
                cardWidth - 50,
                cardHeight - 170);

        importA.setBounds(
                cardWidth / 2 - 125,
                cardHeight - 110,
                250,
                45);

        importB.setBounds(
                cardWidth / 2 - 125,
                cardHeight - 110,
                250,
                45);


        analyzeButton.setBounds(
                w / 2 - 150,
                h - 80,
                300,
                50);
    }

    private JPanel createTextCard(String label) {

        JPanel panel = new JPanel(null);

        panel.setBackground(Theme.CARD);

        panel.setBorder(
                BorderFactory.createLineBorder(
                        Theme.BORDER,
                        2));

        JLabel lbl = new JLabel(label);

        lbl.setForeground(Theme.SUBTEXT);

        lbl.setHorizontalAlignment(
                SwingConstants.CENTER);

        lbl.setBounds(
                0,
                0,
                200,
                25);

        panel.add(lbl);

        return panel;
    }

    private JTextArea createTextArea() {

        JTextArea area = new JTextArea();

        area.setBackground(Theme.BACKGROUND);
        area.setForeground(Theme.TEXT);

        area.setCaretColor(Theme.TEXT);

        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        return area;
    }
}
private void openTextFilePicker() {
    FileDialog fileDialog = new FileDialog((java.awt.Frame) this, "Select TXT File", FileDialog.LOAD);

    // 2. Set extension filter to only allow .txt files
    fileDialog.setFilenameFilter(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".txt");
        }
    });

    // 3. Make dialog visible (blocks background window execution)
    fileDialog.setVisible(true);

    // 4. Handle results
    String directory = fileDialog.getDirectory();
    String filename = fileDialog.getFile();

    if (filename != null) {
        File selectedFile = new File(directory, filename);
        handleSuccessfulImport(selectedFile);
    } else {
        System.out.println("User canceled selection.");
    }
}

private void handleSuccessfulImport(File file) {
    // Confirmation alert to user
    JOptionPane.showMessageDialog(this,
            "Successfully imported: " + file.getName(),
            "Import Success",
            JOptionPane.INFORMATION_MESSAGE);

    // Permanently disable the button to prevent future clicks
    importButton.setEnabled(false);
    importButton.setText("File Already Imported");
}
