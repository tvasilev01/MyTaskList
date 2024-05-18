import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskForm extends JDialog {
private TaskManager taskManager;
private Task task;
private JTextField titleField;
private JTextArea descriptionField;
private JTextField deadlineField;
private JComboBox<Integer> priorityComboBox;

public TaskForm(JFrame parent, TaskManager taskManager, Task task){
    super(parent, "Task Form", true);
    this.taskManager = taskManager;
    this.task = task;

    setLayout(new BorderLayout());

    JPanel formPanel = new JPanel(new GridLayout(4,2));

    JLabel titleLabel = new JLabel("Title:");
    titleField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    descriptionField = new JTextArea(3,20);
    JLabel deadlineLabel = new JLabel("Deadline (yyyy-MM-dd:)");
    deadlineField = new JTextField();
    JLabel priorityLabel = new JLabel("Priority:");
    priorityComboBox = new JComboBox<>(new Integer[]{1,2,3,4,5});

    formPanel.add(titleLabel);
    formPanel.add(titleField);
    formPanel.add(descriptionLabel);
    formPanel.add(new JScrollPane(descriptionField));
    formPanel.add(deadlineLabel);
    formPanel.add(deadlineField);
    formPanel.add(priorityLabel);
    formPanel.add(priorityComboBox);

    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveTask();
        }
    });

    add(formPanel, BorderLayout.CENTER);
    add(saveButton, BorderLayout.SOUTH);

    if(task != null){
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        deadlineField.setText(new SimpleDateFormat("yyyy-MM-dd").format(task.getDeadline()));
        priorityComboBox.setSelectedItem(task.getPriority());
    }

pack();
setLocationRelativeTo(parent);
}

private void saveTask(){
    String title = titleField.getText();
    String description = descriptionField.getText();
    String deadlineText = deadlineField.getText();
    int priority = (int) priorityComboBox.getSelectedItem();

    try{
        Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineText);

        if(task == null){
            task = new Task(title, description, deadline, priority);
            taskManager.addTask(task);
        }else{
            task.setTitle(title);
            task.setDescription(description);
            task.setDeadline(deadline);
            task.setPriority(priority);
            taskManager.updateTask(task, task);
        }
        dispose();
    }catch (ParseException e){
        JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd");
    }
  }
}

