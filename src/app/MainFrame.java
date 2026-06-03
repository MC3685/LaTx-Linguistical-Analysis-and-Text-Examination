package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;

    private JPanel contentPanel;

    private HomePanel homePanel;
    private ResultsPanel resultsPanel;
    private JPanel settingsPanel;

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

        settingsPanel = new SettingsPanel();

        contentPanel.add(homePanel,"HOME");
        contentPanel.add(resultsPanel,"RESULTS");
        contentPanel.add(settingsPanel,"SETTINGS");

        setLayout(new BorderLayout());

        add(new NavigationPanel(this), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSettingsPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(Theme.BACKGROUND);

        return panel;
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