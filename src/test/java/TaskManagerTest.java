import static org.junit.jupiter.api.Assertions.*;

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

    // ---------- addTask ----------

    @Test
    @DisplayName("addTask agrega tarea válida")
    void testAddTaskValida() {
        assertTrue(taskManager.addTask("Complete project"));
    }

    @Test
    @DisplayName("addTask no agrega tarea duplicada")
    void testAddTaskDuplicada() {
        taskManager.addTask("Task A");
        assertFalse(taskManager.addTask("Task A")); // duplicado
    }

    @Test
    @DisplayName("addTask no agrega null ni vacío")
    void testAddTaskInvalida() {
        assertFalse(taskManager.addTask(null));
        assertFalse(taskManager.addTask(""));
        assertFalse(taskManager.addTask("   "));
    }

    // ---------- listTasks ----------

    @Test
    @DisplayName("listTasks devuelve formato correcto")
    void testListTasksFormato() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        List<String> output = taskManager.listTasks();

        assertEquals(2, output.size());
        assertTrue(output.contains("Task 1: Task A"));
        assertTrue(output.contains("Task 2: Task B"));
    }

    // ---------- removeTask ----------

    @Test
    @DisplayName("removeTask elimina tarea válida")
    void testRemoveTaskValida() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        assertTrue(taskManager.removeTask(1));
        List<String> output = taskManager.listTasks();
        assertEquals(1, output.size());
        assertTrue(output.contains("Task 1: Task B"));
    }

    @Test
    @DisplayName("removeTask retorna false para ID inválido")
    void testRemoveTaskIdInvalido() {
        taskManager.addTask("Only Task");

        assertFalse(taskManager.removeTask(0));
        assertFalse(taskManager.removeTask(2));
    }

    @Test
    @DisplayName("removeTask en lista vacía retorna false")
    void testRemoveTaskListaVacia() {
        assertFalse(taskManager.removeTask(1));
    }

    // ---------- updateTask ----------

    @Test
    @DisplayName("updateTask modifica tarea válida")
    void testUpdateTaskValida() {
        taskManager.addTask("Task A");
        assertTrue(taskManager.updateTask(1, "Task A updated"));
        List<String> output = taskManager.listTasks();
        assertTrue(output.contains("Task 1: Task A updated"));
    }

    @Test
    @DisplayName("updateTask no permite ID inválido")
    void testUpdateTaskIdInvalido() {
        taskManager.addTask("Task A");
        assertFalse(taskManager.updateTask(0, "X"));
        assertFalse(taskManager.updateTask(2, "X"));
    }

    @Test
    @DisplayName("updateTask no permite descripción inválida")
    void testUpdateTaskDescripcionInvalida() {
        taskManager.addTask("Task A");
        assertFalse(taskManager.updateTask(1, null));
        assertFalse(taskManager.updateTask(1, ""));
        assertFalse(taskManager.updateTask(1, "   "));
    }

    @Test
    @DisplayName("updateTask no permite duplicados")
    void testUpdateTaskDuplicado() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");
        assertFalse(taskManager.updateTask(2, "Task A")); // B -> A duplicado
    }

    // ---------- getTasksSnapshot ----------

    @Test
    @DisplayName("getTasksSnapshot devuelve lista inmutable")
    void testGetTasksSnapshotInmutable() {
        taskManager.addTask("Task A");
        List<String> snapshot = taskManager.getTasksSnapshot();

        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("Task B"));
    }

    @Test
    @DisplayName("getTasksSnapshot refleja tareas actuales")
    void testGetTasksSnapshotContenido() {
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");

        List<String> snapshot = taskManager.getTasksSnapshot();
        assertEquals(2, snapshot.size());
        assertTrue(snapshot.contains("Task A"));
        assertTrue(snapshot.contains("Task B"));
    }

    // ---------- combinaciones / ramas privadas ----------

    @Test
    @DisplayName("addTask y updateTask combinaciones para cobertura completa")
    void testCombinacionesInternas() {
        // empty list
        assertFalse(taskManager.addTask(""));
        assertFalse(taskManager.addTask(null));

        // agregar varias tareas
        taskManager.addTask("Task1");
        taskManager.addTask("Task2");

        // updateTask a mismo índice con mismo valor (no es duplicado real)
        assertTrue(taskManager.updateTask(1, "Task1"));

        // updateTask duplicando otro índice
        assertFalse(taskManager.updateTask(1, "Task2"));

        // removeTask con índice límite
        assertFalse(taskManager.removeTask(3));
        assertTrue(taskManager.removeTask(2));
    }

    // ---------- tests para cubrir normalize y formatTaskLine ----------

    @Test
    @DisplayName("normalize y formatTaskLine con espacios")
    void testNormalizeAndFormatTaskLine() {
        taskManager.addTask("  Task A  ");
        List<String> output = taskManager.listTasks();
        assertTrue(output.contains("Task 1: Task A")); // asegura trim
    }

    // ---------- tests adicionales para wouldBecomeDuplicate ----------
    @Test
    @DisplayName("wouldBecomeDuplicate retorna true y false correctamente")
    void testWouldBecomeDuplicateCaminos() {
        taskManager.addTask("Task A"); // índice 0
        taskManager.addTask("Task B"); // índice 1

        // Intentar actualizar índice 2 (1-based ID) a valor que existe en otro índice -> false
        assertFalse(taskManager.updateTask(2, "Task A")); // B -> A duplicado

        // Actualizar índice 2 a un valor único -> true
        assertTrue(taskManager.updateTask(2, "Task B updated"));
    }

    // ---------- Test para el Helper de Impresión (Cobertura 100%) ----------
    // ¡ESTE ES EL NUEVO TEST QUE NECESITABAS!
    @Test
    @DisplayName("printOperationResult cubre ramas de éxito y fallo")
    void testPrintOperationResult() {
        // Probamos el helper con true y false para cubrir las dos ramas del operador ternario
        taskManager.printOperationResult(true, "Success", "Fail");
        taskManager.printOperationResult(false, "Success", "Fail");
    }

    // ---------- test main para cobertura 100% ----------

    @Test
    @DisplayName("Ejecutar main con todas las combinaciones para cobertura 100%")
    void testMainFullCoverageBranches() {
        // Ejecutar main normal (ahora usa el helper internamente)
        TaskManager.main(new String[]{});
    }
}