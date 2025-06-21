package src.JavaFXGUI;
import src.OOPBackEnd.RobotTeam;
import src.JavaFXGUI.driveTeamGUI;
import src.OOPBackEnd.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainapp implements ActionListener {
	private static JFrame frame;
	private static JPanel panel;
    private static JLabel titleLabel;
	private static JButton refreshButton;
	private static JPanel robotTeamsPanel;
	private static JScrollPane scrollPane;

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
		robotTeamsPanel = new JPanel();
		robotTeamsPanel.setLayout(new BoxLayout(robotTeamsPanel, BoxLayout.Y_AXIS));
		robotTeamsPanel.setBounds(1280, 50, 300, 1000); // Position on the right side
		robotTeamsPanel.setBorder(BorderFactory.createTitledBorder("Robot Teams"));

		// Create a JScrollPane to make the panel scrollable
		scrollPane = new JScrollPane(robotTeamsPanel);
		scrollPane.setBounds(1280, 50, 300, 1000);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);

		// Add labels for each robot team
		for (String teamName : RobotTeam.getAllTeamNames()) { // Assuming RobotTeam has a method to get all team names
			// Create a button for each team
			JButton teamButton = new JButton(teamName);
			teamButton.setFont(new Font("Arial", Font.PLAIN, 32));
			robotTeamsPanel.add(teamButton);
			teamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.dispose(); // Close the current frame
                RobotTeamGUI robotTeamGUI = new RobotTeamGUI(Scanner.determineRobotTeam(teamName)); 
            }
        });

		}
		// Add a button to refresh the GUI
		refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Arial", Font.PLAIN, 32));
		refreshButton.setBounds(1300, 0, 300, 50);
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh(); 
			}
		});
		panel.add(refreshButton);
		
		
		
		frame.setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// No operation needed here; consider adding relevant logic if required
	}

	public static void refresh() {
		frame.dispose(); // Dispose of the current frame
		showMainAppGUI(); // Reopen the GUI
	}
}