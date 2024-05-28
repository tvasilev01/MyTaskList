import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        UserManager userManager = new UserManager();
        TaskManager taskManager = new TaskManager();
        LoginPanel loginPanel = new LoginPanel(frame, userManager, taskManager);
        frame.setContentPane(loginPanel);

        try {
            taskManager.loadTasksFromFile("tasks.dat"); // Зареждане на файла
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No tasks to load or failed to load tasks.");
        }

        frame.setVisible(true);
    }
}