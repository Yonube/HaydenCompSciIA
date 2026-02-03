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
        listModel.addElement("Blue Alliance Team 1: " + (match.getBlue1() != null ? match.getBlue1().getTeamNumber() : "null"));
        listModel.addElement("Blue Alliance Team 2: " + (match.getBlue2() != null ? match.getBlue2().getTeamNumber() : "null"));
        listModel.addElement("Blue Alliance Team 3: " + (match.getBlue3() != null ? match.getBlue3().getTeamNumber() : "null"));
        listModel.addElement("Red Alliance Team 1: " + (match.getRed1() != null ? match.getRed1().getTeamNumber() : "null"));
        listModel.addElement("Red Alliance Team 2: " + (match.getRed2() != null ? match.getRed2().getTeamNumber() : "null"));
        listModel.addElement("Red Alliance Team 3: " + (match.getRed3() != null ? match.getRed3().getTeamNumber() : "null"));
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
    // Right side (Red) - use BorderLayout so the middle icon sits in the CENTER
    JPanel RedImagePanel = new JPanel(new BorderLayout());
    RedImagePanel.setOpaque(false);

    // TOP (Red1)
    JPanel redTop = new JPanel();
    redTop.setOpaque(false);
    redTop.setLayout(new BoxLayout(redTop, BoxLayout.Y_AXIS));
    if (match.getRed1() != null) {
        ImageIcon red1Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed1().getTeamNumber() + ".png");
        if (red1Icon.getIconWidth() == -1) {
            red1Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImage = red1Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red1Icon = new ImageIcon(scaledImage);
        JLabel red1Label = new JLabel(red1Icon);
        red1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        redTop.add(red1Label);
        redTop.add(Box.createRigidArea(new Dimension(0,4)));
    }
    RedImagePanel.add(redTop, BorderLayout.NORTH);

    // CENTER (Red2) - this will be vertically centered with the center image
    JPanel redCenter = new JPanel(new GridBagLayout());
    redCenter.setOpaque(false);
    if (match.getRed2() != null) {
        ImageIcon red2Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed2().getTeamNumber() + ".png");
        if (red2Icon.getIconWidth() == -1) {
            red2Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImage2 = red2Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red2Icon = new ImageIcon(scaledImage2);
        JLabel red2Label = new JLabel(red2Icon);
        redCenter.add(red2Label); // GridBagLayout centers by default
    }
    RedImagePanel.add(redCenter, BorderLayout.CENTER);

    // BOTTOM (Red3)
    JPanel redBottom = new JPanel();
    redBottom.setOpaque(false);
    redBottom.setLayout(new BoxLayout(redBottom, BoxLayout.Y_AXIS));
    if (match.getRed3() != null) {
        ImageIcon red3Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getRed3().getTeamNumber() + ".png");
        if (red3Icon.getIconWidth() == -1) {
            red3Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImage3 = red3Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        red3Icon = new ImageIcon(scaledImage3);
        JLabel red3Label = new JLabel(red3Icon);
        red3Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        redBottom.add(Box.createRigidArea(new Dimension(0,4)));
        redBottom.add(red3Label);
    }
    RedImagePanel.add(redBottom, BorderLayout.SOUTH);

    // LEFT side (Blue) - same pattern so Blue middle aligns with center image
    JPanel BlueImagePanel = new JPanel(new BorderLayout());
    BlueImagePanel.setOpaque(false);

    JPanel blueTop = new JPanel();
    blueTop.setOpaque(false);
    blueTop.setLayout(new BoxLayout(blueTop, BoxLayout.Y_AXIS));
    if (match.getBlue1() != null) {
        ImageIcon blue1Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getBlue1().getTeamNumber() + ".png");
        if (blue1Icon.getIconWidth() == -1) {
            blue1Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImageB1 = blue1Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        blue1Icon = new ImageIcon(scaledImageB1);
        JLabel blue1Label = new JLabel(blue1Icon);
        blue1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueTop.add(blue1Label);
        blueTop.add(Box.createRigidArea(new Dimension(0,4)));
    }
    BlueImagePanel.add(blueTop, BorderLayout.NORTH);

    JPanel blueCenter = new JPanel(new GridBagLayout());
    blueCenter.setOpaque(false);
    if (match.getBlue2() != null) {
        ImageIcon blue2Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getBlue2().getTeamNumber() + ".png");
        if (blue2Icon.getIconWidth() == -1) {
            blue2Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImageB2 = blue2Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        blue2Icon = new ImageIcon(scaledImageB2);
        JLabel blue2Label = new JLabel(blue2Icon);
        blueCenter.add(blue2Label);
    }
    BlueImagePanel.add(blueCenter, BorderLayout.CENTER);

    JPanel blueBottom = new JPanel();
    blueBottom.setOpaque(false);
    blueBottom.setLayout(new BoxLayout(blueBottom, BoxLayout.Y_AXIS));
    if (match.getBlue3() != null) {
        ImageIcon blue3Icon = new ImageIcon("src/ImagesAndSerialization/" + match.getBlue3().getTeamNumber() + ".png");
        if (blue3Icon.getIconWidth() == -1) {
            blue3Icon = new ImageIcon("src/ImagesAndSerialization/generic.png");
        }
        Image scaledImageB3 = blue3Icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        blue3Icon = new ImageIcon(scaledImageB3);
        JLabel blue3Label = new JLabel(blue3Icon);
        blue3Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueBottom.add(Box.createRigidArea(new Dimension(0,4)));
        blueBottom.add(blue3Label);
    }
    BlueImagePanel.add(blueBottom, BorderLayout.SOUTH);

    // Attach to centerPanel
    centerPanel.add(RedImagePanel, BorderLayout.EAST);
    centerPanel.add(BlueImagePanel, BorderLayout.WEST);

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