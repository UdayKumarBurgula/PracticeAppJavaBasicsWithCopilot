package com.uday.copilot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The TaskManager class is responsible for managing a list of tasks.
 * It allows adding tasks, listing all tasks, and marking tasks as done.
 */
public class TaskManager {
    
    /**
     * The Task class represents a single task with a description and a completion status.
     */
    public static class Task {
        private final UUID id;
        private final String description;
        private volatile boolean done;

        /**
         * Constructs a Task with the specified description.
         *
         * @param description non-null task description
         */
        public Task(String description) {
            this.id = UUID.randomUUID();
            this.description = Objects.requireNonNull(description, "description must not be null");
            this.done = false;
        }

      public UUID getId() {
            return id;
        }

      public String getDescription() {
            return description;
        }

      public boolean isDone() {
            return done;
        }

      public void setDone(boolean done) {
            this.done = done;
        }

        @Override
        public String toString() {
            return (done ? "[X] " : "[ ] ") + description;
        }

      @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return id.equals(task.id);
        }

      @Override
        public int hashCode() {
            return id.hashCode();
        }

    }
    
    private final List<Task> tasks;
    
    /**
     * Constructs a TaskManager with an empty task list.
     */
    public TaskManager() {
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Adds a new task and returns the created Task instance.
     *
     * @param description non-null description
     * @return created Task
     */
    public Task addTask(String description) {
        Objects.requireNonNull(description, "description must not be null");
        Task t = new Task(description);
        synchronized (tasks) {
            tasks.add(t);
        }
        return t;
    }
    
    /**
     * Returns an unmodifiable copy of the tasks list to prevent external mutation.
     */
    public List<Task> listTasks() {
        synchronized (tasks) {
            return Collections.unmodifiableList(new ArrayList<>(tasks));
        }
    }
    
    /**
     * Marks the first task matching the description as done.
     *
     * @return true if a task was found and marked, false otherwise
     */
    public boolean markTaskDone(String description) {
        Objects.requireNonNull(description, "description must not be null");
        synchronized (tasks) {
            for (Task t : tasks) {
                if (t.getDescription().equals(description)) {
                    t.setDone(true);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Marks the task with the given id as done.
     */
    public boolean markTaskDone(UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        synchronized (tasks) {
            for (Task t : tasks) {
                if (t.getId().equals(id)) {
                    t.setDone(true);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Removes the first task that matches the description.
     *
     * @return true if a task was removed, false otherwise
     */
    public boolean removeTask(String description) {
        Objects.requireNonNull(description, "description must not be null");
        synchronized (tasks) {
            Iterator<Task> it = tasks.iterator();
            while (it.hasNext()) {
                if (it.next().getDescription().equals(description)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Removes the task with the given id.
     */
    public boolean removeTask(UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        synchronized (tasks) {
            Iterator<Task> it = tasks.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(id)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Finds all tasks matching the given description.
     */
    public List<Task> findTasks(String description) {
        Objects.requireNonNull(description, "description must not be null");
        synchronized (tasks) {
            List<Task> found = new ArrayList<>();
            for (Task t : tasks) {
                if (t.getDescription().equals(description)) {
                    found.add(t);
                }
            }
            return Collections.unmodifiableList(found);
        }
    }

}