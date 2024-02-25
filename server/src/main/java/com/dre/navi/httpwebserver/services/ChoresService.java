package com.dre.navi.httpwebserver.services;

import com.dre.navi.httpwebserver.model.Chore;
import com.dre.navi.sql.PostgresJDBC;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class ChoresService
{
    PostgresJDBC db = PostgresJDBC.getInstance();

    public List<Chore> getAllChoresForUser(String userId, String day) throws SQLException
    {
        return db.selectAllFromChoresForUser(userId, day);
    }

    public List<Chore> addChore(Chore choresRoutine) throws SQLException
    {
        List<Chore> choresRoutines = db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
        int size = choresRoutines.size();
        choresRoutine.setIndex(choresRoutines.size());
        db.addChore(choresRoutine);
        System.out.println(choresRoutine.getDay());
        return db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
    }

    public List<Chore> updateChore(Chore choresRoutine) throws SQLException
    {
        db.updateChore(choresRoutine);
        return db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
    }

    public List<Chore> deleteChore(Chore choresRoutine) throws SQLException
    {
        db.deleteChore(choresRoutine.getId());
        System.out.println("Morning ROutine delte");
        return db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
    }

    public List<Chore> swapOrderForChore(Chore choresRoutine, String direction) throws SQLException
    {
        // Get all tasks for the user. This list is in the order we want it just has gaps in index
        List<Chore> choresRoutines = db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
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
            db.swapOrderForChores(indexOne, indexTwo);
            return db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
        }

        if (Objects.equals(direction, "down"))
        {
            if (currentTaskIndex == choresRoutines.size() - 1)
                return choresRoutines; // Just to check if we are on the edge of the list
            // Get the first index
            int indexOne = choresRoutine.getIndex();
            // Get the second index
            int indexTwo = choresRoutines.get(currentTaskIndex + 1).getIndex();
            db.swapOrderForChores(indexOne, indexTwo);
            return db.selectAllFromChoresForUser(choresRoutine.getUserId(), choresRoutine.getDay());
        }


        return choresRoutines;
    }
}
