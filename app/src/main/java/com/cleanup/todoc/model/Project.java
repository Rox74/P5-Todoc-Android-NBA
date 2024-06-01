package com.cleanup.todoc.model;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <p>Models for project in which tasks are included.</p>
 * <p>This class represents a project with a unique identifier, a name, and a color.</p>
 */
@Entity(tableName = "projects")
public class Project {

    /**
     * The unique identifier of the project.
     */
    @PrimaryKey
    public long projectId;

    /**
     * The name of the project.
     */
    @NonNull
    public String projectName;

    /**
     * The hex (ARGB) code of the color associated to the project.
     */
    @ColorInt
    public int projectColor;

    /**
     * Instantiates a new Project.
     *
     * @param projectId    the unique identifier of the project to set
     * @param projectName  the name of the project to set
     * @param projectColor the hex (ARGB) code of the color associated to the project to set
     */
    public Project(long projectId, @NonNull String projectName, @ColorInt int projectColor) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectColor = projectColor;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getId() {
        return projectId;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return projectName;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return projectColor;
    }

    /**
     * Returns the string representation of the project, which is its name.
     *
     * @return the name of the project as a string
     */
    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}