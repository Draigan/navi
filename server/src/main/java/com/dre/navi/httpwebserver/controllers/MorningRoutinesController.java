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

    @GetMapping("/navi/morningroutines/user")
    public List<MorningRoutine> getAllRoutineForUser(@RequestParam String user) throws SQLException
    {
        return morningRoutineService.getAllMorningRoutinesForUser(user);
    }
    @PostMapping("/navi/morningroutines/add")
    public List<MorningRoutine> addRoutine(@RequestBody MorningRoutine choresRoutine) throws SQLException
    {
        System.out.println("Adding routine");
        return morningRoutineService.addMorningRoutine(choresRoutine);
    }

    @PostMapping("/navi/morningroutines/update")
    public List<MorningRoutine> updateRoutine(@RequestBody MorningRoutine choresRoutine) throws SQLException
    {
        return morningRoutineService.updateMorningRoutine(choresRoutine);
    }
    @PostMapping("/navi/morningroutines/delete")
    public List<MorningRoutine> deleteMorningRoutine(@RequestBody MorningRoutine choresRoutine) throws SQLException
    {
        return morningRoutineService.deleteMorningRoutine(choresRoutine);
    }

    @PostMapping("/navi/morningroutines/swap")
    public List<MorningRoutine> swapRoutineOrder(@RequestBody MorningRoutine choresRoutine, @RequestParam String direction) throws SQLException
    {
        return morningRoutineService.swapOrderForMorningRoutine(choresRoutine, direction);
    }
}
