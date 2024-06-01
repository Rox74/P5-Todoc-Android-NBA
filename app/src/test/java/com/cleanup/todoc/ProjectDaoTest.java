package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.AppDatabase;
import com.cleanup.todoc.repository.ProjectDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for ProjectDao.
 */
@RunWith(RobolectricTestRunner.class)
public class ProjectDaoTest {

    private AppDatabase database;
    private ProjectDao projectDao;

    /**
     * Allows LiveData to work synchronously in tests.
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /**
     * Setup the in-memory database before each test.
     */
    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        projectDao = database.projectDao();
    }

    /**
     * Close the database after each test.
     */
    @After
    public void tearDown() {
        database.close();
    }

    /**
     * Test for inserting and retrieving projects.
     */
    @Test
    public void insertAndRetrieveProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Project 1", 0xFF0000));
        projects.add(new Project(2L, "Project 2", 0x0000FF));

        projectDao.insertAll(projects);

        LiveData<List<Project>> liveDataProjects = projectDao.getAllProjectsLiveData();
        liveDataProjects.observeForever(projectsList -> {
            assertNotNull(projectsList);
            assertEquals(2, projectsList.size());
            assertEquals("Project 1", projectsList.get(0).getName());
            assertEquals("Project 2", projectsList.get(1).getName());
        });
    }
}