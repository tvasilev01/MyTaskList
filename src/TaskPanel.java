import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskPanel extends JPanel {
    private JFrame frame;
    private User user;
    private TaskManager taskManager;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField filterField;

    public TaskPanel(JFrame frame, User user, TaskManager taskManager) {
        this.frame = frame;
        this.user = user;
        this.taskManager = taskManager;
        this.taskListModel = new DefaultListModel<>();
        this.taskList = new JList<>(taskListModel);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addButton = new JButton("Add Task");
        JButton editButton = new JButton("Edit Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton saveButton = new JButton("Save Tasks"); // Бутон за запазване на задачите
        filterField = new JTextField(10);
        JButton filterButton = new JButton("Filter by Deadline");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton); // Добавяне на бутона в панела
        buttonPanel.add(new JLabel("Deadline:"));
        buttonPanel.add(filterField);
        buttonPanel.add(filterButton);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

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

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterText = filterField.getText();
                if (!filterText.isEmpty()) {
                    try {
                        Date filterDate = new SimpleDateFormat("yyyy-MM-dd").parse(filterText);
                        List<Task> filteredTasks = taskManager.filterTasksByDeadline(filterDate);
                        taskListModel.clear();
                        for (Task task : filteredTasks) {
                            taskListModel.addElement(task);
                        }
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid date format. Please use yyyy-MM-dd");
                    }
                } else {
                    refreshTaskList();
                }
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            setText(String.format("%s - %s - %s - Priority: %d", value.getTitle(), value.getDescription(), sdf.format(value.getDeadline()), value.getPriority()));
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
