package com.cleanup.todoc.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Database class representing the Room database for the application.
 * This database holds the Project and Task entities.
 */
@Database(entities = {Project.class, Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Abstract method to retrieve the Project DAO.
     *
     * @return the Project DAO.
     */
    public abstract ProjectDao projectDao();

    /**
     * Abstract method to retrieve the Task DAO.
     *
     * @return the Task DAO.
     */
    public abstract TaskDao taskDao();

    // Singleton instance of AppDatabase
    private static AppDatabase INSTANCE;

    /**
     * Gets a reference to the database instance. If the database doesn't exist yet, it will be created.
     * If it does exist, the existing instance will be returned.
     *
     * @param context the application context.
     * @return the database instance.
     */
    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // Build the database instance
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "todoc_database")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d("AppDatabase", "Database created.");
                        }
                    })
                    .build();

            // Prepopulate the database with initial data
            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    prepopulateDatabase(INSTANCE);
                }
            });
        }
        return INSTANCE;
    }

    /**
     * Populates the database with initial data if it's empty.
     *
     * @param db the database instance.
     */
    private static void prepopulateDatabase(final AppDatabase db) {
        // List of initial projects to insert into the database
        List<Project> projects = Arrays.asList(
                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2)
        );

        // Insert the initial projects into the database
        db.projectDao().insertAll(projects);
        Log.d("AppDatabase", "Projects inserted: " + projects.size());
    }
}