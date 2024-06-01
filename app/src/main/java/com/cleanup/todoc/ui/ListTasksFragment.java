package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.application.ListTasksViewModel;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.di.AppInjector;
import com.cleanup.todoc.application.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment responsible for displaying a list of tasks and providing filtering options.
 * This fragment shows a list of tasks fetched from a ViewModel and allows the user to filter and delete tasks.
 */
public class ListTasksFragment extends Fragment implements TasksAdapter.DeleteTaskListener {

    private RecyclerView recyclerView;
    private TextView lblNoTasks;

    private ListTasksViewModel listTasksViewModel;
    private TasksAdapter tasksAdapter;

    /**
     * Required empty public constructor for fragment instantiation.
     */
    public ListTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Called to do initial creation of a fragment.
     * Initializes the ViewModel and the adapter, and sets the options menu.
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
        listTasksViewModel = new ViewModelProvider(this, factory).get(ListTasksViewModel.class);
        // Initialize the adapter with an empty list and set the delete task listener
        tasksAdapter = new TasksAdapter(new ArrayList<>(), this);
        // Indicate that this fragment has an options menu
        setHasOptionsMenu(true);
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
        View view = inflater.inflate(R.layout.fragment_list_tasks, container, false);

        // Initialize UI components
        recyclerView = view.findViewById(R.id.list_tasks);
        lblNoTasks = view.findViewById(R.id.lbl_no_task);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(tasksAdapter);

        // Observe initially unsorted tasks
        observeTasks(listTasksViewModel.getAllTasks());

        return view;
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * Sets up the observer for the task list and handles UI visibility based on the task list content.
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe changes in the task list and update the UI accordingly
        listTasksViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            tasksAdapter.updateTasks(tasks);

            // Show or hide the "no tasks" label based on the task list content
            if (tasks.isEmpty()) {
                lblNoTasks.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                lblNoTasks.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Observes changes in the list of tasks and updates the UI accordingly.
     *
     * @param tasksLiveData The LiveData representing the list of tasks to observe.
     */
    private void observeTasks(LiveData<List<Task>> tasksLiveData) {
        // Observe the given LiveData for task list changes
        tasksLiveData.observe(getViewLifecycleOwner(), tasks -> {
            tasksAdapter.updateTasks(tasks);

            // Show or hide the "no tasks" label based on the task list content
            if (tasks.isEmpty()) {
                lblNoTasks.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                lblNoTasks.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Called when a task is deleted from the list.
     * Notifies the ViewModel to delete the specified task.
     *
     * @param task The task to be deleted.
     */
    @Override
    public void onDeleteTask(Task task) {
        // Call the ViewModel to delete the specified task
        listTasksViewModel.deleteTask(task);
    }

    /**
     * Initializes the contents of the fragment's standard options menu.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater The MenuInflater object that can be used to inflate any views in the fragment.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Called when an item in the options menu is selected.
     * Handles the selection of different filtering options and observes the corresponding task list.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
                // Observe tasks sorted alphabetically
                observeTasks(listTasksViewModel.getAllTasksSortedAlphabetically());
                return true;
            case R.id.filter_alphabetical_inverted:
                // Observe tasks sorted alphabetically inverted
                observeTasks(listTasksViewModel.getAllTasksSortedAlphabeticallyInverted());
                return true;
            case R.id.filter_oldest_first:
                // Observe tasks sorted by oldest first
                observeTasks(listTasksViewModel.getAllTasksSortedByDateOldFirst());
                return true;
            case R.id.filter_recent_first:
                // Observe tasks sorted by most recent first
                observeTasks(listTasksViewModel.getAllTasksSortedByDateRecentFirst());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}