package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.AppDatabase;
import com.cleanup.todoc.repository.ProjectDao;
import com.cleanup.todoc.repository.TaskDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Collections;
import java.util.List;

/**
 * Test class for TaskDao.
 */
@RunWith(RobolectricTestRunner.class)
public class TaskDaoTest {

    private AppDatabase database;
    private TaskDao taskDao;
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
        taskDao = database.taskDao();
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
     * Test for inserting and retrieving tasks.
     */
    @Test
    public void insertAndRetrieveTasks() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task 1", System.currentTimeMillis());
        Task task2 = new Task(2L, project, "Task 2", System.currentTimeMillis());

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksLiveData();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertEquals(2, tasksList.size());
            assertEquals("Task 1", tasksList.get(0).getName());
            assertEquals("Task 2", tasksList.get(1).getName());
        });
    }

    /**
     * Test for sorting tasks alphabetically.
     */
    @Test
    public void sortTasksAlphabetically() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task B", System.currentTimeMillis());
        Task task2 = new Task(2L, project, "Task A", System.currentTimeMillis());

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksSortedAlphabetically();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertEquals(2, tasksList.size());
            assertEquals("Task A", tasksList.get(0).getName());
            assertEquals("Task B", tasksList.get(1).getName());
        });
    }

    /**
     * Test for sorting tasks alphabetically in inverted order.
     */
    @Test
    public void sortTasksAlphabeticallyInverted() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task A", System.currentTimeMillis());
        Task task2 = new Task(2L, project, "Task B", System.currentTimeMillis());

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksSortedAlphabeticallyInverted();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertEquals(2, tasksList.size());
            assertEquals("Task B", tasksList.get(0).getName());
            assertEquals("Task A", tasksList.get(1).getName());
        });
    }

    /**
     * Test for sorting tasks by date with the most recent first.
     */
    @Test
    public void sortTasksByDateRecentFirst() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task 1", System.currentTimeMillis() - 1000);
        Task task2 = new Task(2L, project, "Task 2", System.currentTimeMillis());

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksSortedByDateRecentFirst();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertEquals(2, tasksList.size());
            assertEquals("Task 2", tasksList.get(0).getName());
            assertEquals("Task 1", tasksList.get(1).getName());
        });
    }

    /**
     * Test for sorting tasks by date with the oldest first.
     */
    @Test
    public void sortTasksByDateOldFirst() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task 1", System.currentTimeMillis() - 1000);
        Task task2 = new Task(2L, project, "Task 2", System.currentTimeMillis());

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksSortedByDateOldFirst();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertEquals(2, tasksList.size());
            assertEquals("Task 1", tasksList.get(0).getName());
            assertEquals("Task 2", tasksList.get(1).getName());
        });
    }

    /**
     * Test for deleting a task.
     */
    @Test
    public void deleteTask() {
        Project project = new Project(1L, "Test Project", 0x00FF00);
        projectDao.insertAll(Collections.singletonList(project));

        Task task1 = new Task(1L, project, "Task 1", System.currentTimeMillis());
        taskDao.insertTask(task1);

        taskDao.deleteTask(task1);

        LiveData<List<Task>> liveDataTasks = taskDao.getAllTasksLiveData();
        liveDataTasks.observeForever(tasksList -> {
            assertNotNull(tasksList);
            assertTrue(tasksList.isEmpty());
        });
    }
}