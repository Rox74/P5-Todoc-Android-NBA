package com.cleanup.todoc.application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

/**
 * Factory class for creating instances of ViewModels with specific constructor parameters.
 * This factory is used to inject the required repositories into the ViewModels.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // Repositories for managing projects and tasks
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    /**
     * Constructor for ViewModelFactory.
     *
     * @param projectRepository The repository for managing projects
     * @param taskRepository The repository for managing tasks
     */
    public ViewModelFactory(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new instance of the given Class.
     *
     * @param modelClass The class of the ViewModel to create
     * @return A newly created ViewModel
     * @throws IllegalArgumentException if the modelClass is unknown
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(projectRepository, taskRepository);
        } else if (modelClass.isAssignableFrom(ListTasksViewModel.class)) {
            return (T) new ListTasksViewModel(taskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}