package app;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import components.FontLoader;



public class NavigationPanel extends JPanel {

    private static final Font HKModular =
            FontLoader.load("HKModular-Bold.otf", 1f);

    private static final Font Architype =
            FontLoader.load("Architype-Aubette.ttf", 1f);

    private final List<JButton> buttons = new ArrayList<>();
    private JDialog instructionsWindow;
    private JTextArea instructionsArea;

    public NavigationPanel(MainFrame frame) {

        setPreferredSize(new Dimension(250,0));

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

    public void applyTheme()
    {
        setBackground(Theme.SIDEBAR);
        for (JButton button : buttons) {
            button.setBackground(Theme.SIDEBAR);
            button.setForeground(Theme.PURPLE);
        }

        if (instructionsWindow != null) {

            ((JComponent) instructionsWindow.getContentPane())
                    .setBorder(BorderFactory.createLineBorder(
                            Theme.CARD, 5));

            if (instructionsArea != null) {
                instructionsArea.setBackground(Theme.BACKGROUND);
                instructionsArea.setForeground(Theme.TEXT);
            }
        }

    }

    private JButton navButton(String text, java.awt.event.ActionListener listener) {

        JButton button = new JButton(text);

        button.setFont(HKModular.deriveFont(15f));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(150,50));
        button.setAlignmentX(CENTER_ALIGNMENT);

        button.setBackground(Theme.SIDEBAR);
        button.setForeground(Theme.PURPLE);

        button.addActionListener(listener);

        buttons.add(button);

        return button;
    }

    private void showHelp(MainFrame frame) {

        instructionsWindow = new JDialog(frame, false);
        instructionsWindow.setSize(500, 500);
        instructionsWindow.setLocationRelativeTo(null);
        instructionsWindow.setLayout(new BorderLayout());
        ((JComponent) instructionsWindow.getContentPane()).setBorder(
                BorderFactory.createLineBorder(Theme.CARD, 5)); //change color - done


        instructionsArea = new JTextArea( // change intructions
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
        instructionsArea.setBackground(Theme.BACKGROUND); //change color - done
        instructionsArea.setForeground(Theme.TEXT);
        instructionsArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16)); //change font - done



        // Add to layout
        instructionsWindow.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);

        // Center popup
        instructionsWindow.setLocationRelativeTo(this);
        instructionsWindow.setVisible(true);

    }

    private void openWebsite() {

        try {
            Desktop.getDesktop().browse(
                    new URI("https://github.com/MC3685/LaTx"));
        } catch(Exception ignored) {}
    }
}