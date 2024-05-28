import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskPanel extends JPanel {
    private final JFrame frame;
    private final User user;
    private final TaskManager taskManager;
    private final DefaultListModel<Task> taskListModel;
    private final JList<Task> taskList;

    public TaskPanel(JFrame frame, User user, TaskManager taskManager, UserManager userManager) {
        this.frame = frame;
        this.user = user;
        this.taskManager = taskManager;
        this.taskListModel = new DefaultListModel<>();
        this.taskList = new JList<>(taskListModel);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + ".");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(128, 0, 128));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Add Task");
        addButton.setFont(new Font("Serif", Font.ITALIC, 16));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.GRAY);
        JButton editButton = new JButton("Edit Task");
        editButton.setFont(new Font("Serif", Font.ITALIC, 16));
        editButton.setForeground(Color.WHITE);
        editButton.setBackground(Color.GRAY);
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setFont(new Font("Serif", Font.ITALIC, 16));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.GRAY);
        JButton saveButton = new JButton("Save Tasks");
        saveButton.setFont(new Font("Serif", Font.ITALIC, 16));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(Color.GRAY);
        JButton filterButton = new JButton("Filter by Deadline");
        filterButton.setFont(new Font("Serif", Font.ITALIC, 16));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBackground(Color.GRAY);
        JButton sortByPriorityButton = new JButton("Sort by Priority");
        sortByPriorityButton.setFont(new Font("Serif", Font.ITALIC, 16));
        sortByPriorityButton.setForeground(Color.WHITE);
        sortByPriorityButton.setBackground(Color.GRAY);
        JButton logoutButton = new JButton("Log out");
        logoutButton.setFont(new Font("Serif", Font.ITALIC, 16));
        logoutButton.setForeground(Color.RED);
        logoutButton.setBackground(Color.LIGHT_GRAY);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(sortByPriorityButton);
        buttonPanel.add(logoutButton);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskForm taskForm = new TaskForm(frame, taskManager, null);
                taskForm.setVisible(true);
                refreshTaskList();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    TaskForm taskForm = new TaskForm(frame, taskManager, selectedTask);
                    taskForm.setVisible(true);
                    refreshTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to edit");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    taskManager.removeTask(selectedTask);
                    refreshTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to delete");
                }
            }
        });
        // --------------------    запазване - бутон
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    taskManager.saveTasksToFile("tasks.dat"); // Запазване на задачите във файл
                    JOptionPane.showMessageDialog(frame, "Tasks saved successfully");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving tasks: " + ex.getMessage());
                }
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Task> sortedTasks = taskManager.filterTasksByDeadline();
                taskListModel.clear();
                for (Task task : sortedTasks) {
                    taskListModel.addElement(task);
                }
            }
        });

        sortByPriorityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Task> sortedTasks = taskManager.getTasksSortedBtPriority();
                taskListModel.clear();
                for (Task task : sortedTasks) {
                    taskListModel.addElement(task);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new LoginPanel(frame, userManager, taskManager));
                frame.revalidate();
            }
        });

        taskList.setCellRenderer(new TaskListCellRenderer());
        refreshTaskList();
    }


    private void refreshTaskList() {
        taskListModel.clear();
        for (Task task : taskManager.getTasks()) {
            taskListModel.addElement(task);
        }
    }

    private static class TaskListCellRenderer extends JLabel implements ListCellRenderer<Task> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setText(String.format("Title: %s | Description: %s | Date: %s | Priority: %d", value.getTitle(), value.getDescription(), sdf.format(value.getDeadline()), value.getPriority()));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
}
