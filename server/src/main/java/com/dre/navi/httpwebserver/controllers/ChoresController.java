package com.dre.navi.httpwebserver.controllers;

import com.dre.navi.httpwebserver.model.Chore;
import com.dre.navi.httpwebserver.services.ChoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ChoresController
{
    @Autowired
    private ChoresService choresService;

    @GetMapping("/chores/user")
    public List<Chore> getAllTasksForChores(@RequestParam String user, @RequestParam String day) throws SQLException
    {
        System.out.println(day);
        return choresService.getAllChoresForUser(user, day);
    }
    @PostMapping("/chores/add")
    public List<Chore> addTaskForChores(@RequestBody Chore chore) throws SQLException
    {
        System.out.println("Adding routine :" + chore.getDay());
        return choresService.addChore(chore);
    }

    @PostMapping("/chores/update")
    public List<Chore> getAllTasksForChores(@RequestBody Chore chore) throws SQLException
    {
        return choresService.updateChore(chore);
    }
    @PostMapping("/chores/delete")
    public List<Chore> deleteChore(@RequestBody Chore chore) throws SQLException
    {
        return choresService.deleteChore(chore);
    }

    @PostMapping("/chores/swap")
    public List<Chore> swapChoresOrder(@RequestBody Chore chore, @RequestParam String direction) throws SQLException
    {
        return choresService.swapOrderForChore(chore, direction);
    }
}
