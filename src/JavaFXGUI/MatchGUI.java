package src.JavaFXGUI;
import src.OOPBackEnd.Matches;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MatchGUI implements ActionListener {
    private Matches match;
    private JFrame frame;
    private JPanel panel;
    private JLabel matchNumberLabel;
    @SuppressWarnings("unused")
    private DefaultListModel<String> listModel;
    @SuppressWarnings("unused")
    private JList<String> attributesList;
    @SuppressWarnings("unused")
    private JScrollPane scrollPane;
    @SuppressWarnings("unused")
    private JButton closeButton;
    
    private final int imageWidth = 50;
    private final int imageHeight = 50;
    
    
    public MatchGUI(Matches match) {
        this.match = match;
        setupGUI();
    }

    public void setupGUI() {
        // Create the frame
        frame = new JFrame("Match Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a panel to hold components
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add the match number at the top
        matchNumberLabel = new JLabel("Match Number: " + match.getMatchNumber(), JLabel.CENTER);
        matchNumberLabel.setFont(new Font("Arial", Font.BOLD, 64));
        panel.add(matchNumberLabel, BorderLayout.NORTH);

        // Create a list to display match attributes
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Match Number: " + match.getMatchNumber());
        listModel.addElement("Blue Alliance Team 1: " + (match.getBlue1() != null ? match.getBlue1().getTeamName() : "null"));
        listModel.addElement("Blue Alliance Team 2: " + (match.getBlue2() != null ? match.getBlue2().getTeamName() : "null"));
        listModel.addElement("Blue Alliance Team 3: " + (match.getBlue3() != null ? match.getBlue3().getTeamName() : "null"));
        listModel.addElement("Red Alliance Team 1: " + (match.getRed1() != null ? match.getRed1().getTeamName() : "null"));
        listModel.addElement("Red Alliance Team 2: " + (match.getRed2() != null ? match.getRed2().getTeamName() : "null"));
        listModel.addElement("Red Alliance Team 3: " + (match.getRed3() != null ? match.getRed3().getTeamName() : "null"));
        if(match.getBlueScore() > match.getRedScore()) {
            listModel.addElement("Winning Alliance: Blue");
        } else if(match.getRedScore() > match.getBlueScore()) {
            listModel.addElement("Winning Alliance: Red");
        } else {
            listModel.addElement("Winning Alliance: Tie");
        }
        listModel.addElement("Blue Alliance Score: " + match.getBlueScore());
        listModel.addElement("Red Alliance Score: " + match.getRedScore());
        
        //Add scores and include the winning alliance + ranking points + error if not all data has been entered

        // Create a JList to display the list model
        JList<String> matchDetailsList = new JList<>(listModel);
        matchDetailsList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(matchDetailsList);
        panel.add(scrollPane, BorderLayout.WEST);

        // Add a close button at the bottom
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.addActionListener(e -> frame.dispose());
        panel.add(closeButton, BorderLayout.SOUTH);

        
        // Create center panel for stuff
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
         // Add image onto center of frame
    JLabel imageLabel = new JLabel(new ImageIcon("src/ImagesAndSerialization/2025_REEFSCAPE.png")); 
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        // Add Red Alliance Images On the Side
    JPanel RedImagePanel = new JPanel();
    // Use a vertical box layout so the red-team icons stack tightly one above another
    RedImagePanel.setLayout(new BoxLayout(RedImagePanel, BoxLayout.Y_AXIS));
        //Red 1
        if (match.getRed1() != null) {
            ImageIcon red1Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed1().getTeamNumber()+".png");
            if (red1Icon.getIconWidth() == -1) { // Check if the image is invalid
                red1Icon = new ImageIcon("src/ImagesandSerialization/generic.png"); // Use generic image
            }
        // Scale the image to fit the window
        Image scaledImage = red1Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red1Icon = new ImageIcon(scaledImage);
        JLabel red1Label = new JLabel();
        red1Label.setHorizontalAlignment(JLabel.CENTER);
        red1Label.setIcon(red1Icon);
        // Center alignment for BoxLayout
        red1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        RedImagePanel.add(red1Label);
        // small gap to keep icons close together
        RedImagePanel.add(Box.createRigidArea(new Dimension(0,4)));
        }
        //Red 2
        if (match.getRed2() != null) {
            ImageIcon red2Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed2().getTeamNumber()+".png");
            if (red2Icon.getIconWidth() == -1) { // Check if the image is invalid
                red2Icon = new ImageIcon("src/ImagesAndSerialization/generic.png"); // Use generic image
            }
        // Scale the image to fit the window
        Image scaledImage2 = red2Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red2Icon = new ImageIcon(scaledImage2);
        JLabel red2Label = new JLabel();
        red2Label.setHorizontalAlignment(JLabel.CENTER);
        red2Label.setIcon(red2Icon);
        red2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        RedImagePanel.add(red2Label);
        RedImagePanel.add(Box.createRigidArea(new Dimension(0,4)));
        }
        // Red 3
        if (match.getRed3() != null) {
            ImageIcon red3Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed3().getTeamNumber()+".png");
            if (red3Icon.getIconWidth() == -1) { // Check if the image is invalid
                red3Icon = new ImageIcon("src/ImagesAndSerialization/generic.png"); // Use generic image
            }
        // Scale the image to fit the window
        Image scaledImage3 = red3Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red3Icon = new ImageIcon(scaledImage3);
        JLabel red3Label = new JLabel();
        red3Label.setHorizontalAlignment(JLabel.CENTER);
        red3Label.setIcon(red3Icon);
        red3Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        RedImagePanel.add(red3Label);
        }

    // Add the RedImagePanel to the right of the center panel so it becomes visible
    centerPanel.add(RedImagePanel, BorderLayout.EAST);

    // Add the panel to the frame
    frame.add(panel);
    panel.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
        // Add your logic here to handle the action event
    }
}