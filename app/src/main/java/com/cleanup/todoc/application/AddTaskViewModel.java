package com.cleanup.todoc.application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.di.AppInjector;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

/**
 * ViewModel for adding tasks.
 * This ViewModel provides methods to interact with the Project and Task repositories
 * and exposes LiveData for observing the list of all projects.
 */
public class AddTaskViewModel extends ViewModel {

    // Repositories for managing projects and tasks
    public ProjectRepository projectRepository;
    public TaskRepository taskRepository;

    // LiveData for holding the list of all projects
    private LiveData<List<Project>> allProjectsLiveData;

    /**
     * Constructor for AddTaskViewModel.
     *
     * @param projectRepository The repository for managing projects
     * @param taskRepository The repository for managing tasks
     */
    public AddTaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        // Initialize the repositories with the provided instances
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;

        // Get the LiveData for all projects from the project repository
        allProjectsLiveData = projectRepository.getAllProjects();
    }

    /**
     * Getter for the LiveData of all projects.
     * This method provides LiveData that can be observed for changes in the list of all projects.
     *
     * @return LiveData containing the list of all projects
     */
    public LiveData<List<Project>> getAllProjects() {
        return allProjectsLiveData;
    }

    /**
     * Method to add a new task.
     * This method calls the task repository's method to insert the task into the database.
     *
     * @param task The task to be added
     */
    public void addTask(Task task) {
        // Call the task repository's method to insert the task
        taskRepository.insertTask(task);
    }
}