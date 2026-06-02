package app;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

import components.FontLoader;

public class NavigationPanel extends JPanel {

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    public NavigationPanel(MainFrame frame) {

        setPreferredSize(new Dimension(170,0));

        setBackground(Theme.SIDEBAR);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(80));

        add(navButton(" !! HOME", e -> frame.showHome()));

        add(Box.createVerticalStrut(40));

        add(navButton("-- REPORTS", e -> frame.showResults()));

        add(Box.createVerticalStrut(40));

        add(navButton("# SETTINGS", e -> frame.showSettings()));

        add(Box.createVerticalStrut(40));

        add(navButton("? HELP", e ->
                showHelp()));

        add(Box.createVerticalStrut(40));

        add(navButton("% RATE US", e ->
                openWebsite()));
    }

    private JButton navButton(String text, java.awt.event.ActionListener listener) {

        JButton button = new JButton(text);

        button.setFont(HKModular.deriveFont(15f));
        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setMaximumSize(
                new Dimension(150,50));

        button.setAlignmentX(CENTER_ALIGNMENT);

        button.setBackground(Theme.SIDEBAR);

        button.setForeground(Theme.PURPLE);

        button.addActionListener(listener);

        return button;
    }

    private void showHelp() {

        JOptionPane.showMessageDialog(
                this,
                """
                Linguistic Analysis Tool

                1. Import two text files
                2. Enter optional titles
                3. Run analysis
                4. Review similarity metrics

                Designed for stylometric analysis.
                """
        );
    }

    private void openWebsite() {

        try {
            Desktop.getDesktop().browse(
                    new URI("https://github.com/MC3685/LaTx"));
        } catch(Exception ignored) {}
    }
}