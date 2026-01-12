package src.JavaFXGUI;

import src.Main;
import src.JavaFXGUI.GraphsFolder.JFreeChartScatterGraphPanel;
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

		titleLabel = new JLabel(" Robotics Scouter App");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		// titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		titleLabel.setBounds(500, 10, 500, 45);
		panel.add(titleLabel);

		JTextField mouseCoordinates = new JTextField("Mouse Coordinates: (0, 0)");
		mouseCoordinates.setHorizontalAlignment(JTextField.CENTER);
		mouseCoordinates.setFont(new Font("Arial", Font.PLAIN, 16));
		mouseCoordinates.setEditable(false);
		mouseCoordinates.setBounds(200, 0, 200, 30); // Position at the center of the panel
		panel.add(mouseCoordinates);

		// Add a MouseMotionListener to track mouse movement (Temporary)
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				mouseCoordinates.setText("Mouse Coordinates: (" + x + ", " + y + ")");
			}
		});

		// Create Pie Chart For Top 5 Teams By Points
		RobotTeam[] topTeams = Scanner.calculateUpToTop5RobotTeams();
		java.util.List<RobotTeam> topTeamsList = new java.util.ArrayList<>();
		if (topTeams != null) {
			for (RobotTeam rt : topTeams) {
				if (rt != null) {
					topTeamsList.add(rt);
				}
			}
		}
		// RobotTeamPoints[] topTeams = []
		// System.out.println(topTeams + " " + topTeams.length);
		// int[] topTeamPoints = new int[topTeams.length];
		// for (int i = 0; i < topTeams.length; i++) {
		// 	if (topTeams[i] != null) {
		// 		topTeamPoints[i] = topTeams[i].getTotalCoralPoints() + topTeams[i].getTotalAlgaePoints();
		// 	} else {
		// 		topTeamPoints[i] = 0;
		// 	}
		// }
		JFreeChartScatterGraphPanel centerGraph = new JFreeChartScatterGraphPanel(topTeamsList);
		centerGraph.setBounds(320, 60, 780, 600);
		centerGraph.setBorder(BorderFactory.createTitledBorder("Top Teams Scatter Graph"));
		panel.add(centerGraph);
		// System.out.println("Pie Chart Added");

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
			if (robot == null) {
				continue;
			}
			JButton teamButton = new JButton(robot.getTeamNumber() + " (" + robot.getTeamName() + ")");
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
		// @Override
		// public void actionPerformed(ActionEvent event) {
		// JPanel inputPanel = new JPanel(new GridLayout(2, 2));
		// JTextField teamNameField = new JTextField();
		// JTextField teamNumberField = new JTextField();

		// inputPanel.add(new JLabel("Team Name:"));
		// inputPanel.add(teamNameField);
		// inputPanel.add(new JLabel("Team Number:"));
		// inputPanel.add(teamNumberField);

		// int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Robot
		// Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		// if (result == JOptionPane.OK_OPTION) {
		// String teamName = teamNameField.getText();
		// String teamNumber = teamNumberField.getText();
		// try {
		// java.util.Scanner tempscanner = new java.util.Scanner("");
		// Scanner.createNewRobotTeam(Integer.parseInt(teamNumber), teamName,
		// tempscanner);
		// refresh(); // Refresh the GUI to show the new team
		// tempscanner.close();
		// } catch (NumberFormatException ex) {
		// JOptionPane.showMessageDialog(null, "Invalid team number. Please enter a
		// valid number.", "Error", JOptionPane.ERROR_MESSAGE);
		// } catch (Exception ex) {
		// JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(),
		// "Error", JOptionPane.ERROR_MESSAGE);
		// }
		// }
		// }
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
			if (Matches.getAllMatches()[matchIndex].getIsPopulated() == false) {
				matchButton.setForeground(Color.GRAY);
			}
			matchesPanel.add(matchButton);
			matchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent r) {
					@SuppressWarnings("unused")
					MatchGUI matchGUI = new MatchGUI(Matches.getAllMatches()[matchIndex]); // Assuming MatchGUI takes a
																							// match object

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
		// Add textbox and button to input data
		inputDataButton = new JButton("Input Data");
		inputDataButton.setFont(new Font("Arial", Font.PLAIN, 32));
		inputDataField = new JTextField();
		inputDataField.setFont(new Font("Arial", Font.PLAIN, 32));
		inputDataButton.setBounds(900, height - 50, 200, 50);
		inputDataField.setBounds(300, height - 50, 600, 50);
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

	public static void handlefileInput() {
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

	public static void addPieChart(int centerX, int centerY, int radius, int[] data) {
		// Calculate the total sum of the data
		java.util.concurrent.atomic.AtomicInteger total = new java.util.concurrent.atomic.AtomicInteger(0);
		for (int value : data) {
			total.addAndGet(value);
		}

		// Create a new JPanel to draw the pie chart
		JPanel pieChartPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				// Set rendering hints for better quality
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Start drawing the pie chart
				int startAngle = 0;
				for (int value : data) {
					// Calculate the angle for this slice
					int arcAngle = (int) Math.round((value * 360.0) / total.get());

					// Generate a random color for each slice
					g2d.setColor(new Color((int) (Math.random() * 0x1000000)));

					// Draw the slice
					g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);

					// Update the start angle for the next slice
					startAngle += arcAngle;
				}
			}
		};

		// Set the size of the panel to fit the pie chart
		pieChartPanel.setPreferredSize(new Dimension(centerX * 2, centerY * 2));

		// Add the pie chart panel to the main panel
		panel.add(pieChartPanel);
		panel.revalidate();
		panel.repaint();
	}
}