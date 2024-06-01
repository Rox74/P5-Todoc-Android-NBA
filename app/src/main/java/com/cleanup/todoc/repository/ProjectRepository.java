package com.cleanup.todoc.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Repository for managing Project data operations.
 * Provides a clean API to the data source.
 */
public class ProjectRepository {
    // DAO for accessing project data
    public final ProjectDao projectDao;
    // LiveData for holding the list of all projects
    private LiveData<List<Project>> allProjects;

    /**
     * Constructor to initialize the ProjectRepository.
     *
     * @param projectDao the DAO for accessing project data
     */
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
        allProjects = projectDao.getAllProjectsLiveData();
        Log.d("ProjectRepository", "ProjectRepository initialized.");
    }

    /**
     * Getter for the LiveData of all projects.
     *
     * @return LiveData containing the list of all projects
     */
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}