package src.JavaFXGUI;
import src.OOPBackEnd.RobotTeam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainapp implements ActionListener {

	private static JLabel success;
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JPasswordField passwordText;
	private static JLabel passwordLabel;
	private static JButton button;
	private static int failedAttempts = 0; // counter for failed attempts
    private static JLabel titleLabel;

	public static void main(String[] args) {

		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(1600, 1600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		panel.setLayout(null);

        titleLabel = new JLabel("Robotics Scouter App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 10, 400, 30);
        panel.add(titleLabel);

		userLabel = new JLabel("User");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);

		userText = new JTextField();
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);

		passwordLabel = new JLabel("Log Stats:");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField();
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);

		button = new JButton("Login");
		button.setBounds(10, 80, 80, 25);
		button.addActionListener(new mainapp());
		panel.add(button);

		success = new JLabel("");
		success.setBounds(10, 110, 300, 25);
		panel.add(success);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userText.getText();
		String password = new String(passwordText.getPassword());

		if (user.equals("Hayden") && password.equals("Hayden")) {
			success.setText("Login successful after " + failedAttempts + " failed attempt(s).");
		} else {
			failedAttempts++;
			success.setText("Login failed. Attempts: " + failedAttempts);
		}
	}
}