package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 * <p>This adapter binds task data to the views in the RecyclerView.</p>
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with.
     */
    @NonNull
    private List<Task> tasks;

    /**
     * The listener for when a task needs to be deleted.
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set.
     * @param deleteTaskListener the listener for when a task needs to be deleted.
     */
    TasksAdapter(@NonNull final List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with and notifies the data set change.
     *
     * @param tasks the list of tasks the adapter deals with to set.
     */
    void updateTasks(@NonNull final List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Inflate the item view for the task
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        // Bind the task data to the view holder
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Listener for deleting tasks.
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted.
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the tasks list.</p>
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project.
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task.
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project.
         */
        private final TextView lblProjectName;

        /**
         * The delete icon.
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted.
         */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item.
         * @param deleteTaskListener the listener for when a task needs to be deleted to set.
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            // Initialize the views
            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            // Set the click listener for the delete icon
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        // Call the delete task listener when delete icon is clicked
                        TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                    }
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view.
         */
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task); // Set the task as the tag for the delete icon

            final Project taskProject = task.getProject();
            if (taskProject != null) {
                // Set the project color and name if the project is not null
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
            } else {
                // Hide the project icon and clear the project name if the project is null
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }
        }
    }
}