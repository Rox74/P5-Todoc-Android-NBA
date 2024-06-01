package com.cleanup.todoc.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Repository for managing Task data operations.
 * Provides a clean API to the data source.
 */
public class TaskRepository {
    // DAO for accessing task data
    public final TaskDao taskDao;
    // LiveData for holding the list of all tasks
    private LiveData<List<Task>> allTasks;

    /**
     * Constructor to initialize the TaskRepository.
     *
     * @param taskDao the DAO for accessing task data
     */
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        allTasks = taskDao.getAllTasksLiveData();
        Log.d("TaskRepository", "TaskRepository initialized.");
    }

    /**
     * Getter for the LiveData of all tasks.
     *
     * @return LiveData containing the list of all tasks
     */
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    /**
     * Inserts a task into the database.
     * Runs the insertion in a separate thread to avoid blocking the main thread.
     *
     * @param task the task to insert
     */
    public void insertTask(Task task) {
        new Thread(() -> {
            taskDao.insertTask(task);
        }).start();
    }

    /**
     * Deletes a task from the database.
     * Runs the deletion in a separate thread to avoid blocking the main thread.
     *
     * @param task the task to delete
     */
    public void deleteTask(Task task) {
        new Thread(() -> {
            taskDao.deleteTask(task);
        }).start();
    }

    /**
     * Retrieves all tasks sorted alphabetically by name.
     *
     * @return LiveData containing the list of tasks sorted alphabetically
     */
    public LiveData<List<Task>> getAllTasksSortedAlphabetically() {
        return taskDao.getAllTasksSortedAlphabetically();
    }

    /**
     * Retrieves all tasks sorted alphabetically by name in inverted order.
     *
     * @return LiveData containing the list of tasks sorted alphabetically in inverted order
     */
    public LiveData<List<Task>> getAllTasksSortedAlphabeticallyInverted() {
        return taskDao.getAllTasksSortedAlphabeticallyInverted();
    }

    /**
     * Retrieves all tasks sorted by date with the most recent first.
     *
     * @return LiveData containing the list of tasks sorted by date with the most recent first
     */
    public LiveData<List<Task>> getAllTasksSortedByDateRecentFirst() {
        return taskDao.getAllTasksSortedByDateRecentFirst();
    }

    /**
     * Retrieves all tasks sorted by date with the oldest first.
     *
     * @return LiveData containing the list of tasks sorted by date with the oldest first
     */
    public LiveData<List<Task>> getAllTasksSortedByDateOldFirst() {
        return taskDao.getAllTasksSortedByDateOldFirst();
    }
}