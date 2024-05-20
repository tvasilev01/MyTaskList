import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JFrame frame;
    private UserManager userManager;
    private TaskManager taskManager;

    public LoginPanel(JFrame frame, UserManager userManager, TaskManager taskManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.taskManager = taskManager;

        this.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 200, 100, 40);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 250, 100, 40);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(300, 200, 300, 40);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(300, 250, 300, 40);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(300, 300, 150, 40);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(450, 300, 150, 40);

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(registerButton);
        this.add(loginButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (userManager.addUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful");
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = userManager.authenticate(username, password);
                if (user != null) {
                    TaskPanel taskPanel = new TaskPanel(frame, user, taskManager);
                    frame.setContentPane(taskPanel);
                    frame.revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });
    }
}
