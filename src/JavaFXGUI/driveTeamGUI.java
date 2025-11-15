package src.JavaFXGUI;
import javax.swing.*;
import java.awt.*;

public class driveTeamGUI {
    public static void showDriveTeamGUI() {
        JFrame frame = new JFrame("Drive Team GUI");
        JPanel panel = new JPanel();
        frame.setSize(1600, 1600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Drive Team GUI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 10, 400, 30);
        panel.add(titleLabel);

        frame.setVisible(true);
    }
}
