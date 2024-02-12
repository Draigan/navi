package com.dre.navi.httpwebserver.controllers;

import com.dre.navi.httpwebserver.model.Task;
import com.dre.navi.httpwebserver.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TaskController
{
    @Autowired
    private TasksService taskService;


    @GetMapping("/tasks/user")
    public List<Task> getAllTasksForUser(@RequestParam String user) throws SQLException
    {
        return taskService.getAllTasksForUser(user);
    }

    @PostMapping("/tasks/update")
    public String getAllTasksForUser(@RequestBody Task task) throws SQLException
    {
        taskService.updateTask(task);
        System.out.println(task.getName());
        return "Recieved row/task" + task.getIndex();
    }
    @PostMapping("/tasks/add")
    public String addTaskForUser(@RequestBody Task task) throws SQLException
    {
        taskService.addNewTask(task);
        System.out.println("Adding task");
        return "Adding task" + task.getId();
    }

    @PostMapping("/tasks/delete")
    public String deleteTask(@RequestBody Task task) throws SQLException
    {
        taskService.deleteTask(task);
        System.out.println("deleting task:" + task.getName());
        return "Recieved row/task" + task.getIndex();
    }

    @PostMapping("/tasks/swap")
    public List<Task> swapTasksOrder(@RequestBody Task task, @RequestParam String direction) throws SQLException
    {
        System.out.println("swapping");
        return taskService.swapOrderForTasks(task, direction);


    }
}
