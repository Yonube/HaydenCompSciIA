package src.JavaFXGUI;
import src.OOPBackEnd.RobotTeam;
import src.JavaFXGUI.driveTeamGUI;
import src.OOPBackEnd.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainapp implements ActionListener {

	private static JLabel success;
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JPasswordField passwordText;
	private static JLabel passwordLabel;
	private static JButton button;
	private static int failedAttempts = 0; // counter for failed attempts
    private static JLabel titleLabel;

	public static void showMainAppGUI() {

		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(1600, 1600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		panel.setLayout(null);

        titleLabel = new JLabel("Robotics Scouter App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 10, 400, 30);
        panel.add(titleLabel);

		// Create a scrollable panel for robot teams
		JPanel robotTeamsPanel = new JPanel();
		robotTeamsPanel.setLayout(new BoxLayout(robotTeamsPanel, BoxLayout.Y_AXIS));
		robotTeamsPanel.setBounds(1200, 50, 300, 500); // Position on the right side
		robotTeamsPanel.setBorder(BorderFactory.createTitledBorder("Robot Teams"));

		// Create a JScrollPane to make the panel scrollable
		JScrollPane scrollPane = new JScrollPane(robotTeamsPanel);
		scrollPane.setBounds(1200, 50, 300, 500);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);

		// Add labels for each robot team
		for (String teamName : RobotTeam.getAllTeamNames()) { // Assuming RobotTeam has a method to get all team names
			// Create a button for each team
			JButton teamButton = new JButton(teamName);
			teamButton.setFont(new Font("Arial", Font.PLAIN, 16));
			robotTeamsPanel.add(teamButton);
			teamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                RobotTeamGUI robotTeamGUI = new RobotTeamGUI(Scanner.determineRobotTeam(teamName)); // Assuming RobotTeam has a method to get a team by name
            }
        });
		}

		// userLabel = new JLabel("User");
		// userLabel.setBounds(10, 20, 80, 25);
		// panel.add(userLabel);

		// userText = new JTextField();
		// userText.setBounds(100, 20, 165, 25);
		// panel.add(userText);

		// passwordLabel = new JLabel("Log Stats:");
		// passwordLabel.setBounds(10, 50, 80, 25);
		// panel.add(passwordLabel);

		// passwordText = new JPasswordField();
		// passwordText.setBounds(100, 50, 165, 25);
		// panel.add(passwordText);

		// button = new JButton("Login");
		// button.setBounds(10, 80, 80, 25);
		// button.addActionListener(new mainapp());
		// panel.add(button);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// No operation needed here; consider adding relevant logic if required
	}
}