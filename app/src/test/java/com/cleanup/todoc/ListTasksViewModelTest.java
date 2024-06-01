package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.application.ListTasksViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.application.ViewModelFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/**
 * Unit tests for the ListTasksViewModel class.
 * Uses RobolectricTestRunner to run the tests on the JVM.
 */
@RunWith(RobolectricTestRunner.class)
public class ListTasksViewModelTest {

    // Mocked TaskRepository and LiveData for tasks
    @Mock
    TaskRepository mockTaskRepository;
    @Mock
    LiveData<List<Task>> mockLiveDataTasks;

    // Instance of ListTasksViewModel under test
    private ListTasksViewModel listTasksViewModel;

    // Rule to allow LiveData to work synchronously in unit tests
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    /**
     * Sets up the test environment before each test.
     */
    @Before
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Configure the mockTaskRepository to return the mock LiveData when getAllTasks() is called
        Mockito.when(mockTaskRepository.getAllTasks()).thenReturn(mockLiveDataTasks);

        // Initialize ViewModelFactory with the mocked repositories
        ViewModelFactory factory = new ViewModelFactory(null, mockTaskRepository); // Pass null for projectRepository (not needed)

        // Use the factory to create the ListTasksViewModel
        listTasksViewModel = factory.create(ListTasksViewModel.class);
    }

    /**
     * Tests that getAllTasks() returns the expected LiveData containing the list of tasks.
     */
    @Test
    public void getAllTasks_ReturnsLiveDataTasks() {
        // Act: Retrieve the LiveData of all tasks from the ViewModel
        LiveData<List<Task>> result = listTasksViewModel.getAllTasks();

        // Assert: Verify that the LiveData is not null and is the same as the mocked LiveData
        assertNotNull(result);
        assertEquals(mockLiveDataTasks, result);
    }

    /**
     * Tests that deleteTask() calls the deleteTask() method of the TaskRepository with the correct task.
     */
    @Test
    public void deleteTask_CallsTaskRepositoryDeleteTask() {
        // Arrange: Create a sample project and task to delete
        Project project = new Project(1L, "Test Project", 0xFF0000);
        Task taskToDelete = new Task(1L, project, "Test Task", System.currentTimeMillis());

        // Act: Delete the task using the ViewModel
        listTasksViewModel.deleteTask(taskToDelete);

        // Assert: Verify that the deleteTask() method was called with the correct task
        Mockito.verify(mockTaskRepository).deleteTask(taskToDelete);
    }

    /**
     * Tests that getAllTasksSortedAlphabetically() returns the expected LiveData containing the list of tasks sorted alphabetically.
     */
    @Test
    public void getAllTasksSortedAlphabetically_ReturnsLiveDataTasks() {
        // Arrange: Configure the mockTaskRepository to return the mock LiveData for sorted tasks
        Mockito.when(mockTaskRepository.getAllTasksSortedAlphabetically()).thenReturn(mockLiveDataTasks);

        // Act: Retrieve the LiveData of sorted tasks from the ViewModel
        LiveData<List<Task>> result = listTasksViewModel.getAllTasksSortedAlphabetically();

        // Assert: Verify that the LiveData is not null and is the same as the mocked LiveData
        assertNotNull(result);
        assertEquals(mockLiveDataTasks, result);
    }

    /**
     * Tests that getAllTasksSortedAlphabeticallyInverted() returns the expected LiveData containing the list of tasks sorted alphabetically in inverted order.
     */
    @Test
    public void getAllTasksSortedAlphabeticallyInverted_ReturnsLiveDataTasks() {
        // Arrange: Configure the mockTaskRepository to return the mock LiveData for sorted tasks in inverted order
        Mockito.when(mockTaskRepository.getAllTasksSortedAlphabeticallyInverted()).thenReturn(mockLiveDataTasks);

        // Act: Retrieve the LiveData of sorted tasks in inverted order from the ViewModel
        LiveData<List<Task>> result = listTasksViewModel.getAllTasksSortedAlphabeticallyInverted();

        // Assert: Verify that the LiveData is not null and is the same as the mocked LiveData
        assertNotNull(result);
        assertEquals(mockLiveDataTasks, result);
    }

    /**
     * Tests that getAllTasksSortedByDateRecentFirst() returns the expected LiveData containing the list of tasks sorted by date with the most recent first.
     */
    @Test
    public void getAllTasksSortedByDateRecentFirst_ReturnsLiveDataTasks() {
        // Arrange: Configure the mockTaskRepository to return the mock LiveData for tasks sorted by date (recent first)
        Mockito.when(mockTaskRepository.getAllTasksSortedByDateRecentFirst()).thenReturn(mockLiveDataTasks);

        // Act: Retrieve the LiveData of tasks sorted by date (recent first) from the ViewModel
        LiveData<List<Task>> result = listTasksViewModel.getAllTasksSortedByDateRecentFirst();

        // Assert: Verify that the LiveData is not null and is the same as the mocked LiveData
        assertNotNull(result);
        assertEquals(mockLiveDataTasks, result);
    }

    /**
     * Tests that getAllTasksSortedByDateOldFirst() returns the expected LiveData containing the list of tasks sorted by date with the oldest first.
     */
    @Test
    public void getAllTasksSortedByDateOldFirst_ReturnsLiveDataTasks() {
        // Arrange: Configure the mockTaskRepository to return the mock LiveData for tasks sorted by date (oldest first)
        Mockito.when(mockTaskRepository.getAllTasksSortedByDateOldFirst()).thenReturn(mockLiveDataTasks);

        // Act: Retrieve the LiveData of tasks sorted by date (oldest first) from the ViewModel
        LiveData<List<Task>> result = listTasksViewModel.getAllTasksSortedByDateOldFirst();

        // Assert: Verify that the LiveData is not null and is the same as the mocked LiveData
        assertNotNull(result);
        assertEquals(mockLiveDataTasks, result);
    }
}