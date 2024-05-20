import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        UserManager userManager = new UserManager();
        TaskManager taskManager = new TaskManager();
        LoginPanel loginPanel = new LoginPanel(frame, userManager, taskManager);
        frame.setContentPane(loginPanel);

        frame.setVisible(true);
    }
}