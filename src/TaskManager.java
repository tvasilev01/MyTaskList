import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager(){
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public List<Task> filterTasksByDeadLine(Date deadline){
        return tasks.stream()
                .filter(task -> task.getDeadline().equals(deadline))
                .collect(Collectors.toList());
    }

    public void updateTask(Task oldTask, Task newTask){
        int index = tasks.indexOf(oldTask);
        if(index != -1){
            tasks.set(index, newTask);
        }
    }

}
