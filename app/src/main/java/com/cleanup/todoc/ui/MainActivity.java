package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cleanup.todoc.R;
import com.cleanup.todoc.di.AppInjector;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks and allows navigation to add a new task.</p>
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Initializes the activity, sets the content view, and handles fragment transactions.
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this is the state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_container);

        // Initialize AppInjector for dependency injection
        AppInjector.init(this.getApplication());

        // Check if this is the first creation of the activity
        if (savedInstanceState == null) {
            // Add the ListTasksFragment to the activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ListTasksFragment())
                    .commitNow();
        }

        // Set a click listener on the FloatingActionButton to navigate to AddTaskFragment
        findViewById(R.id.fab_add_task).setOnClickListener(view -> {
            // Hide the FAB when navigating to AddTaskFragment
            findViewById(R.id.fab_add_task).setVisibility(View.GONE);
            // Replace the current fragment with AddTaskFragment and add the transaction to the back stack
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddTaskFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Add a listener to show the FAB when returning to ListTasksFragment
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            // Check the back stack entry count to determine if we are back to the ListTasksFragment
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // Show the FAB when back to ListTasksFragment
                findViewById(R.id.fab_add_task).setVisibility(View.VISIBLE);
            }
        });
    }
}