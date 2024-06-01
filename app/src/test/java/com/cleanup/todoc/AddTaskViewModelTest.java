package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.application.AddTaskViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.application.ViewModelFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the AddTaskViewModel class.
 * Uses RobolectricTestRunner to run the tests on the JVM.
 */
@RunWith(RobolectricTestRunner.class)
public class AddTaskViewModelTest {

    // Mocked repositories for projects and tasks
    @Mock
    ProjectRepository mockProjectRepository;
    @Mock
    TaskRepository mockTaskRepository;

    // Captor to capture Task arguments
    @Captor
    ArgumentCaptor<Task> taskCaptor;

    // Instance of AddTaskViewModel under test
    private AddTaskViewModel addTaskViewModel;

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

        // Create a list of mock projects
        List<Project> mockProjects = new ArrayList<>();
        mockProjects.add(new Project(1L, "Project 1", 0xFF0000));
        mockProjects.add(new Project(2L, "Project 2", 0x0000FF));

        // Create a LiveData object containing the list of mock projects
        MutableLiveData<List<Project>> liveDataProjects = new MutableLiveData<>();
        liveDataProjects.setValue(mockProjects);

        // Configure the mockProjectRepository to return the mock LiveData when getAllProjects() is called
        Mockito.when(mockProjectRepository.getAllProjects()).thenReturn(liveDataProjects);

        // Initialize ViewModelFactory with the mocked repositories
        ViewModelFactory factory = new ViewModelFactory(mockProjectRepository, mockTaskRepository);

        // Use the factory to create the AddTaskViewModel
        addTaskViewModel = factory.create(AddTaskViewModel.class);
    }

    /**
     * Tests that getAllProjects() returns the expected LiveData containing the list of mock projects.
     */
    @Test
    public void getAllProjects_ReturnsLiveDataProjects() {
        // Act: Retrieve the LiveData of all projects from the ViewModel
        LiveData<List<Project>> result = addTaskViewModel.getAllProjects();

        // Assert: Verify that the LiveData is not null and contains the correct number of projects
        assertNotNull(result);
        assertEquals(2, result.getValue().size());
    }

    /**
     * Tests that addTask() calls the insertTask() method of the TaskRepository with the correct task.
     */
    @Test
    public void addTask_CallsTaskRepositoryInsertTask() {
        // Arrange: Create a sample project and task
        Project project = new Project(1L, "Test Project", 0x00FF00);
        Task task = new Task(1L, project, "Test Task", System.currentTimeMillis());

        // Act: Add the task using the ViewModel
        addTaskViewModel.addTask(task);

        // Assert: Verify that the insertTask() method was called with the correct task
        Mockito.verify(mockTaskRepository).insertTask(taskCaptor.capture());
        assertEquals(task, taskCaptor.getValue());
    }
}