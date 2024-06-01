package com.cleanup.todoc.di;

import android.app.Application;
import android.util.Log;

import com.cleanup.todoc.repository.AppDatabase;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

/**
 * AppInjector class is responsible for initializing and providing instances of repositories and the database.
 * This class follows the singleton pattern to ensure that only one instance of the database and repositories is created.
 */
public class AppInjector {
    // Singleton instance of the AppDatabase
    private static AppDatabase database;

    // Singleton instances of repositories
    private static ProjectRepository projectRepository;
    private static TaskRepository taskRepository;

    /**
     * Initializes the AppDatabase and repositories.
     * This method should be called once, typically in the Application class of the Android app.
     *
     * @param application the application context used to initialize the database
     */
    public static void init(Application application) {
        // Initialize the database instance
        database = AppDatabase.getDatabase(application);
        Log.d("AppInjector", "Database initialized.");

        // Initialize the project repository
        projectRepository = new ProjectRepository(database.projectDao());
        Log.d("AppInjector", "ProjectRepository initialized.");

        // Initialize the task repository
        taskRepository = new TaskRepository(database.taskDao());
        Log.d("AppInjector", "TaskRepository initialized.");
    }

    /**
     * Provides the singleton instance of the ProjectRepository.
     *
     * @return the ProjectRepository instance
     */
    public static ProjectRepository provideProjectRepository() {
        return projectRepository;
    }

    /**
     * Provides the singleton instance of the TaskRepository.
     *
     * @return the TaskRepository instance
     */
    public static TaskRepository provideTaskRepository() {
        return taskRepository;
    }
}