import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final JFrame frame;
    private final UserManager userManager;
    private final TaskManager taskManager;

    public LoginPanel(JFrame frame, UserManager userManager, TaskManager taskManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.taskManager = taskManager;

        ImageIcon imageIcon = new ImageIcon("resources/login-background.jpg");

        JPanel backgroundPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(250, 200, 100, 40);
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250, 250, 100, 40);
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(350, 200, 300, 40);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(350, 250, 300, 40);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(350, 300, 150, 40);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(500, 300, 150, 40);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.GRAY);
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));

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
                    TaskPanel taskPanel = new TaskPanel(frame, user, taskManager, userManager);
                    frame.setContentPane(taskPanel);
                    frame.revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }
}
