package com.dre.navi.httpwebserver.services;

import com.dre.navi.httpwebserver.model.Task;
import com.dre.navi.sql.PostgresJDBC;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TasksService
{
    PostgresJDBC db = PostgresJDBC.getInstance();

    public List<Task> getAllTasksForUser(String userId) throws SQLException
    {
        return db.selectAllFromTasksForUser(userId);
    }

    public void updateTask(Task task) throws SQLException
    {
        // We are going to do a quick check to make sure this task is already in records
        // If it's not we are going to pass the task to a create method.
        List<Task> tasks = db.selectAllFromTasksForUser(task.getUserId());
        boolean isNewTask = true; // Assumed to be true
        for (Task item : tasks)
        {
            // If we find the new task id in our db, this isn't a new task
            if (Objects.equals(item.getId(), task.getId()))
            {
                isNewTask = false;
                break;
            }
        }
        if (isNewTask)
        {
            addNewTask(task);
            System.out.println("This is a new row");
            return;
        }
//        if (!tasks.contains(task.getId())){
//        }

        // This is the actual updateTask
        db.updateTask(task);

    }

    public void addNewTask(Task task) throws SQLException
    {
        String taskId = UUID.randomUUID().toString();
        task.setId(taskId);
        db.addTask(task);
    }

    public void deleteTask(Task task) throws SQLException
    {
        db.deleteTask(task.getId());
    }

    // This is probably the most complicated part of the application
    // We need to swap entries based on their indexes the only problem is
    // that there could be gaps in the indexes. So we need to find the next
    // closest and swap with that one
    public void swapOrderForTasks(Task task, String direction) throws SQLException
    {
        // Get all tasks for the user. This list is in the order we want it just has gaps in index
        List<Task> tasks = db.selectAllFromTasksForUser(task.getUserId());
        //  Find the index of the current tasks index
        int currentTaskIndex = -1;
        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).getId().equals(task.getId()))
            {
                currentTaskIndex = i;
                break;
            }
        }
        // Guard clause
        if (currentTaskIndex == -1) return;

        // In the case of direction up
        if (Objects.equals(direction, "up"))
        {
            if (currentTaskIndex == 0) return; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = task.getIndex();
            // Get the second index
            int indexTwo = tasks.get(currentTaskIndex - 1).getIndex();
            db.swapOrderForTasks(indexOne, indexTwo);
            return;
        }


//        db.swapOrderForTasks(indexOne, indexTwo);
    }

}
