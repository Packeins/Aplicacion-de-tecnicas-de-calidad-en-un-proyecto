import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    @DisplayName("addTask agrega una tarea (retorna true)")
    void testAddTask() {
        boolean result = taskManager.addTask("Complete project");
        assertTrue(result);
    }

    @Test
    @DisplayName("listTasks devuelve las tareas en el formato correcto")
    void testListTasks() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        List<String> output = taskManager.listTasks();

        assertTrue(output.contains("Task 1: Task A"));
        assertTrue(output.contains("Task 2: Task B"));
    }

    @Test
    @DisplayName("removeTask elimina una tarea (retorna true)")
    void testRemoveTask() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        boolean removed = taskManager.removeTask(1);

        assertTrue(removed);
    }

    @Test
    @DisplayName("removeTask elimina correctamente y listTasks refleja el cambio")
    void testRemoveTaskYListTasks() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        boolean removed = taskManager.removeTask(1);
        assertTrue(removed);

        List<String> output = taskManager.listTasks();
        assertTrue(output.contains("Task 1: Task B"));
        assertFalse(output.contains("Task 2: Task B"));
    }

    @Test
    @DisplayName("removeTask con ID inválido NO lanza excepción y retorna false")
    void testRemoveTaskIdInvalido() {
        taskManager.addTask("Only Task");

        boolean removed = taskManager.removeTask(2);

        assertFalse(removed);
    }
}
