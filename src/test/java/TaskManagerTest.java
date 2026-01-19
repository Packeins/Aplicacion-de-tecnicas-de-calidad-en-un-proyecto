import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @DisplayName("addTask agrega una tarea e imprime mensaje de confirmación")
    void testAddTask() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        taskManager.addTask("Complete project");

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Task added."));
    }

    @Test
    @DisplayName("listTasks imprime las tareas en el formato correcto")
    void testListTasks() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        taskManager.listTasks();

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Task 1: Task A"));
        assertTrue(output.contains("Task 2: Task B"));
    }

    @Test
    @DisplayName("removeTask elimina una tarea e imprime mensaje de confirmación")
    void testRemoveTask() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        taskManager.removeTask(1);

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Task removed."));
    }

    @Test
    @DisplayName("removeTask elimina correctamente y listTasks refleja el cambio")
    void testRemoveTaskYListTasks() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        taskManager.removeTask(1);
        taskManager.listTasks();

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Task 1: Task B"));
    }

    @Test
    @DisplayName("removeTask con ID inválido lanza excepción (problema predefinido)")
    void testRemoveTaskIdInvalido() {
        taskManager.addTask("Only Task");

        assertThrows(IndexOutOfBoundsException.class, () -> taskManager.removeTask(2));
    }
}