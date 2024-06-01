package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.R;
import com.cleanup.todoc.application.AddTaskViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.di.AppInjector;
import com.cleanup.todoc.application.ViewModelFactory;

import java.util.Date;

/**
 * A fragment responsible for adding a new task.
 * This fragment allows the user to enter a task name, select a project, and add the task to the database.
 */
public class AddTaskFragment extends Fragment {

    private EditText txtTaskName;
    private Spinner projectSpinner;
    private Button fabAddTask;

    private AddTaskViewModel addTaskViewModel;

    /**
     * Required empty public constructor for fragment instantiation.
     */
    public AddTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Called to do initial creation of a fragment.
     * Initializes the ViewModel.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                AppInjector.provideProjectRepository(),
                AppInjector.provideTaskRepository()
        );

        // Initialize the ViewModel
        addTaskViewModel = new ViewModelProvider(this, factory).get(AddTaskViewModel.class);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        // Initialize UI components
        txtTaskName = view.findViewById(R.id.txt_task_name);
        projectSpinner = view.findViewById(R.id.project_spinner);
        fabAddTask = view.findViewById(R.id.but_add_task);

        return view;
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * Initializes the spinner with project data and sets up the button click listener.
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe the list of projects from the ViewModel and populate the spinner when the data changes
        addTaskViewModel.getAllProjects().observe(getViewLifecycleOwner(), projects -> {
            // Create an ArrayAdapter using the list of projects and set it to the spinner
            ArrayAdapter<Project> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, projects);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectSpinner.setAdapter(adapter);
        });

        // Set the click listener for the add task button
        fabAddTask.setOnClickListener(v -> addTask());
    }

    /**
     * Adds a new task based on user input.
     * Validates the task name and project selection, then creates a new Task object and passes it to the ViewModel for insertion into the database.
     */
    private void addTask() {
        // Get the task name from the EditText
        String taskName = txtTaskName.getText().toString();
        // Get the selected project from the spinner
        Project selectedProject = (Project) projectSpinner.getSelectedItem();

        // Validate the task name
        if (TextUtils.isEmpty(taskName.trim())) {
            // Show error if task name is empty
            txtTaskName.setError(getString(R.string.empty_task_name));
            return;
        }

        // Validate the selected project
        if (selectedProject == null) {
            // Handle the case when no project is selected
            return;
        }

        // Create a new Task object with the input data
        Task newTask = new Task(
                0,  // Task ID (0 for new task)
                selectedProject,  // Selected project
                taskName,  // Task name
                new Date().getTime()  // Current timestamp
        );

        // Add the new task using the ViewModel
        addTaskViewModel.addTask(newTask);

        // Navigate back to the previous fragment or activity
        requireActivity().onBackPressed();
    }
}