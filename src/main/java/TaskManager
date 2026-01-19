import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List tasks = new ArrayList<>();

    public void addTask(String t) {
        tasks.add(t);
        System.out.println("Task added.");
    }

    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Task " + (i + 1) + ": " + tasks.get(i));
        }
    }

    public void removeTask(int id) {
        tasks.remove(id - 1);
        System.out.println("Task removed.");
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.addTask("Complete project");
        tm.listTasks();
        tm.removeTask(1);
    }
}