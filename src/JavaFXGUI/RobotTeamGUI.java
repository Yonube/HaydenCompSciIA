package src.JavaFXGUI;

import src.JavaFXGUI.GraphsFolder.JFreeBarChartPanel;
import src.OOPBackEnd.Scanner;
import src.OOPBackEnd.RobotTeam;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RobotTeamGUI implements ActionListener {
    // ==================== COLORS AND FONTS ====================
    public static final Color BACKGROUND = new Color(30, 30, 30);
    public static final Color PANEL_BG = new Color(45, 45, 48);
    public static final Color BORDER = new Color(60, 60, 60);
    public static final Color TEXT = new Color(230, 230, 230);
    public static final Color BUTTON_IDLE = new Color(45, 45, 48);

    // ===== FONTS =====
    public static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 40);
    public static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font LIST_FONT = new Font("Segoe UI", Font.PLAIN, 15);

    // ==================== CLASS VARIABLES ====================
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
    private JButton editButton;
    private int imageWidth;
    private int imageHeight;

    public RobotTeamGUI(RobotTeam robotTeam) {
        this.robotTeam = robotTeam;
        setupGUI();
    }

    private void refreshAttributesList() {
        listModel.clear();
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
        listModel.addElement("Can Deep Climb: " + (robotTeam.canDeepClimb() ? "Yes" : "No"));
        listModel.addElement("Can Shallow Climb: " + (robotTeam.canShallowClimb() ? "Yes" : "No"));
        listModel.addElement("Can Remove Algae: " + (robotTeam.canRemoveAlgae() ? "Yes" : "No"));
        listModel.addElement("Has Auton: " + (robotTeam.hasAuton() ? "Yes" : "No"));
        listModel.addElement("Has Teleop: " + (robotTeam.hasTeleop() ? "Yes" : "No"));
        listModel.addElement("Notes:");
        for (String note : robotTeam.getNotes()) {
            if (note != null && !note.trim().isEmpty()) {
                listModel.addElement(note);
            }
        }
        if (attributesList != null) {
            attributesList.setModel(listModel);
        }
    }

    private void setupGUI() {
        frame = new JFrame("Robot Team Details");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700); // wider for better layout

        // Main panel
        panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        // Team name label at top
        teamNameLabel = new JLabel(robotTeam.getTeamName(), JLabel.CENTER);
        teamNameLabel.setFont(TITLE_FONT);
        teamNameLabel.setForeground(TEXT);
        panel.add(teamNameLabel, BorderLayout.NORTH);

        // Attributes list on the left
        listModel = new DefaultListModel<>();
        attributesList = new JList<>(listModel);
        attributesList.setBackground(PANEL_BG); // Dark background to match theme
        attributesList.setForeground(TEXT); // Light text for contrast
        attributesList.setFont(LIST_FONT);
        scrollPane = new JScrollPane(attributesList);
        scrollPane.getViewport().setBackground(PANEL_BG); // Match list background
        scrollPane.setPreferredSize(new Dimension(280, 0)); // Fix width for left panel

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(70, 70, 70); // draggable part
                this.trackColor = new Color(45, 45, 48); // background track
            }
        });

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(70, 70, 70);
                this.trackColor = new Color(45, 45, 48);
            }
        });

        panel.add(scrollPane, BorderLayout.WEST);

        refreshAttributesList();

        // Robot panel center with image and graph
        robotPanel = new JPanel();
        robotPanel.setBackground(BACKGROUND);
        robotPanel.setLayout(new BoxLayout(robotPanel, BoxLayout.Y_AXIS));
        robotPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Load robot image (try png, jpeg, jpg, fallback generic)
        File pngFile = new File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".png");
        File jpegFile = new File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".jpeg");
        File jpgFile = new File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".jpg");
        String imagePath;
        if (pngFile.exists()) {
            imagePath = pngFile.getPath();
        } else if (jpegFile.exists()) {
            imagePath = jpegFile.getPath();
        } else if (jpgFile.exists()) {
            imagePath = jpgFile.getPath();
        } else {
            imagePath = "src/ImagesandSerialization/generic.png";
        }
        robotImageIcon = new ImageIcon(imagePath);
        if (robotImageIcon.getIconWidth() == -1) { // invalid image fallback
            robotImageIcon = new ImageIcon("src/ImagesandSerialization/generic.png");
        }

        robotImageLabel = new JLabel();
        imageWidth = 600;
        imageHeight = 480;
        Image scaledImage = robotImageIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        robotImageIcon = new ImageIcon(scaledImage);
        robotImageLabel.setIcon(robotImageIcon);
        robotImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        robotPanel.add(robotImageLabel);
        robotPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Vertical space between image and chart

        // Add graph panel below image
        JFreeBarChartPanel graphPanel = JFreeBarChartPanel.fromMatches(
                Scanner.matchesTeamIsIn(robotTeam),
                "Points Scored Per Match By: " + robotTeam.getTeamNumber() + " - " + robotTeam.getTeamName(),
                robotTeam);
        graphPanel.setPreferredSize(new Dimension(imageWidth, 320)); // fix graph width to match image width
        graphPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        robotPanel.add(graphPanel);

        // Wrap robotPanel in scroll pane
        JScrollPane centerScrollPane = new JScrollPane(robotPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerScrollPane.getViewport().setBackground(BACKGROUND);
        panel.add(centerScrollPane, BorderLayout.CENTER);

        // Buttons at bottom
        editButton = new JButton("Edit Team");
        editButton.setFont(UI_FONT);
        editButton.setOpaque(true);
        editButton.setContentAreaFilled(false);
        editButton.setBackground(BACKGROUND);
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false); // optional, for a cleaner flat look
        editButton.addActionListener(e -> openEditDialog());

        closeButton = new JButton("Close");
        closeButton.setFont(UI_FONT);
        closeButton.setOpaque(true);
        closeButton.setContentAreaFilled(false);
        closeButton.setBackground(BACKGROUND);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(e -> frame.dispose());

        JPanel southButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southButtons.setBackground(BACKGROUND);
        southButtons.add(closeButton);
        southButtons.add(editButton);
        panel.add(southButtons, BorderLayout.SOUTH);

        // Add the panel to frame
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No action currently needed here
    }

    private void openEditDialog() {
        JDialog dialog = new JDialog(frame, "Edit Team", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField numberField = new JTextField(String.valueOf(robotTeam.getTeamNumber()));
        JTextField nameField = new JTextField(robotTeam.getTeamName() != null ? robotTeam.getTeamName() : "");
        numberField.setToolTipText("Maximum 6 digits");
        nameField.setToolTipText("Maximum 30 characters");
        try {
            ((AbstractDocument) numberField.getDocument()).setDocumentFilter(new LimitedDocumentFilter(6, true));
            ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new LimitedDocumentFilter(30, false));
        } catch (Exception ex) {
            // fallback silently if something goes wrong
        }
        JButton imageButton = new JButton("Choose Image");
        JLabel imageLabelSmall = new JLabel("No file chosen");

        form.add(new JLabel("Team Number:"));
        form.add(numberField);
        form.add(new JLabel("Team Name:"));
        form.add(nameField);
        form.add(new JLabel("Team Image:"));
        JPanel imgRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imgRow.add(imageButton);
        imgRow.add(imageLabelSmall);
        form.add(imgRow);

        dialog.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        buttons.add(save);
        buttons.add(cancel);
        dialog.add(buttons, BorderLayout.SOUTH);

        final File[] chosenImage = new File[1];

        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
            int ret = chooser.showOpenDialog(dialog);
            if (ret == JFileChooser.APPROVE_OPTION) {
                chosenImage[0] = chooser.getSelectedFile();
                imageLabelSmall.setText(chosenImage[0].getName());
            }
        });

        cancel.addActionListener(e -> dialog.dispose());

        save.addActionListener(e -> {
            try {
                String newName = nameField.getText().trim();
                int newNumber = Integer.parseInt(numberField.getText().trim());
                int oldNumber = robotTeam.getTeamNumber();
                robotTeam.setTeamNumber(newNumber);
                robotTeam.setTeamName(newName);
                if (chosenImage[0] != null && chosenImage[0].exists()) {
                    String fname = chosenImage[0].getName().toLowerCase();
                    String ext = fname.endsWith(".png") ? ".png"
                            : (fname.endsWith(".jpg") || fname.endsWith(".jpeg") ? ".jpg" : ".png");
                    Path dest = Path.of("src/ImagesAndSerialization/" + newNumber + ext);
                    try {
                        Files.createDirectories(dest.getParent());
                        Files.copy(chosenImage[0].toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (oldNumber != newNumber) {
                    String[] exts = new String[] { ".png", ".jpg", ".jpeg" };
                    for (String eext : exts) {
                        Path oldPath = Path.of("src/ImagesAndSerialization/" + oldNumber + eext);
                        if (Files.exists(oldPath)) {
                            Path newPath = Path.of("src/ImagesAndSerialization/" + newNumber + eext);
                            try {
                                Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        }
                    }
                }

                // Update UI
                teamNameLabel.setText(robotTeam.getTeamName());
                refreshAttributesList();

                // Update image shown
                String imgPath = null;
                Path ppng = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".png");
                Path pj = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".jpg");
                Path pjpeg = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".jpeg");
                if (Files.exists(ppng))
                    imgPath = ppng.toString();
                else if (Files.exists(pjpeg))
                    imgPath = pjpeg.toString();
                else if (Files.exists(pj))
                    imgPath = pj.toString();
                else
                    imgPath = "src/ImagesAndSerialization/generic.png";

                robotImageIcon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(imageWidth,
                        imageHeight, Image.SCALE_SMOOTH));
                robotImageLabel.setIcon(robotImageIcon);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid team number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    /**
     * DocumentFilter that limits length and optionally restricts to digits only.
     */
    private static class LimitedDocumentFilter extends DocumentFilter {
        private final int maxLength;
        private final boolean digitsOnly;

        public LimitedDocumentFilter(int maxLength, boolean digitsOnly) {
            this.maxLength = maxLength;
            this.digitsOnly = digitsOnly;
        }

        private String filterText(String text) {
            if (text == null)
                return null;
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (digitsOnly) {
                    if (Character.isDigit(c))
                        sb.append(c);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null)
                return;
            String filtered = filterText(string);
            int allowed = maxLength - fb.getDocument().getLength();
            if (allowed <= 0)
                return;
            if (filtered.length() > allowed)
                filtered = filtered.substring(0, allowed);
            super.insertString(fb, offset, filtered, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String filtered = filterText(text);
            int currentLength = fb.getDocument().getLength();
            int newLength = currentLength - length + (filtered != null ? filtered.length() : 0);
            if (newLength > maxLength) {
                int allowed = maxLength - (currentLength - length);
                if (allowed <= 0)
                    return;
                if (filtered.length() > allowed)
                    filtered = filtered.substring(0, allowed);
            }
            super.replace(fb, offset, length, filtered, attrs);
        }
    }
}
