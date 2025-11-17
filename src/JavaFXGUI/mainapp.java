package src.JavaFXGUI;
import src.Main;
import src.OOPBackEnd.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Mainapp implements ActionListener {
	private static JFrame frame;
	private static JPanel panel;
    private static JLabel titleLabel;
	private static JButton refreshButton;
	private static JPanel robotTeamsPanel;
	private static JScrollPane scrollPane;
	private static JPanel matchesPanel;
	private static JScrollPane m_scrollPane;
	private static JButton inputDataButton;
	private static JTextField inputDataField;
	// private static JButton addTeamButton;
	private static JButton inputFileButton;
	public static int width = 1500;
	public static int height = 900;

	public Mainapp() {
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showMainAppGUI();
	}
	
	public static void showMainAppGUI() {
		panel = new JPanel();
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

		// Add buttons for each robot team
		for (RobotTeam robot : RobotTeam.AllTeams) { // Assuming RobotTeam has a method to get all team names
			// Create a button for each team
			if(robot == null){
				continue;
			}
			JButton teamButton = new JButton(robot.getTeamNumber()+ " (" + robot.getTeamName()+ ")");
			teamButton.setFont(new Font("Arial", Font.PLAIN, 32));
			robotTeamsPanel.add(teamButton);
			teamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RobotTeamGUI for the selected team
				new RobotTeamGUI(Scanner.determineRobotTeam(robot.getTeamName())); 
            }
			
        	});

		}
		// Add a button to add a new robot team
		// addTeamButton = new JButton("+");
		// System.out.println("Add Team Button Created");
		// addTeamButton.setFont(new Font("Arial", Font.PLAIN, 32));
		// System.out.println("Add Team Button Font Set");
		// robotTeamsPanel.add(addTeamButton);
		// System.out.println("Add Team Button Added to Panel");
		// addTeamButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		// addTeamButton.addActionListener(new ActionListener() {
		// 	@Override
		// 	public void actionPerformed(ActionEvent event) {
		// 		JPanel inputPanel = new JPanel(new GridLayout(2, 2));
		// 		JTextField teamNameField = new JTextField();
		// 		JTextField teamNumberField = new JTextField();

		// 		inputPanel.add(new JLabel("Team Name:"));
		// 		inputPanel.add(teamNameField);
		// 		inputPanel.add(new JLabel("Team Number:"));
		// 		inputPanel.add(teamNumberField);

		// 		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Robot Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		// 		if (result == JOptionPane.OK_OPTION) {
		// 			String teamName = teamNameField.getText();
		// 			String teamNumber = teamNumberField.getText();
		// 			try {
		// 				java.util.Scanner tempscanner = new java.util.Scanner("");
		// 				Scanner.createNewRobotTeam(Integer.parseInt(teamNumber), teamName, tempscanner);
		// 				refresh(); // Refresh the GUI to show the new team
		// 				tempscanner.close();
		// 			} catch (NumberFormatException ex) {
		// 				JOptionPane.showMessageDialog(null, "Invalid team number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
		// 			} catch (Exception ex) {
		// 				JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		// 			}
		// 		}
		// 	}
		// });
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
				@SuppressWarnings("unused")
				MatchGUI matchGUI = new MatchGUI(Matches.getAllMatches()[matchIndex]); // Assuming MatchGUI takes a match object

			}
		});

		}
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
		//Add textbox and button to input data
		inputDataButton = new JButton("Input Data");
		inputDataButton.setFont(new Font("Arial", Font.PLAIN, 32));
		inputDataField = new JTextField();
		inputDataField.setFont(new Font("Arial", Font.PLAIN, 32));
		inputDataButton.setBounds(900, height-50, 200, 50);
		inputDataField.setBounds(300, height-50, 600, 50);
		inputDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputData = inputDataField.getText();
				try {
					Scanner.QRdataToRobotTeam(inputData, new java.util.Scanner(System.in));
					refresh(); 
				} catch (Exception ex) {
					System.err.println("An error occurred: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		inputFileButton = new JButton("Input File");
		inputFileButton.setFont(new Font("Arial", Font.PLAIN, 32));
		inputFileButton.setBounds(0, 0, 200, 50);
		inputFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handlefileInput();
			}
		});
		panel.add(inputDataButton);
		panel.add(inputDataField);
		panel.add(inputFileButton);
	
		frame.setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void refresh() {
    // Clear the content pane of the frame
    
	frame.getContentPane().removeAll();

    // Reinitialize the components and layout
    showMainAppGUI();

    // Revalidate and repaint the frame to reflect changes
    frame.revalidate();
    frame.repaint();
	// Save data to files
	Main.rtList.serialize();
	Main.mList.serialize();
}


	public static void handlefileInput(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("TSV Files", "tsv"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				java.util.List<String> lines = Files.readAllLines(Path.of(selectedFile.getAbsolutePath()));
				for (String line : lines) {
					try {
						Scanner.FileDataToRobotTeam(line, frame);
					} catch (Exception ex) {
						System.err.println("An error occurred while processing line: " + line);
						ex.printStackTrace();
					}
				}
				refresh(); 
			} catch (IOException e) {
				System.err.println("An error occurred while reading the file: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}