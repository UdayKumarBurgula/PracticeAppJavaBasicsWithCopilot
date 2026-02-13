package com.uday.copilot;

import org.junit.Before;
import org.junit.Test;

import com.uday.copilot.TaskManager.Task;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Unit tests for the TaskManager class.
 */
public class TaskManagerTest {
    
    private TaskManager taskManager;
    
    @Before
    public void setUp() {
        taskManager = new TaskManager();
    }
    
    // Task Inner Class Tests
    
    @Test
    public void testTaskConstructor() {
        TaskManager.Task task = new TaskManager.Task("Buy groceries");
        assertEquals("Buy groceries", task.getDescription());
        assertFalse(task.isDone());
        assertNotNull(task.getId());
    }
    
    @Test
    public void testTaskGetDescription() {
        TaskManager.Task task = new TaskManager.Task("Complete project");
        assertEquals("Complete project", task.getDescription());
    }
    
    @Test
    public void testTaskIsDoneInitiallyFalse() {
        TaskManager.Task task = new TaskManager.Task("Test task");
        assertFalse(task.isDone());
    }
    
    @Test
    public void testTaskSetDoneTrue() {
        TaskManager.Task task = new TaskManager.Task("Test task");
        task.setDone(true);
        assertTrue(task.isDone());
    }
    
    @Test
    public void testTaskSetDoneFalse() {
        TaskManager.Task task = new TaskManager.Task("Test task");
        task.setDone(true);
        task.setDone(false);
        assertFalse(task.isDone());
    }
    
    @Test
    public void testTaskToStringIncomplete() {
        TaskManager.Task task = new TaskManager.Task("Incomplete task");
        assertEquals("[ ] Incomplete task", task.toString());
    }
    
    @Test
    public void testTaskToStringComplete() {
        TaskManager.Task task = new TaskManager.Task("Complete task");
        task.setDone(true);
        assertEquals("[X] Complete task", task.toString());
    }
    
    // TaskManager Constructor Tests
    
    @Test
    public void testTaskManagerConstructor() {
        assertNotNull(taskManager);
        assertEquals(0, taskManager.listTasks().size());
    }
    
    // addTask Tests
    
    @Test
    public void testAddSingleTask() {
        taskManager.addTask("Task 1");
        assertEquals(1, taskManager.listTasks().size());
        assertEquals("Task 1", taskManager.listTasks().get(0).getDescription());
    }
    
    @Test
    public void testAddMultipleTasks() {
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");
        assertEquals(3, taskManager.listTasks().size());
    }
    
    @Test
    public void testAddTasksAreIncomplete() {
        taskManager.addTask("New task");
        assertFalse(taskManager.listTasks().get(0).isDone());
    }
    
    @Test
    public void testAddDuplicateTasks() {
        taskManager.addTask("Duplicate");
        taskManager.addTask("Duplicate");
        assertEquals(2, taskManager.listTasks().size());
    }
    
    // listTasks Tests
    
    @Test
    public void testListTasksEmpty() {
        List<Task> tasks = taskManager.listTasks();
        assertEquals(0, tasks.size());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testListTasksReturnsUnmodifiable() {
        taskManager.addTask("Original task");
        List<TaskManager.Task> tasks = taskManager.listTasks();
        tasks.add(new TaskManager.Task("Added externally"));
    }
    
    @Test
    public void testListTasksPreservesOrder() {
        taskManager.addTask("First");
        taskManager.addTask("Second");
        taskManager.addTask("Third");
        List<TaskManager.Task> tasks = taskManager.listTasks();
        assertEquals("First", tasks.get(0).getDescription());
        assertEquals("Second", tasks.get(1).getDescription());
        assertEquals("Third", tasks.get(2).getDescription());
    }
    
    // markTaskDone Tests
    
    @Test
    public void testMarkTaskDoneSuccess() {
        taskManager.addTask("Complete me");
        boolean result = taskManager.markTaskDone("Complete me");
        assertTrue(result);
        assertTrue(taskManager.listTasks().get(0).isDone());
    }
    
    @Test
    public void testMarkTaskDoneNotFound() {
        taskManager.addTask("Existing task");
        boolean result = taskManager.markTaskDone("Non-existent task");
        assertFalse(result);
    }
    
    @Test
    public void testMarkTaskDoneEmptyList() {
        boolean result = taskManager.markTaskDone("Any task");
        assertFalse(result);
    }
    
    @Test
    public void testMarkTaskDoneMultipleTasks() {
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");
        taskManager.markTaskDone("Task 2");
        assertFalse(taskManager.listTasks().get(0).isDone());
        assertTrue(taskManager.listTasks().get(1).isDone());
        assertFalse(taskManager.listTasks().get(2).isDone());
    }
    
    @Test
    public void testMarkTaskDoneMarksFirstMatch() {
        taskManager.addTask("Duplicate");
        taskManager.addTask("Duplicate");
        taskManager.markTaskDone("Duplicate");
        assertTrue(taskManager.listTasks().get(0).isDone());
        assertFalse(taskManager.listTasks().get(1).isDone());
    }
    
    @Test
    public void testMarkTaskDoneToggle() {
        taskManager.addTask("Toggle task");
        taskManager.markTaskDone("Toggle task");
        assertTrue(taskManager.listTasks().get(0).isDone());
        taskManager.markTaskDone("Toggle task");
        assertTrue(taskManager.listTasks().get(0).isDone());
    }

    @Test
    public void testRemoveTask() {
        taskManager.addTask("Task to be removed");
        taskManager.addTask("Task to stay");
        boolean removed = taskManager.removeTask("Task to be removed");
        assertTrue(removed);
        List<TaskManager.Task> tasks = taskManager.listTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task to stay", tasks.get(0).getDescription());
    }

    @Test
    public void testMarkTaskDoneById() {
        Task t = taskManager.addTask("By id");
        boolean res = taskManager.markTaskDone(t.getId());
        assertTrue(res);
        assertTrue(taskManager.listTasks().get(0).isDone());
    }

    @Test
    public void testRemoveTaskById() {
        Task t = taskManager.addTask("Remove me");
        boolean res = taskManager.removeTask(t.getId());
        assertTrue(res);
        assertEquals(0, taskManager.listTasks().size());
    }
}