package src.JavaFXGUI;
import src.JavaFXGUI.GraphsFolder.JFreeBarChartPanel;
import src.JavaFXGUI.GraphsFolder.JFreeChartScatterGraphPanel;
import src.OOPBackEnd.Matches;
import src.OOPBackEnd.Scanner;
import src.OOPBackEnd.RobotTeam;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RobotTeamGUI implements ActionListener {
    // Class-level variables
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
        if (attributesList != null) {
            attributesList.setModel(listModel);
        }
    }

    private void setupGUI() {
        frame = new JFrame("Robot Team Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        // Initialize the panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Initialize the team name label
        teamNameLabel = new JLabel(robotTeam.getTeamName(), JLabel.CENTER);
        teamNameLabel.setFont(new Font("Arial", Font.BOLD, 64));
        panel.add(teamNameLabel, BorderLayout.NORTH);

        // Initialize the list model and populate it with attributes
        listModel = new DefaultListModel<>();
        attributesList = new JList<>(listModel);
        scrollPane = new JScrollPane(attributesList);
        panel.add(scrollPane, BorderLayout.WEST);

        // Populate initial data
        refreshAttributesList();

        // Add a full edit button
        JButton editButton = new JButton("Edit Team");
        editButton.addActionListener(ev -> openEditDialog());
        JPanel westButtons = new JPanel();
        westButtons.setLayout(new BoxLayout(westButtons, BoxLayout.Y_AXIS));
        westButtons.add(editButton);
        panel.add(westButtons, BorderLayout.EAST);

        // Initialize the robot panel
        robotPanel = new JPanel();
        robotPanel.setLayout(new BorderLayout());

        // Add a label for the robot image
        // Try .png, then .jpeg, then .jpg; fall back to generic if none found
        java.io.File pngFile = new java.io.File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".png");
        java.io.File jpegFile = new java.io.File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".jpeg");
        java.io.File jpgFile = new java.io.File("src/ImagesandSerialization/" + robotTeam.getTeamNumber() + ".jpg");
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
        if (robotImageIcon.getIconWidth() == -1) { // Check if the image is invalid
            robotImageIcon = new ImageIcon("src/ImagesandSerialization/generic.png"); // Use generic image
        }
        robotImageLabel = new JLabel();

        // Scale the image to fit the window
        imageWidth = 500;
        imageHeight = 400;
        Image scaledImage = robotImageIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        robotImageIcon = new ImageIcon(scaledImage);
        robotImageLabel.setIcon(robotImageIcon);
        robotPanel.add(robotImageLabel, BorderLayout.NORTH);

        //JFreeChartScatterGraphPanel graphPanel = new JFreeChartScatterGraphPanel(Scanner.matchesTeamIsIn(robotTeam), robotTeam);
        JFreeBarChartPanel graphPanel = JFreeBarChartPanel.fromMatches(Scanner.matchesTeamIsIn(robotTeam), "Points Scored Per Match By: " + robotTeam.getTeamNumber() + " - " + robotTeam.getTeamName(), robotTeam);
        robotPanel.add(graphPanel, BorderLayout.SOUTH);

        // Wrap the robotPanel in a container so the scroll pane sizes correctly
        JPanel contentWrap = new JPanel(new BorderLayout());
        contentWrap.add(robotPanel, BorderLayout.NORTH);
        contentWrap.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Put everything into a scroll pane so the image/graph can be scrolled if needed
        JScrollPane centerScrollPane = new JScrollPane(contentWrap,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(centerScrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);

        // Add a key listener for ESC to close the frame
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose(); // Close the frame
                    return true; // Event consumed
                }
                return false; // Event not consumed
            }
        });

        // Add a close button
        closeButton = new JButton("Close");
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

    private void openEditDialog() {
        JDialog dialog = new JDialog(frame, "Edit Team", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField numberField = new JTextField(String.valueOf(robotTeam.getTeamNumber()));
        JTextField nameField = new JTextField(robotTeam.getTeamName() != null ? robotTeam.getTeamName() : "");
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
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
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
                // Handle image: if user picked one, copy to ImagesAndSerialization with new number
                if (chosenImage[0] != null && chosenImage[0].exists()) {
                    String fname = chosenImage[0].getName().toLowerCase();
                    String ext = fname.endsWith(".png") ? ".png" : (fname.endsWith(".jpg") || fname.endsWith(".jpeg") ? ".jpg" : ".png");
                    Path dest = Path.of("src/ImagesAndSerialization/" + newNumber + ext);
                    try {
                        Files.createDirectories(dest.getParent());
                        Files.copy(chosenImage[0].toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (oldNumber != newNumber) {
                    // try to rename existing image files from oldNumber to newNumber
                    String[] exts = new String[]{".png", ".jpg", ".jpeg"};
                    for (String eext : exts) {
                        Path oldPath = Path.of("src/ImagesAndSerialization/" + oldNumber + eext);
                        if (Files.exists(oldPath)) {
                            Path newPath = Path.of("src/ImagesAndSerialization/" + newNumber + eext);
                            try { Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING); } catch (IOException ex) { ex.printStackTrace(); }
                            break;
                        }
                    }
                }

                // Update UI
                teamNameLabel.setText(robotTeam.getTeamName());
                refreshAttributesList();
                // update image shown
                String imgPath = null;
                Path ppng = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".png");
                Path pj = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".jpg");
                Path pjpeg = Path.of("src/ImagesAndSerialization/" + robotTeam.getTeamNumber() + ".jpeg");
                if (Files.exists(ppng)) imgPath = ppng.toString();
                else if (Files.exists(pjpeg)) imgPath = pjpeg.toString();
                else if (Files.exists(pj)) imgPath = pj.toString();
                else imgPath = "src/ImagesAndSerialization/generic.png";
                robotImageIcon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));
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
}