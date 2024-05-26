import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

   public List<Task> filterTasksByDeadline(Date deadline){
        List<Task> filteredTasks = new ArrayList<>();
       for (Task task : tasks) {
           if(task.getDeadline().equals(deadline)){
               filteredTasks.add(task);
           }
       }
       return filteredTasks;
   }
    public void updateTask(Task oldTask, Task newTask){
        int index = tasks.indexOf(oldTask);
        if(index != -1){
            tasks.set(index, newTask);
        }
    }
    // --------   запазваме файловете
    public void saveTasksToFile(String filename) throws IOException {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/" + filename;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tasks);
            System.out.println("Задачите са записани в " + filePath);
        }catch (IOException e) {
            System.err.println("Грешка при записване на задачите: " + e.getMessage());
            throw e;
        }
    }
    public void loadTasksFromFile(String filename) throws IOException, ClassNotFoundException {
        File file = new File(System.getProperty("user.dir") + "/" + filename);
        if (!file.exists()) {
            System.out.println("Файлът не съществува, няма задачи за зареждане.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            tasks = (List<Task>) ois.readObject();
            System.out.println("Задачите са заредени от " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при зареждане на задачите: " + e.getMessage());
            throw e;
        }
    }
}
