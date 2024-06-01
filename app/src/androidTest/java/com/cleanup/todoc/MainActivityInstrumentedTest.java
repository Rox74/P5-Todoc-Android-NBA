package com.cleanup.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.cleanup.todoc.TestUtils.withRecyclerView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class contains instrumented tests for the MainActivity.
 * It runs on an Android device and tests the functionality of adding, removing, and sorting tasks.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    /**
     * Rule to launch MainActivity before each test.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests the functionality of adding and removing a task.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
    @Test
    public void addAndRemoveTask() throws InterruptedException {
        // Click on the add task FAB button
        onView(withId(R.id.fab_add_task)).perform(ViewActions.click());

        // Wait for the UI to update after the click
        Thread.sleep(200);

        // Enter a task name
        onView(withId(R.id.txt_task_name)).perform(ViewActions.replaceText("Task example"));
        // Click on the add task button
        onView(withId(R.id.but_add_task)).perform(ViewActions.click());

        // Wait for the task to be added and observed
        Thread.sleep(200);

        // Check that the no task label is not displayed
        onView(withId(R.id.lbl_no_task)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        // Check that the task list is displayed
        onView(withId(R.id.list_tasks)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        // Check that the task name is displayed correctly
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("Task example")));

        // Click on the delete button for the task
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete)).perform(ViewActions.click());

        // Wait for the task to be removed and observed
        Thread.sleep(200);

        // Check that the no task label is displayed again
        onView(withId(R.id.lbl_no_task)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        // Check that the task list is not displayed
        onView(withId(R.id.list_tasks)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    /**
     * Tests the functionality of sorting tasks in various orders.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
    @Test
    public void sortTasks() throws InterruptedException {

        // Wait for initial data to be ready
        Thread.sleep(200);

        // Add tasks with different names
        addTask("aaa Task example");
        Thread.sleep(200);
        addTask("zzz Task example");
        Thread.sleep(200);
        addTask("hhh Task example");
        Thread.sleep(200);

        // Check initial order of tasks
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Task example")));

        // Sort tasks alphabetically
        onView(withId(R.id.action_filter)).perform(ViewActions.click());
        onView(ViewMatchers.withText(R.string.sort_alphabetical)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Task example")));

        // Sort tasks alphabetically inverted
        onView(withId(R.id.action_filter)).perform(ViewActions.click());
        onView(ViewMatchers.withText(R.string.sort_alphabetical_invert)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Task example")));

        // Sort tasks by oldest first
        onView(withId(R.id.action_filter)).perform(ViewActions.click());
        onView(ViewMatchers.withText(R.string.sort_oldest_first)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Task example")));

        // Sort tasks by recent first
        onView(withId(R.id.action_filter)).perform(ViewActions.click());
        onView(ViewMatchers.withText(R.string.sort_recent_first)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Task example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Task example")));

        // Delete tasks
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete)).perform(ViewActions.click());
        Thread.sleep(200);
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete)).perform(ViewActions.click());
    }

    /**
     * Helper method to add a task with the specified name.
     *
     * @param taskName the name of the task to be added
     */
    private void addTask(String taskName) {
        onView(withId(R.id.fab_add_task)).perform(ViewActions.click());
        onView(withId(R.id.txt_task_name)).perform(ViewActions.replaceText(taskName));
        onView(withId(R.id.but_add_task)).perform(ViewActions.click());
    }
}