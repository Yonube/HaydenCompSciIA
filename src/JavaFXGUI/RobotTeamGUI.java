package src.JavaFXGUI;

import src.JavaFXGUI.GraphsFolder.ScatterGraphPanel;
import src.OOPBackEnd.RobotTeam;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class RobotTeamGUI implements ActionListener {
    // Class-level variables
    private RobotTeam robotTeam;
    private JFrame frame;
    private JList<String> attributesList;
    private JPanel panel;
    private JLabel teamNameLabel;
    private DefaultListModel<String> listModel;
    private JPanel robotPanel;
    private JLabel robotImageLabel;
    private ImageIcon robotImageIcon;
    private JScrollPane scrollPane;
    private JButton closeButton;
    private int imageWidth;
    private int imageHeight;

    public RobotTeamGUI(RobotTeam robotTeam) {
        this.robotTeam = robotTeam;
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Robot Team Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        // Initialize the panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Initialize the team name label
        teamNameLabel = new JLabel(robotTeam.getTeamName(), JLabel.CENTER);
        teamNameLabel.setFont(new Font("Arial", Font.BOLD, 64));
        panel.add(teamNameLabel, BorderLayout.NORTH);

        // Initialize the list model and populate it with attributes
        listModel = new DefaultListModel<>();
        listModel.addElement("Team Number: " + robotTeam.getTeamNumber());
        listModel.addElement("Team Name: " + robotTeam.getTeamName());
        listModel.addElement("Wins: " + robotTeam.getWins());
        listModel.addElement("Losses: " + robotTeam.getLosses());
        listModel.addElement("Draws: " + robotTeam.getDraws());
        listModel.addElement("Total Matches Played: " + robotTeam.getTotalMatchesPlayed());
        listModel.addElement("Breakdowns: " + robotTeam.getBreakdowns());
        listModel.addElement("Stuck Game Pieces: " + robotTeam.getStuckGamePieces());
        listModel.addElement("Total Coral Points: " + robotTeam.getTotalCoralPoints());
        listModel.addElement("Total Algae Points: " + robotTeam.getTotalAlgaePoints());
        JButton changeNameButton = new JButton("Change Name");
        changeNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(frame, "Enter new team name:", "Change Team Name",
                        JOptionPane.PLAIN_MESSAGE);
                if (newName != null && !newName.trim().isEmpty()) {
                    robotTeam.setTeamName(newName);
                    teamNameLabel.setText(newName);
                    listModel.setElementAt("TeamName: " + newName, 1); // Update the list model
                }
            }
        });

        attributesList = new JList<>(listModel);
        scrollPane = new JScrollPane(attributesList);
        scrollPane.add(changeNameButton);
        panel.add(scrollPane, BorderLayout.WEST);

        // Initialize the robot panel
        robotPanel = new JPanel();
        robotPanel.setLayout(new BorderLayout());
        panel.add(robotPanel, BorderLayout.CENTER);

        // Add a label for the robot image
        robotImageIcon = new ImageIcon("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".png");
        if (robotImageIcon.getIconWidth() == -1) { // Check if the image is invalid
            robotImageIcon = new ImageIcon("src/ImagesandSerialization/generic.png"); // Use generic image
        }
        robotImageLabel = new JLabel(robotImageIcon);

        // Scale the image to fit the window
        imageWidth = 400;
        imageHeight = 400;
        Image scaledImage = robotImageIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        robotImageIcon = new ImageIcon(scaledImage);
        robotImageLabel.setIcon(robotImageIcon);
        robotPanel.add(robotImageLabel, BorderLayout.CENTER);

        java.util.List<Integer> matchPoints = java.util.List.of(10, 18, 12, 25, 20, 30);
        ScatterGraphPanel graphPanel = new ScatterGraphPanel(matchPoints);
        graphPanel.setPreferredSize(new Dimension(400, 300));
        robotPanel.add(graphPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);

        // Add a key listener for ESC to close the frame
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose(); // Close the frame
                    return true; // Event consumed
                }
                return false; // Event not consumed
            }
        });

        // Add a close button
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setBounds(350, 500, 100, 30);
        panel.add(closeButton, BorderLayout.SOUTH);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle any actions if needed
    }
}