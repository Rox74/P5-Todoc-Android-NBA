package com.cleanup.todoc.application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.di.AppInjector;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

/**
 * ViewModel for listing tasks.
 * This ViewModel provides methods to interact with the Task repository
 * and exposes LiveData for observing the list of all tasks and sorted tasks.
 */
public class ListTasksViewModel extends ViewModel {

    // Repository for managing tasks
    public TaskRepository taskRepository;

    // LiveData for holding the list of all tasks
    public LiveData<List<Task>> allTasksLiveData;

    /**
     * Constructor for ListTasksViewModel.
     *
     * @param taskRepository The repository for managing tasks
     */
    public ListTasksViewModel(TaskRepository taskRepository) {
        // Initialize the repository with the provided instance
        this.taskRepository = taskRepository;

        // Get the LiveData for all tasks from the task repository
        allTasksLiveData = taskRepository.getAllTasks();
    }

    /**
     * Getter for the LiveData of all tasks.
     * This method provides LiveData that can be observed for changes in the list of all tasks.
     *
     * @return LiveData containing the list of all tasks
     */
    public LiveData<List<Task>> getAllTasks() {
        return allTasksLiveData;
    }

    /**
     * Method to delete a task.
     * This method calls the task repository's method to delete the task from the database.
     *
     * @param task The task to be deleted
     */
    public void deleteTask(Task task) {
        // Call the task repository's method to delete the task
        taskRepository.deleteTask(task);
    }

    /**
     * Method to get all tasks sorted alphabetically by name.
     * This method retrieves tasks sorted alphabetically from the task repository.
     *
     * @return LiveData containing the list of tasks sorted alphabetically
     */
    public LiveData<List<Task>> getAllTasksSortedAlphabetically() {
        return taskRepository.getAllTasksSortedAlphabetically();
    }

    /**
     * Method to get all tasks sorted alphabetically by name in inverted order.
     * This method retrieves tasks sorted alphabetically in inverted order from the task repository.
     *
     * @return LiveData containing the list of tasks sorted alphabetically in inverted order
     */
    public LiveData<List<Task>> getAllTasksSortedAlphabeticallyInverted() {
        return taskRepository.getAllTasksSortedAlphabeticallyInverted();
    }

    /**
     * Method to get all tasks sorted by date with the most recent first.
     * This method retrieves tasks sorted by date with the most recent first from the task repository.
     *
     * @return LiveData containing the list of tasks sorted by date with the most recent first
     */
    public LiveData<List<Task>> getAllTasksSortedByDateRecentFirst() {
        return taskRepository.getAllTasksSortedByDateRecentFirst();
    }

    /**
     * Method to get all tasks sorted by date with the oldest first.
     * This method retrieves tasks sorted by date with the oldest first from the task repository.
     *
     * @return LiveData containing the list of tasks sorted by date with the oldest first
     */
    public LiveData<List<Task>> getAllTasksSortedByDateOldFirst() {
        return taskRepository.getAllTasksSortedByDateOldFirst();
    }
}