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

        // Add image onto center of frame
        JLabel imageLabel = new JLabel(new ImageIcon("src/ImagesandSerialization/2025_REEFSCAPE.png")); 
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);


        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
        // Add your logic here to handle the action event
    }
}