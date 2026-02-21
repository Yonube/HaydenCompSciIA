package src.JavaFXGUI;

import src.Main;
import src.JavaFXGUI.GraphsFolder.JFreePieChartPanel;
import src.OOPBackEnd.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Mainapp {

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
	private static JButton inputFileButton;
	private static JButton clearDataButton;

	public static int width = 1500;
	public static int height = 900;

	// COLORS
	public static final Color BACKGROUND = new Color(30, 30, 30);
	public static final Color PANEL_BG = new Color(45, 45, 48);
	public static final Color BORDER = new Color(60, 60, 60);
	public static final Color TEXT = new Color(230, 230, 230);
	public static final Color TEXT_DISABLED = new Color(150, 150, 150);
	public static final Color BUTTON_IDLE = new Color(45, 45, 48);
	public static final Color BUTTON_HOVER = new Color(79, 195, 247);

	// FONTS
	public static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 40);
	public static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 18);
	public static final Font LIST_FONT = new Font("Segoe UI", Font.PLAIN, 20);

	public Mainapp() {
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showMainAppGUI();
	}

	// Add all the content to the GUI
	public static void showMainAppGUI() {

		// Main panel
		panel = new JPanel(null);
		panel.setBackground(BACKGROUND);
		panel.setBorder(BorderFactory.createLineBorder(BORDER));
		frame.add(panel);

		// Title
		titleLabel = new JLabel(" Scout-O-Matic 3000");
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(TEXT);
		titleLabel.setBounds(500, 10, 600, 45);
		panel.add(titleLabel);

		// Get the top 5 performing teams by total points
		java.util.List<RobotTeam> topTeamsList = getTopTeamsList();

		// Create the pie chart
		JFreePieChartPanel centerGraph = new JFreePieChartPanel(topTeamsList, "Top 5 Teams by Total Points");
		centerGraph.setBounds(320, 60, 780, 600);
		centerGraph.chart.setBackgroundPaint(BACKGROUND);
		centerGraph.chartPanel.setBackground(PANEL_BG);
		panel.add(centerGraph);

		// || ROBOT TEAMS PANEL ||
		robotTeamsPanel = new JPanel();
		robotTeamsPanel.setLayout(new BoxLayout(robotTeamsPanel, BoxLayout.Y_AXIS));
		robotTeamsPanel.setBackground(PANEL_BG);

		// Title
		TitledBorder robotBorder = BorderFactory.createTitledBorder("Robot Teams");
		robotBorder.setTitleColor(TEXT);
		robotTeamsPanel.setBorder(robotBorder);

		// Scroll pane for the robot teams panel
		scrollPane = new JScrollPane(robotTeamsPanel);
		scrollPane.setBounds(1140, 50, 300, 800);
		scrollPane.getViewport().setBackground(PANEL_BG);
		scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
		panel.add(scrollPane);

		// Populate the robot teams panel with buttons leading to their respective robot
		// team GUIs
		populateRTPanelWithButtons();

		// || MATCHES PANEL ||
		matchesPanel = new JPanel();
		matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));
		matchesPanel.setBackground(PANEL_BG);

		// Title for matches panel
		TitledBorder matchBorder = BorderFactory.createTitledBorder("Matches");
		matchBorder.setTitleColor(TEXT);
		matchesPanel.setBorder(matchBorder);

		// Scroll pane for matches panel
		m_scrollPane = new JScrollPane(matchesPanel);
		m_scrollPane.setBounds(0, 50, 300, 800);
		m_scrollPane.getViewport().setBackground(PANEL_BG);
		m_scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
		// Attempt to customize scroll bar
		m_scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(70, 70, 70); // draggable part
				this.trackColor = new Color(45, 45, 48); // background track
			}
		});
		panel.add(m_scrollPane);

		// Populate the matches panel with buttons leading to their respective match
		// GUIs
		populateMPanelWithButtons();

		// TOP BUTTONS
		refreshButton = createActionButton("Refresh");
		refreshButton.setBounds(1180, 0, 300, 50);
		refreshButton.addActionListener(e -> refresh());
		panel.add(refreshButton);

		inputFileButton = createActionButton("Input File");
		inputFileButton.setBounds(0, 0, 200, 50);
		inputFileButton.addActionListener(e -> handleFileInput());
		panel.add(inputFileButton);

		// CLEAR DATA - bottom-left corner
		clearDataButton = createActionButton("Clear Data");
		clearDataButton.setBounds(0, height - 50, 200, 40);
		clearDataButton.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(frame,
					"Warning: Clearing saved data will delete 'robotteam.data' and 'match.data' and the application will close to clear in-memory data.\n\nDo you want to continue?",
					"Confirm Clear Data (App will close)",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				// Ensure latest data is serialized before deletion
				try {
					if (Main.rtList != null)
						Main.rtList.serialize();
					if (Main.mList != null)
						Main.mList.serialize();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Failed to serialize data before clearing: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Delete files
				boolean r1 = true, r2 = true;
				java.io.File robotFile = new java.io.File("robotteam.data");
				java.io.File matchFile = new java.io.File("match.data");
				if (robotFile.exists()) {
					r1 = robotFile.delete();
				}
				if (matchFile.exists()) {
					r2 = matchFile.delete();
				}
				if (r1 && r2) {
					JOptionPane.showMessageDialog(frame,
							"Data cleared (files deleted). The application will now close.",
							"Clear Data", JOptionPane.INFORMATION_MESSAGE);
				} else if (r1 || r2) {
					JOptionPane.showMessageDialog(frame,
							"Partial clear: one of the data files was deleted. The application will now close.",
							"Clear Data", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame,
							"No data files were found or deletion failed. The application will now close.",
							"Clear Data", JOptionPane.INFORMATION_MESSAGE);
				}
				// Close all windows and exit so in-memory data is cleared
				java.awt.Window[] windows = java.awt.Window.getWindows();
				for (java.awt.Window window : windows) {
					window.dispose();
				}
				System.exit(0);
			}
		});
		panel.add(clearDataButton);

		// INPUT DATA - Text Field and Button
		inputDataField = new JTextField("Paste QR Scout Content Here");
		inputDataField.setFont(UI_FONT);
		inputDataField.setBackground(PANEL_BG);
		inputDataField.setForeground(TEXT);
		inputDataField.setCaretColor(TEXT);
		inputDataField.setBorder(BorderFactory.createLineBorder(BORDER));
		inputDataField.setBounds(300, height - 50, 600, 40);
		// Clear the text field on focus (when clicked)
		inputDataField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				inputDataField.setText("");
			}

			public void focusLost(FocusEvent e) {
				if (inputDataField.getText().isEmpty()) {
					inputDataField.setText("Paste QR Scout Content Here");
				}
			}
		});

		panel.add(inputDataField);

		inputDataButton = createActionButton("Input Data");
		inputDataButton.setBounds(920, height - 50, 200, 40);
		// Commence input data processing
		inputDataButton.addActionListener(e -> {
			try {
				Scanner.FileDataToRobotTeamTSV(inputDataField.getText(), frame);
				refresh();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		panel.add(inputDataButton);

		// Make it seen
		frame.setVisible(true);
	}

	private static void populateMPanelWithButtons() {
		for (int i = 1; i < Matches.getAllMatches().length; i++) {
			final int matchIndex = i;

			JButton matchButton = createListButton("Match " + matchIndex);

			if (!Matches.getAllMatches()[matchIndex].getIsPopulated()) {
				matchButton.setForeground(TEXT_DISABLED);
			}

			matchButton.addActionListener(e -> {
				new MatchGUI(Matches.getAllMatches()[matchIndex]);
			});

			matchesPanel.add(matchButton);
		}
	}

	private static void populateRTPanelWithButtons() {
		for (RobotTeam robot : RobotTeam.AllTeams) {
			if (robot == null)
				continue;
			JButton teamButton = createListButton(robot.getTeamNumber() + " (" + robot.getTeamName() + ")");
			teamButton.addActionListener(e -> new RobotTeamGUI(Scanner.determineRobotTeam(robot.getTeamName())));
			robotTeamsPanel.add(teamButton);
		}
	}

	private static java.util.List<RobotTeam> getTopTeamsList() {
		RobotTeam[] topTeams = Scanner.calculateUpToTop5RobotTeams();
		java.util.List<RobotTeam> topTeamsList = new java.util.ArrayList<>();
		if (topTeams != null) {
			for (RobotTeam rt : topTeams) {
				if (rt != null)
					topTeamsList.add(rt);
			}
		}
		return topTeamsList;
	}

	// BUTTON METHODS
	private static JButton createListButton(String text) {
		JButton b = new JButton(text);

		b.setFont(LIST_FONT);
		b.setBackground(BUTTON_IDLE);
		b.setForeground(TEXT);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setOpaque(true);
		b.setAlignmentX(Component.LEFT_ALIGNMENT);

		b.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (b.isEnabled()) {
					b.setBackground(BUTTON_HOVER);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (b.isEnabled()) {
					b.setBackground(BUTTON_IDLE);
				}
			}
		});

		return b;
	}

	private static JButton createActionButton(String text) {
		JButton b = new JButton(text);
		b.setFont(UI_FONT);
		b.setBackground(BUTTON_HOVER);
		b.setForeground(TEXT);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		return b;
	}

	// Refresh the main application GUI and save data
	public static void refresh() {
		frame.getContentPane().removeAll();
		showMainAppGUI();
		frame.revalidate();
		frame.repaint();
		Main.rtList.serialize();
		Main.mList.serialize();
	}

	// Handle file input for data import
	public static void handleFileInput() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("TSV/CSV Files", "tsv", "csv",
				"txt", "xls", "xlsx"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile == null || !selectedFile.exists()) {
			JOptionPane.showMessageDialog(frame, "The selected file does not exist.", "File not found",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String name = selectedFile.getName().toLowerCase();
		try {
			java.util.List<String> lines = Files.readAllLines(selectedFile.toPath());
			if (name.endsWith(".tsv")) {
				for (String line : lines) {
					try {
						Scanner.FileDataToRobotTeamTSV(line, frame);
					} catch (Exception ex) {
						System.err.println("An error occurred while processing line: " + line);
						ex.printStackTrace();
					}
				}
				refresh();
				return;
			} else if (name.endsWith(".csv")) {
				for (String line : lines) {
					try {
						Scanner.FileDataToRobotTeamCSV(line, frame);
					} catch (Exception ex) {
						System.err.println("An error occurred while processing line: " + line);
						ex.printStackTrace();
					}
				}
				refresh();
				return;
			} else {
				JOptionPane.showMessageDialog(frame,
						"Selected file type is not yet supported. Please use a .tsv or .csv file.",
						"Unsupported file type", JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (IOException e) {
			System.err.println("An error occurred while reading the file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
