package com.cleanup.todoc.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * <p>Model for the tasks of the application.</p>
 * <p>This class represents a task with a unique identifier, a project associated with the task, a name, and a creation timestamp.</p>
 */
@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(entity = Project.class,
                parentColumns = "projectId",
                childColumns = "projectId"))
public class Task {
    /**
     * The unique identifier of the task.
     */
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    /**
     * The project associated with the task.
     * Using @Embedded annotation to retrieve project data related to the task.
     */
    @Embedded
    public Project project;

    /**
     * The name of the task.
     */
    @NonNull
    public String taskName;

    /**
     * The timestamp when the task has been created.
     */
    @ColumnInfo(name = "creation_timestamp")
    public long taskCreationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param taskId                the unique identifier of the task to set
     * @param project               the project associated with the task to set
     * @param taskName              the name of the task to set
     * @param taskCreationTimestamp the timestamp when the task has been created to set
     */
    public Task(long taskId, @NonNull Project project, @NonNull String taskName, long taskCreationTimestamp) {
        this.taskId = taskId;
        this.project = project;
        this.taskName = taskName;
        this.taskCreationTimestamp = taskCreationTimestamp;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return taskId;
    }

    /**
     * Returns the project associated with the task.
     *
     * @return the project associated with the task
     */
    @Nullable
    public Project getProject() {
        return project;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return taskName;
    }
}