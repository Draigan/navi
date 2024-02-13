package com.dre.navi.httpwebserver.services;

import com.dre.navi.httpwebserver.model.MorningRoutine;
import com.dre.navi.sql.PostgresJDBC;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class ChoresService
{
    PostgresJDBC db = PostgresJDBC.getInstance();

    public List<MorningRoutine> getAllMorningRoutinesForUser(String userId) throws SQLException
    {
        return db.selectAllFromMorningRoutinesForUser(userId);
    }

    public List<MorningRoutine> addMorningRoutine(MorningRoutine choresRoutine) throws SQLException
    {
        List<MorningRoutine> choresRoutines = db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
        int size = choresRoutines.size();
        choresRoutine.setIndex(choresRoutines.size());
        db.addMorningRoutine(choresRoutine);
        return db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
    }

    public List<MorningRoutine> updateMorningRoutine(MorningRoutine choresRoutine) throws SQLException
    {
        db.updateMorningRoutine(choresRoutine);
        return db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
    }

    public List<MorningRoutine> deleteMorningRoutine(MorningRoutine choresRoutine) throws SQLException
    {
        db.deleteMorningRoutine(choresRoutine.getId());
        System.out.println("Morning ROutine delte");
        return db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
    }

    public List<MorningRoutine> swapOrderForMorningRoutine(MorningRoutine choresRoutine, String direction) throws SQLException
    {
        // Get all tasks for the user. This list is in the order we want it just has gaps in index
        List<MorningRoutine> choresRoutines = db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
        //  Find the index of the current tasks index
        int currentTaskIndex = -1;
        for (int i = 0; i < choresRoutines.size(); i++)
        {
            if (choresRoutines.get(i).getId().equals(choresRoutine.getId()))
            {
                currentTaskIndex = i;
                break;
            }
        }
        // Guard clause
        if (currentTaskIndex == -1) return choresRoutines;

        // In the case of direction up
        if (Objects.equals(direction, "up"))
        {
            if (currentTaskIndex == 0) return choresRoutines; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = choresRoutine.getIndex();
            // Get the second index
            int indexTwo = choresRoutines.get(currentTaskIndex - 1).getIndex();
            db.swapOrderForMorningRoutines(indexOne, indexTwo);
            return db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
        }

        if (Objects.equals(direction, "down"))
        {
            if (currentTaskIndex == choresRoutines.size() - 1)
                return choresRoutines; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = choresRoutine.getIndex();
            // Get the second index
            int indexTwo = choresRoutines.get(currentTaskIndex + 1).getIndex();
            db.swapOrderForMorningRoutines(indexOne, indexTwo);
            return db.selectAllFromMorningRoutinesForUser(choresRoutine.getUserId());
        }


        return choresRoutines;
    }
}
