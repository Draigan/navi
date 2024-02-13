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
        db.updateTask(task);

    }

    public List<Task> addNewTask(Task task) throws SQLException
    {
        List<Task> tasks = db.selectAllFromTasksForUser(task.getUserId());
        int size = tasks.size() ;
        task.setIndex(tasks.size());
        db.addTask(task);
        return db.selectAllFromTasksForUser(task.getUserId());
    }

    public List<Task> deleteTask(Task task) throws SQLException
    {
        db.deleteTask(task.getId());
        return db.selectAllFromTasksForUser(task.getUserId());
    }

    // We need to swap entries based on their indexes the only problem is
    // that there could be gaps in the indexes. So we need to find the next
    // closest and swap with that one
    public List<Task> swapOrderForTasks(Task task, String direction) throws SQLException
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
        if (currentTaskIndex == -1) return tasks;

        // In the case of direction up
        if (Objects.equals(direction, "up"))
        {
            if (currentTaskIndex == 0) return tasks; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = task.getIndex();
            // Get the second index
            int indexTwo = tasks.get(currentTaskIndex - 1).getIndex();
            db.swapOrderForTasks(indexOne, indexTwo);
            return db.selectAllFromTasksForUser(task.getUserId());
        }

        if (Objects.equals(direction, "down"))
        {
            if (currentTaskIndex == tasks.size() - 1) return tasks; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = task.getIndex();
            // Get the second index
            int indexTwo = tasks.get(currentTaskIndex + 1).getIndex();
            db.swapOrderForTasks(indexOne, indexTwo);
            return db.selectAllFromTasksForUser(task.getUserId());
        }


        return tasks;
    }

}
