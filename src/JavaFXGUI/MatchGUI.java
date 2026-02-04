package src.JavaFXGUI;

import src.OOPBackEnd.Matches;
import src.OOPBackEnd.RobotTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MatchGUI implements ActionListener {

    private Matches match;
    private JFrame frame;
    private JPanel panel;
    private JLabel matchNumberLabel;

    private final int imageWidth = 50;
    private final int imageHeight = 50;

    // ===== COLORS (Mainapp theme) =====
    private static final Color BACKGROUND = new Color(30, 30, 30);
    private static final Color PANEL_BG = new Color(45, 45, 48);
    private static final Color BORDER = new Color(60, 60, 60);
    private static final Color TEXT = new Color(230, 230, 230);

    // ===== FONTS =====
    private static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 40);
    private static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public MatchGUI(Matches match) {
        this.match = match;
        setupGUI();
    }

    private int[] getTeamPoints(RobotTeam team, int matchNum) {
        int[] pts = new int[] { 0, 0, 0 };
        if (team == null)
            return pts;
        try {
            pts[0] = team.getTotalCoralPointsInMatch(matchNum);
        } catch (Exception e) {
        }
        try {
            pts[1] = team.getTotalAlgaePointsInMatch(matchNum);
        } catch (Exception e) {
        }
        pts[2] = pts[0] + pts[1];
        return pts;
    }

    public void setupGUI() {

        frame = new JFrame("Match Details");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BACKGROUND);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        frame.add(panel);

        // ===== TITLE =====
        matchNumberLabel = new JLabel("Match Number: " + match.getMatchNumber(), JLabel.CENTER);
        matchNumberLabel.setFont(TITLE_FONT);
        matchNumberLabel.setForeground(TEXT);
        matchNumberLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(matchNumberLabel, BorderLayout.NORTH);

        // ===== LIST =====
        DefaultListModel<String> listModel = new DefaultListModel<>();
        int mNum = match.getMatchNumber();
        listModel.addElement("Match Number: " + mNum);

        int blueTotal = 0;
        int redTotal = 0;

        RobotTeam[] blues = { match.getBlue1(), match.getBlue2(), match.getBlue3() };
        RobotTeam[] reds = { match.getRed1(), match.getRed2(), match.getRed3() };

        for (int i = 0; i < 3; i++) {
            if (blues[i] != null) {
                int[] p = getTeamPoints(blues[i], mNum);
                listModel.addElement("Blue Team " + (i + 1) + ": " + blues[i].getTeamNumber());
                listModel.addElement("  Coral: " + p[0]);
                listModel.addElement("  Algae: " + p[1]);
                listModel.addElement("  Total: " + p[2]);
                blueTotal += p[2];
            }
            if (reds[i] != null) {
                int[] p = getTeamPoints(reds[i], mNum);
                listModel.addElement("Red Team " + (i + 1) + ": " + reds[i].getTeamNumber());
                listModel.addElement("  Coral: " + p[0]);
                listModel.addElement("  Algae: " + p[1]);
                listModel.addElement("  Total: " + p[2]);
                redTotal += p[2];
            }
        }

        match.setBlueScore(blueTotal);
        match.setRedScore(redTotal);

        listModel.addElement("Winning Alliance: " +
                (blueTotal > redTotal ? "Blue" : redTotal > blueTotal ? "Red" : "Tie"));
        listModel.addElement("Blue Score: " + blueTotal);
        listModel.addElement("Red Score: " + redTotal);

        JList<String> matchDetailsList = new JList<>(listModel);
        matchDetailsList.setFont(UI_FONT);
        matchDetailsList.setBackground(PANEL_BG);
        matchDetailsList.setForeground(TEXT);

        JScrollPane scrollPane = new JScrollPane(matchDetailsList);
        scrollPane.getViewport().setBackground(PANEL_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        panel.add(scrollPane, BorderLayout.WEST);

        // ===== CENTER AREA =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND);

        JLabel centerImage = new JLabel(new ImageIcon("src/ImagesAndSerialization/2025_REEFSCAPE.png"));
        centerImage.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(centerImage, BorderLayout.CENTER);

        // ===== IMAGE PANELS =====
        JPanel BlueImagePanel = new JPanel();
        BlueImagePanel.setOpaque(false);
        BlueImagePanel.setLayout(new BoxLayout(BlueImagePanel, BoxLayout.Y_AXIS));
        BlueImagePanel.add(Box.createVerticalGlue());

        if (match.getBlue1() != null) {
            BlueImagePanel.add(createTeamImageLabel(match.getBlue1()));
            BlueImagePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        if (match.getBlue2() != null) {
            BlueImagePanel.add(createTeamImageLabel(match.getBlue2()));
            BlueImagePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        if (match.getBlue3() != null) {
            BlueImagePanel.add(createTeamImageLabel(match.getBlue3()));
        }

        BlueImagePanel.add(Box.createVerticalGlue());

        JPanel RedImagePanel = new JPanel();
        RedImagePanel.setOpaque(false);
        RedImagePanel.setLayout(new BoxLayout(RedImagePanel, BoxLayout.Y_AXIS));
        RedImagePanel.add(Box.createVerticalGlue());

        if (match.getRed1() != null) {
            RedImagePanel.add(createTeamImageLabel(match.getRed1()));
            RedImagePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        if (match.getRed2() != null) {
            RedImagePanel.add(createTeamImageLabel(match.getRed2()));
            RedImagePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        if (match.getRed3() != null) {
            RedImagePanel.add(createTeamImageLabel(match.getRed3()));
        }

        RedImagePanel.add(Box.createVerticalGlue());

        centerPanel.add(BlueImagePanel, BorderLayout.WEST);
        centerPanel.add(RedImagePanel, BorderLayout.EAST);

        panel.add(centerPanel, BorderLayout.CENTER);

        // ===== CLOSE BUTTON =====
        JButton closeButton = new JButton("Close");
        closeButton.setFont(UI_FONT);
        closeButton.setBackground(PANEL_BG);
        closeButton.setForeground(TEXT);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createLineBorder(BORDER));
        closeButton.addActionListener(e -> frame.dispose());
        panel.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JLabel createTeamImageLabel(RobotTeam team) {
        ImageIcon icon = new ImageIcon("src/ImagesAndSerialization/" + team.getTeamNumber() + ".png");
        if (icon.getIconWidth() == -1) {
            icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaled = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaled));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }
}
