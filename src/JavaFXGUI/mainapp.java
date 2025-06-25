package src.JavaFXGUI;
import src.OOPBackEnd.RobotTeam;
import src.JavaFXGUI.driveTeamGUI;
import src.OOPBackEnd.Matches;
import src.OOPBackEnd.Scanner;
import java.awt.Color;
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
	private static JPanel matchesPanel;
	private static JScrollPane m_scrollPane;

	public static void showMainAppGUI() {

		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		panel.setLayout(null);

        titleLabel = new JLabel("Robotics Scouter App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(550, 10, 400, 30);
        panel.add(titleLabel);

		// Create a scrollable panel for robot teams
		robotTeamsPanel = new JPanel();
		robotTeamsPanel.setLayout(new BoxLayout(robotTeamsPanel, BoxLayout.Y_AXIS));
		robotTeamsPanel.setBounds(1140, 50, 300, 1000); // Position on the right side
		robotTeamsPanel.setBorder(BorderFactory.createTitledBorder("Robot Teams"));

		// Create a JScrollPane to make the panel scrollable
		scrollPane = new JScrollPane(robotTeamsPanel);
		scrollPane.setBounds(1140, 50, 300, 1000);
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
				// Create a scrollable panel for Matches
		matchesPanel = new JPanel();
		matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));
		matchesPanel.setBounds(0, 50, 300, 1000); // Position on the right side
		matchesPanel.setBorder(BorderFactory.createTitledBorder("Matches"));

		// Create a JScrollPane to make the panel scrollable
		m_scrollPane = new JScrollPane(matchesPanel);
		m_scrollPane.setBounds(0, 50, 300, 1000);
		m_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(m_scrollPane);

		// Add labels for each robot team
		for (int i = 1; i < Matches.getAllMatches().length; i++) { // Assuming Scanner has a method to get all matches
			final int matchIndex = i; // Declare a final variable to hold the current index
			// Create a button for each match
			JButton matchButton = new JButton("Match " + (matchIndex));
			matchButton.setFont(new Font("Arial", Font.PLAIN, 32));
			if(Matches.getAllMatches()[matchIndex].getIsPopulated() == false){
				matchButton.setForeground(Color.GRAY);
			}
			matchesPanel.add(matchButton);
			matchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent r) {
				MatchGUI matchGUI = new MatchGUI(Matches.getAllMatches()[matchIndex]); // Assuming MatchGUI takes a match object

			}
		});

		}
		// Add a button to refresh the GUI
		refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Arial", Font.PLAIN, 32));
		refreshButton.setBounds(1180, 0, 300, 50);
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