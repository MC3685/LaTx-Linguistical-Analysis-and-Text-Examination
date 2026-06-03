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

        add(navButton("$ REPORTS", e -> frame.showResults()));

        add(Box.createVerticalStrut(40));

        add(navButton("# SETTINGS", e -> frame.showSettings()));

        add(Box.createVerticalStrut(40));

        add(navButton("? HELP", e ->
                showHelp(frame)));

        add(Box.createVerticalStrut(40));

        add(navButton("! RATE US", e ->
                openWebsite()));
    }

    private JButton navButton(String text, java.awt.event.ActionListener listener) {

        JButton button = new JButton(text);

        button.setFont(HKModular.deriveFont(15f));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setBorderPainted(false);

        button.setMaximumSize(
                new Dimension(150,50));

        button.setAlignmentX(CENTER_ALIGNMENT);

        button.setBackground(Theme.SIDEBAR);

        button.setForeground(Theme.PURPLE);

        button.addActionListener(listener);

        return button;
    }

    private void showHelp(MainFrame frame) {

        JDialog instructionsWindow = new JDialog(frame, "Instructions", true);
        instructionsWindow.setSize(350, 300);
        instructionsWindow.setLocationRelativeTo(null);
        instructionsWindow.setLayout(new BorderLayout());
        ((JComponent) instructionsWindow.getContentPane()).setBorder(
                BorderFactory.createLineBorder(new Color(244,234,251), 5)); //change color


        JTextArea instructionsArea = new JTextArea(
                " Instructions:\n\n" +
                        " 1. Select a shape\n" +
                        " 2. Enter dimensions\n" +
                        "    - Cannot be decimals\n" +
                        "    - Cannot be negative numbers\n" +
                        "    - Cannot include letters\n" +
                        "    - Cannot be longer than 6 digits\n" +
                        " 3. Click Calculate\n" +
                        "    - Dimensions cannot be empty\n\n" +
                        " Dimensions needed for each shape:\n" +
                        " - Cube: side\n" +
                        " - Sphere: radius\n" +
                        " - Cone: radius & height\n" +
                        " - Cylinder: radius & height\n" +
                        " - Prism: side & height\n" +
                        " - Tetrahedron: side"
        );
        instructionsArea.setEditable(false);
        instructionsArea.setFocusable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setBackground(new Color(244,234,251)); //change color
        instructionsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14)); //change font

        // Back button
        JButton back = new JButton("Back");
        back.setFont(new Font("Segoe UI", Font.BOLD, 16)); //change font
        back.setBackground(new Color(177,149,192)); //change color
        back.setForeground(new Color(255, 255, 255)); //change color
        back.setBorder(BorderFactory.createLineBorder(new Color(137, 101, 157), 2)); //change color
        back.addActionListener(e -> instructionsWindow.dispose()); // closes popup

        // Add to layout
        instructionsWindow.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);
        instructionsWindow.add(back, BorderLayout.SOUTH);

        // Center popup
        instructionsWindow.setLocationRelativeTo(this);
        instructionsWindow.setVisible(true);
        /*JOptionPane.showMessageDialog(
                this,
                """
                Linguistic Analysis Tool

                1. Import two text files
                2. Enter optional titles
                3. Run analysis
                4. Review similarity metrics

                Designed for stylometric analysis.
                """
        );*/
    }

    private void openWebsite() {

        try {
            Desktop.getDesktop().browse(
                    new URI("https://github.com/MC3685/LaTx"));
        } catch(Exception ignored) {}
    }
}