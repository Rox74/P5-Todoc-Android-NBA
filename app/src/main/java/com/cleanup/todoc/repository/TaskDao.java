package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Data Access Object (DAO) for the Task entity.
 * Defines methods to interact with the "tasks" table in the database.
 */
@Dao
public interface TaskDao {

    /**
     * Retrieves all tasks from the database.
     *
     * @return a LiveData list of all tasks.
     */
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasksLiveData();

    /**
     * Retrieves all tasks from the database sorted alphabetically by name.
     *
     * @return a LiveData list of tasks sorted alphabetically.
     */
    @Query("SELECT * FROM tasks ORDER BY taskName ASC")
    LiveData<List<Task>> getAllTasksSortedAlphabetically();

    /**
     * Retrieves all tasks from the database sorted alphabetically by name in inverted order.
     *
     * @return a LiveData list of tasks sorted alphabetically in inverted order.
     */
    @Query("SELECT * FROM tasks ORDER BY taskName DESC")
    LiveData<List<Task>> getAllTasksSortedAlphabeticallyInverted();

    /**
     * Retrieves all tasks from the database sorted by date with the most recent first.
     *
     * @return a LiveData list of tasks sorted by date with the most recent first.
     */
    @Query("SELECT * FROM tasks ORDER BY creation_timestamp DESC")
    LiveData<List<Task>> getAllTasksSortedByDateRecentFirst();

    /**
     * Retrieves all tasks from the database sorted by date with the oldest first.
     *
     * @return a LiveData list of tasks sorted by date with the oldest first.
     */
    @Query("SELECT * FROM tasks ORDER BY creation_timestamp ASC")
    LiveData<List<Task>> getAllTasksSortedByDateOldFirst();

    /**
     * Inserts a list of tasks into the database.
     * If a task already exists, it will be ignored.
     *
     * @param tasks the list of tasks to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Task> tasks);

    /**
     * Inserts a single task into the database.
     * If the task already exists, it will be ignored.
     *
     * @param task the task to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Task task);

    /**
     * Deletes a task from the database.
     *
     * @param task the task to delete.
     */
    @Delete
    void deleteTask(Task task);
}