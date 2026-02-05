package src.JavaFXGUI;

import src.Main;
import src.JavaFXGUI.GraphsFolder.JFreePieChartPanel;
import src.OOPBackEnd.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

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

	public static int width = 1500;
	public static int height = 900;

	// ===== COLORS =====
	public static final Color BACKGROUND = new Color(30, 30, 30);
	public static final Color PANEL_BG = new Color(45, 45, 48);
	public static final Color BORDER = new Color(60, 60, 60);
	public static final Color TEXT = new Color(230, 230, 230);
	public static final Color TEXT_DISABLED = new Color(150, 150, 150);
	public static final Color BUTTON_IDLE = new Color(45, 45, 48);
	public static final Color BUTTON_HOVER = new Color(79, 195, 247);

	// ===== FONTS =====
	public static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 40);
	public static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 18);
	public static final Font LIST_FONT = new Font("Segoe UI", Font.PLAIN, 20);

	public Mainapp() {
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showMainAppGUI();
	}

	public static void showMainAppGUI() {

		panel = new JPanel(null);
		panel.setBackground(BACKGROUND);
		panel.setBorder(BorderFactory.createLineBorder(BORDER));
		frame.add(panel);

		titleLabel = new JLabel(" Scout-O-Matic 3000");
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(TEXT);
		titleLabel.setBounds(500, 10, 600, 45);
		panel.add(titleLabel);

		RobotTeam[] topTeams = Scanner.calculateUpToTop5RobotTeams();
		java.util.List<RobotTeam> topTeamsList = new java.util.ArrayList<>();
		if (topTeams != null) {
			for (RobotTeam rt : topTeams) {
				if (rt != null)
					topTeamsList.add(rt);
			}
		}

		JFreePieChartPanel centerGraph = new JFreePieChartPanel(topTeamsList, "Top 5 Teams by Total Points");
		centerGraph.setBounds(320, 60, 780, 600);
		centerGraph.chart.setBackgroundPaint(BACKGROUND);
		centerGraph.chartPanel.setBackground(PANEL_BG);
		panel.add(centerGraph);

		// ===== ROBOT TEAMS PANEL =====
		robotTeamsPanel = new JPanel();
		robotTeamsPanel.setLayout(new BoxLayout(robotTeamsPanel, BoxLayout.Y_AXIS));
		robotTeamsPanel.setBackground(PANEL_BG);

		TitledBorder robotBorder = BorderFactory.createTitledBorder("Robot Teams");
		robotBorder.setTitleColor(TEXT);
		robotTeamsPanel.setBorder(robotBorder);

		scrollPane = new JScrollPane(robotTeamsPanel);
		scrollPane.setBounds(1140, 50, 300, 800);
		scrollPane.getViewport().setBackground(PANEL_BG);
		scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
		panel.add(scrollPane);

		for (RobotTeam robot : RobotTeam.AllTeams) {
			if (robot == null)
				continue;

			JButton teamButton = createListButton(
					robot.getTeamNumber() + " (" + robot.getTeamName() + ")");

			teamButton.addActionListener(e -> new RobotTeamGUI(Scanner.determineRobotTeam(robot.getTeamName())));

			robotTeamsPanel.add(teamButton);
		}

		// ===== MATCHES PANEL =====
		matchesPanel = new JPanel();
		matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));
		matchesPanel.setBackground(PANEL_BG);

		TitledBorder matchBorder = BorderFactory.createTitledBorder("Matches");
		matchBorder.setTitleColor(TEXT);
		matchesPanel.setBorder(matchBorder);

		m_scrollPane = new JScrollPane(matchesPanel);
		m_scrollPane.setBounds(0, 50, 300, 800);
		m_scrollPane.getViewport().setBackground(PANEL_BG);
		m_scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
		panel.add(m_scrollPane);

		for (int i = 1; i < Matches.getAllMatches().length; i++) {
			final int matchIndex = i;

			JButton matchButton = createListButton("Match " + matchIndex);

			if (!Matches.getAllMatches()[matchIndex].getIsPopulated()) {
				matchButton.setForeground(TEXT_DISABLED);
			}

			matchButton.addActionListener(e -> {
				try {
					new MatchGUI(Matches.getAllMatches()[matchIndex]);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(
							frame,
							"Error opening match.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			matchesPanel.add(matchButton);
		}

		// ===== TOP BUTTONS =====
		refreshButton = createActionButton("Refresh");
		refreshButton.setBounds(1180, 0, 300, 50);
		refreshButton.addActionListener(e -> refresh());
		panel.add(refreshButton);

		inputFileButton = createActionButton("Input File");
		inputFileButton.setBounds(0, 0, 200, 50);
		inputFileButton.addActionListener(e -> handlefileInput());
		panel.add(inputFileButton);

		// ===== INPUT DATA =====
		inputDataField = new JTextField("Paste QR Scout Content Here");
		inputDataField.setFont(UI_FONT);
		inputDataField.setBackground(PANEL_BG);
		inputDataField.setForeground(TEXT);
		inputDataField.setCaretColor(TEXT);
		inputDataField.setBorder(BorderFactory.createLineBorder(BORDER));
		inputDataField.setBounds(300, height - 60, 600, 40);
		inputDataField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				inputDataField.setText("");
			}

			public void focusLost(FocusEvent e) {
				// nothing
			}
		});

		panel.add(inputDataField);

		inputDataButton = createActionButton("Input Data");
		inputDataButton.setBounds(920, height - 60, 200, 40);
		inputDataButton.addActionListener(e -> {
			try {
				Scanner.FileDataToRobotTeamTSV(inputDataField.getText(), frame);
				refresh();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		panel.add(inputDataButton);

		frame.setVisible(true);
	}

	// ===== BUTTON FACTORIES =====
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

	public static void refresh() {
		frame.getContentPane().removeAll();
		showMainAppGUI();
		frame.revalidate();
		frame.repaint();
		Main.rtList.serialize();
		Main.mList.serialize();
	}

	public static void handlefileInput() {
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
