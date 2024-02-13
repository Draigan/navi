package com.dre.navi.httpwebserver.controllers;

import com.dre.navi.httpwebserver.model.MorningRoutine;
import com.dre.navi.httpwebserver.services.MorningRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class MorningRoutinesController
{
    @Autowired
    private MorningRoutineService morningRoutineService;

    @GetMapping("/morningroutines/user")
    public List<MorningRoutine> getAllTasksForUser(@RequestParam String user) throws SQLException
    {
        return morningRoutineService.getAllMorningRoutinesForUser(user);
    }
    @PostMapping("/morningroutine/add")
    public List<MorningRoutine> addTaskForUser(@RequestBody MorningRoutine morningRoutine) throws SQLException
    {
        System.out.println("Adding task");
        return morningRoutineService.addMorningRoutine(morningRoutine);
    }

//    @PostMapping("/tasks/update")
//    public String getAllTasksForUser(@RequestBody Task task) throws SQLException
//    {
//        taskService.updateTask(task);
//        System.out.println(task.getName());
//        return "Recieved row/task" + task.getIndex();
//    }
//
//    @PostMapping("/tasks/delete")
//    public List<Task> deleteTask(@RequestBody Task task) throws SQLException
//    {
//        System.out.println("deleting task:" + task.getName());
//        return taskService.deleteTask(task);
//    }
//
//    @PostMapping("/tasks/swap")
//    public List<Task> swapTasksOrder(@RequestBody Task task, @RequestParam String direction) throws SQLException
//    {
//        return taskService.swapOrderForTasks(task, direction);
//    }
}
