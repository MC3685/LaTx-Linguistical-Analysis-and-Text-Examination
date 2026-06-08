package app;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;

    private JPanel contentPanel;

    private HomePanel homePanel;
    private ResultsPanel resultsPanel;
    private SettingsPanel settingsPanel;
    private NavigationPanel navigationPanel;

    public MainFrame() {

        setTitle("Linguistic Analysis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400,800  );
        setLocationRelativeTo(null);

        setUndecorated(false);

        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(this);

        resultsPanel = new ResultsPanel();

        settingsPanel = new SettingsPanel(this);

        navigationPanel = new NavigationPanel(this);

        contentPanel.add(homePanel,"HOME");
        contentPanel.add(resultsPanel,"RESULTS");
        contentPanel.add(settingsPanel,"SETTINGS");

        setLayout(new BorderLayout());

        add(navigationPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void refreshTheme() {

        homePanel.applyTheme();

        resultsPanel.applyTheme();

        settingsPanel.applyTheme();

        navigationPanel.applyTheme();

        SwingUtilities.updateComponentTreeUI(this);

        repaint();
    }

    public void showHome() {
        cardLayout.show(contentPanel,"HOME");
    }

    public void showResults() {
        cardLayout.show(contentPanel,"RESULTS");
    }

    public ResultsPanel getResultsPanel() {
        return resultsPanel;
    }

    public void showSettings() {
        cardLayout.show(contentPanel,"SETTINGS");
    }
}