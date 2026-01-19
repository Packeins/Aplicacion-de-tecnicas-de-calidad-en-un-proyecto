import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {

    private final List<String> tasks = new ArrayList<>();

    /**
     * Adds a new task to the manager.
     * Rules:
     * - taskDescription must not be null/blank
     * - duplicates are not allowed (case-sensitive)
     *
     * @param taskDescription description of the task
     * @return true if the task was added; false otherwise
     */
    public boolean addTask(String taskDescription) {
        if (!isValidTaskDescription(taskDescription)) {
            return false;
        }

        String normalized = normalize(taskDescription);
        if (isDuplicate(normalized)) {
            return false;
        }

        tasks.add(normalized);
        return true;
    }

    /**
     * Lists tasks in the same 1-based format used in the original code.
     *
     * @return lines like "Task 1: ..." preserving insertion order
     */
    public List<String> listTasks() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            result.add(formatTaskLine(i, tasks.get(i)));
        }
        return result;
    }

    /**
     * Removes a task by a 1-based id (as shown in listTasks).
     *
     * @param id 1-based task id
     * @return true if removed; false if id is invalid
     */
    public boolean removeTask(int id) {
        if (!isValidTaskId(id)) {
            return false;
        }
        tasks.remove(id - 1);
        return true;
    }

    /**
     * Updates an existing task by a 1-based id.
     * Rules:
     * - id must be valid (1..size)
     * - newDescription must not be null/blank
     * - newDescription must not duplicate another existing task
     *
     * @param id 1-based task id
     * @param newDescription new description
     * @return true if updated; false otherwise
     */
    public boolean updateTask(int id, String newDescription) {
        if (!isValidTaskId(id)) {
            return false;
        }
        if (!isValidTaskDescription(newDescription)) {
            return false;
        }

        String normalized = normalize(newDescription);
        int index = id - 1;

        if (wouldBecomeDuplicate(index, normalized)) {
            return false;
        }

        tasks.set(index, normalized);
        return true;
    }

    /**
     * Read-only snapshot for testing/debugging without exposing internals.
     */
    public List<String> getTasksSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(tasks));
    }

    // ----------------- Private helpers (small, single-purpose) -----------------

    private boolean isValidTaskDescription(String taskDescription) {
        return taskDescription != null && !taskDescription.trim().isEmpty();
    }

    private String normalize(String taskDescription) {
        return taskDescription.trim();
    }

    private boolean isDuplicate(String normalizedTaskDescription) {
        for (String existing : tasks) {
            if (existing.equals(normalizedTaskDescription)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidTaskId(int id) {
        return id >= 1 && id <= tasks.size();
    }

    private boolean wouldBecomeDuplicate(int targetIndex, String normalizedNewDescription) {
        for (int i = 0; i < tasks.size(); i++) {
            if (i == targetIndex) {
                continue;
            }
            if (tasks.get(i).equals(normalizedNewDescription)) {
                return true;
            }
        }
        return false;
    }

    private String formatTaskLine(int indexZeroBased, String taskDescription) {
        return "Task " + (indexZeroBased + 1) + ": " + taskDescription;
    }

    // ----------------- Console/UI only (kept in main) -----------------

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        boolean added = taskManager.addTask("Complete project");
        System.out.println(added ? "Task added." : "Task NOT added.");

        for (String line : taskManager.listTasks()) {
            System.out.println(line);
        }

        boolean updated = taskManager.updateTask(1, "Complete project (updated)");
        System.out.println(updated ? "Task updated." : "Task NOT updated.");

        boolean removed = taskManager.removeTask(1);
        System.out.println(removed ? "Task removed." : "Task NOT removed.");
    }
}

// Prueba CI/CD