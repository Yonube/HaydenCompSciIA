package src.JavaFXGUI;
import src.OOPBackEnd.RobotTeam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RobotTeamGUI implements ActionListener {
    private RobotTeam robotTeam;
    private JFrame frame;
    private JList<String> attributesList;

    public RobotTeamGUI(RobotTeam robotTeam) {
        this.robotTeam = robotTeam;
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Robot Team Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add the team name at the top
        JLabel teamNameLabel = new JLabel(robotTeam.getTeamName(), JLabel.CENTER);
        teamNameLabel.setFont(new Font("Arial", Font.BOLD, 64));
        panel.add(teamNameLabel, BorderLayout.NORTH);

        // Create a list to display attributes
        DefaultListModel<String> listModel = new DefaultListModel<>();
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
        listModel.addElement("Total Points: " + robotTeam.getTotalPoints());
        listModel.addElement("Has Auton: " + robotTeam.hasAuton());
        listModel.addElement("Has Teleop: " + robotTeam.hasTeleop());
        listModel.addElement("Can Deep Climb: " + robotTeam.canDeepClimb());
        listModel.addElement("Can Shallow Climb: " + robotTeam.canShallowClimb());
        listModel.addElement("Can Remove Algae: " + robotTeam.canRemoveAlgae());
        listModel.addElement("Can Defend: " + robotTeam.canDefend());
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        attributesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(attributesList);
        panel.add(scrollPane, BorderLayout.WEST);

        // // Create a panel for the pie chart
        // JPanel pieChartPanel = new JPanel() {
        //     @Override
        //     protected void paintComponent(Graphics g) {
        //     super.paintComponent(g);
        //     Graphics2D g2d = (Graphics2D) g;

        //     // Calculate total points
        //     int totalPoints = robotTeam.getTotalPoints();
        //     int algaePoints = robotTeam.getTotalAlgaePoints();
        //     int coralPoints = robotTeam.getTotalCoralPoints();

        //     // Calculate angles for the pie chart
        //     int algaeAngle = (int) Math.round((double) algaePoints / totalPoints * 360);
        //     int coralAngle = (int) Math.round((double) coralPoints / totalPoints * 360);
        //     int remainingAngle = 360 - algaeAngle - coralAngle;

        //     // Draw the pie chart
        //     int x = 200, y = 100, width = 300, height = 300;
        //     g2d.setColor(Color.GREEN);
        //     g2d.fillArc(x, y, width, height, 0, algaeAngle);
        //     g2d.setColor(Color.ORANGE);
        //     g2d.fillArc(x, y, width, height, algaeAngle, coralAngle);
        //     g2d.setColor(Color.GRAY);
        //     g2d.fillArc(x, y, width, height, algaeAngle + coralAngle, remainingAngle);

        //     // Add labels
        //     g2d.setColor(Color.BLACK);
        //     g2d.drawString("Algae Points", x + width + 20, y + 50);
        //     g2d.drawString("Coral Points", x + width + 20, y + 70);
        //     g2d.drawString("Other Points", x + width + 20, y + 90);
        //     }
        // };

        // panel.add(pieChartPanel, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);
       
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose(); // Close the frame
                    return true; // Event consumed
                }
                return false; // Event not consumed
        }});


        //Close Button
        JButton closeButton = new JButton("Close");
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