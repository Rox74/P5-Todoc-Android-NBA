package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Data Access Object (DAO) for the Project entity.
 * Defines methods to interact with the "projects" table in the database.
 */
@Dao
public interface ProjectDao {

    /**
     * Retrieves all projects from the database.
     *
     * @return a LiveData list of all projects.
     */
    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getAllProjectsLiveData();

    /**
     * Inserts a list of projects into the database.
     * If a project already exists, it will be ignored.
     *
     * @param projects the list of projects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Project> projects);
}