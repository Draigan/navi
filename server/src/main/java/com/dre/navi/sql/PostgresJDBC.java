package com.dre.navi.sql;

import com.dre.navi.httpwebserver.model.Chore;
import com.dre.navi.httpwebserver.model.MorningRoutine;
import com.dre.navi.httpwebserver.model.Task;
import com.dre.navi.httpwebserver.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PostgresJDBC
{

    private Connection connection;
    private static PostgresJDBC instance;

    public PostgresJDBC()
    {
    }

    public static PostgresJDBC getInstance()
    {
        if (instance == null)
        {
            instance = new PostgresJDBC();
        }
        return instance;
    }
    // JDBC URL, username, and password of PostgreSQL server

    public void startConnection() throws SQLException
    {
        String url = "jdbc:postgresql://postgres-container:5432/navi";
        String user = "postgres";
        String password = "dre123";
        try
        {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex)
        {
            throw new RuntimeException(ex);
        }

        connection = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection()
    {
        try
        {
            connection.close();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    public void createTablesIfNotExists() throws SQLException
    {
        // Create tables if they don't exist
        createTableIfNotExistsUsers();
        createTableIfNotExistsTasks();
        createTableIfNotExistsRoutines();
        createTableIfNotExistsChores();
    }
    private void createTableIfNotExistsUsers() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("create table if not exists users (user_id varchar(255), user_name varchar(255), required_routine int, required_chores int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createTableIfNotExistsTasks() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("create table if not exists tasks (task_id varchar(255), user_id varchar(255), name varchar(255), points int, index_order int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createTableIfNotExistsRoutines() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("create table if not exists morningroutines (routine_id varchar(255), user_id varchar(255), name varchar(255),  index_order int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createTableIfNotExistsChores() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("create table if not exists chores (chore_id varchar(255), user_id varchar(255), name varchar(255), day varchar(255), index_order int)");
        ps.executeUpdate();
        ps.close();
    }

    public List<User> selectAllFromUsers() throws SQLException
    {
        createTableIfNotExistsUsers();
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from users");

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next())
        {
            // Retrieve data from the result set
            String id = rs.getString("user_id");
            String userName = rs.getString("user_name");

            // Do something with the data
//                System.out.println("Id: " + id + ", Age: " + age + ", Name: " + name);
//            System.out.println("Id: " + id + ",  User Name: " + userName);
            users.add(new User(id, userName));
        }

        // Close resources
        rs.close();
        ps.close();
        return users;
    }

    public void dropUsers() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("drop table if exists users");
        ps.executeUpdate();
        ps.close();
    }

    public void addUser(String id, String userName) throws SQLException
    {
        createTableIfNotExistsUsers();
        PreparedStatement ps = connection.prepareStatement("insert into users (user_id, user_name) values (?, ?)");
        ps.setString(1, id);
        ps.setString(2, userName);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }


    public void deleteUser(String id) throws SQLException
    {
        createTableIfNotExistsUsers();
        System.out.println("Deleting user with id: " + id);
        PreparedStatement ps = connection.prepareStatement("delete from users where user_id = ?");
        ps.setString(1, id);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    //
    // Tasks
    //
    public void addTask(Task task) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("insert into tasks (user_id, task_id, name, points, index_order) values (?, ?, ?, ?, ?)");
        ps.setString(1, task.getUserId());
        ps.setString(2, task.getId());
        ps.setString(3, task.getName());
        ps.setInt(4, task.getPoints());
        ps.setInt(5, task.getIndex());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public List<Task> selectAllFromTasksForUser(String userId) throws SQLException
    {
        List<Task> tasks = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from tasks where user_id = ? order by index_order asc");
        ps.setString(1, userId);

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next())
        {
            // Retrieve data from the result set
            String user_id = rs.getString("user_id");
            String task_id = rs.getString("task_id");
            String name = rs.getString("name");
            int points = rs.getInt("points");
            int index_order = rs.getInt("index_order");

            tasks.add(new Task(name, points, user_id, index_order, task_id));
        }

        // Close resources
        rs.close();
        ps.close();
        return tasks;
    }

    public void updateTask(Task task) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("update tasks set name = ?, points = ?, index_order = ? where task_id = ? ");
        ps.setString(1, task.getName());
        ps.setInt(2, task.getPoints());
        ps.setInt(3, task.getIndex());
        ps.setString(4, task.getId());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void deleteTask(String taskId) throws SQLException
    {
        System.out.println("Deleting task with id: " + taskId);
        PreparedStatement ps = connection.prepareStatement("delete from tasks where task_id = ?");
        ps.setString(1, taskId);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void swapOrderForTasks(int indexOne, int indexTwo) throws SQLException
    {
        System.out.println("Current: " + indexOne + " Target: " + indexTwo);
        PreparedStatement ps = connection.prepareStatement(
                "update tasks set index_order = " +
                        "case " +
                        "when index_order = ? then ? " +
                        "when index_order = ? then ? " +
                        "end " +
                        "where index_order in (?,?)");
        ps.setInt(1, indexOne);
        ps.setInt(2, indexTwo);
        ps.setInt(3, indexTwo);
        ps.setInt(4, indexOne);
        ps.setInt(5, indexOne);
        ps.setInt(6, indexTwo);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    //
    // Morning Routines
    //
    public List<MorningRoutine> selectAllFromMorningRoutinesForUser(String userId) throws SQLException
    {
        List<MorningRoutine> choresRoutines = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from morningroutines where user_id = ? order by index_order asc");
        ps.setString(1, userId);

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next())
        {
            // Retrieve data from the result set
            String user_id = rs.getString("user_id");
            String morningRoutine_id = rs.getString("routine_id");
            String name = rs.getString("name");
            int index_order = rs.getInt("index_order");

            choresRoutines.add(new MorningRoutine(name, user_id, index_order, morningRoutine_id));
        }

        // Close resources
        rs.close();
        ps.close();
        return choresRoutines;
    }

    public void addMorningRoutine(MorningRoutine routine) throws SQLException
    {
        PreparedStatement ps =
                connection.prepareStatement("insert into morningroutines (user_id, routine_id, name,  index_order) values (?, ?, ?, ? )");
        ps.setString(1, routine.getUserId());
        ps.setString(2, routine.getId());
        ps.setString(3, routine.getName());
        ps.setInt(4, routine.getIndex());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void updateMorningRoutine(MorningRoutine choresRoutine) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("update morningroutines set name = ?,  index_order = ? where routine_id = ? ");
        ps.setString(1, choresRoutine.getName());
        ps.setInt(2, choresRoutine.getIndex());
        ps.setString(3, choresRoutine.getId());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void deleteMorningRoutine(String routineId) throws SQLException
    {
        System.out.println("Deleting morning routine with id: " + routineId);
        PreparedStatement ps = connection.prepareStatement("delete from morningroutines where routine_id = ?");
        ps.setString(1, routineId);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void swapOrderForMorningRoutines(int indexOne, int indexTwo) throws SQLException
    {
        System.out.println("Current: " + indexOne + " Target: " + indexTwo);
        PreparedStatement ps = connection.prepareStatement(
                "update morningroutines set index_order = " +
                        "case " +
                        "when index_order = ? then ? " +
                        "when index_order = ? then ? " +
                        "end " +
                        "where index_order in (?,?)");
        ps.setInt(1, indexOne);
        ps.setInt(2, indexTwo);
        ps.setInt(3, indexTwo);
        ps.setInt(4, indexOne);
        ps.setInt(5, indexOne);
        ps.setInt(6, indexTwo);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    //
    // Chores
    //
    public List<Chore> selectAllFromChoresForUser(String userId, String day) throws SQLException
    {
        List<Chore> chores = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from chores where user_id = ? and day = ? order by index_order asc");
        ps.setString(1, userId);
        ps.setString(2, day);

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next())
        {
            // Retrieve data from the result set
            String user_id = rs.getString("user_id");
            String morningRoutine_id = rs.getString("chore_id");
            String name = rs.getString("name");
            int index_order = rs.getInt("index_order");
            String day_routine = rs.getString("day");

            chores.add(new Chore(name, user_id, index_order, morningRoutine_id, day_routine));
        }

        // Close resources
        rs.close();
        ps.close();
        return chores;
    }

    public void addChore(Chore chore) throws SQLException
    {
        PreparedStatement ps =
                connection.prepareStatement("insert into chores (user_id, chore_id, name,  index_order, day) values (?, ?, ?, ?, ? )");
        ps.setString(1, chore.getUserId());
        ps.setString(2, chore.getId());
        ps.setString(3, chore.getName());
        ps.setInt(4, chore.getIndex());
        ps.setString(5, chore.getDay());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void updateChore(Chore choresRoutine) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("update chores set name = ?,  index_order = ? where chore_id = ? ");
        ps.setString(1, choresRoutine.getName());
        ps.setInt(2, choresRoutine.getIndex());
        ps.setString(3, choresRoutine.getId());

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void deleteChore(String choreId) throws SQLException
    {
        System.out.println("Deleting morning chore with id: " + choreId);
        PreparedStatement ps = connection.prepareStatement("delete from chores where chore_id = ?");
        ps.setString(1, choreId);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    public void swapOrderForChores(int indexOne, int indexTwo) throws SQLException
    {
        System.out.println("Current: " + indexOne + " Target: " + indexTwo);
        PreparedStatement ps = connection.prepareStatement(
                "update chores set index_order = " +
                        "case " +
                        "when index_order = ? then ? " +
                        "when index_order = ? then ? " +
                        "end " +
                        "where index_order in (?,?)");
        ps.setInt(1, indexOne);
        ps.setInt(2, indexTwo);
        ps.setInt(3, indexTwo);
        ps.setInt(4, indexOne);
        ps.setInt(5, indexOne);
        ps.setInt(6, indexTwo);

        // Execute INSERT
        ps.executeUpdate();

        // Close resources
        ps.close();
    }

    //
    // Update scores
    //
    public void setRequiredRoutine(int requiredPoints, String userId) throws SQLException {
        System.out.println("Set Required Routine Firing ssql");
        PreparedStatement ps = connection.prepareStatement("update users set required_points = ? where user_id = ?");
        ps.setInt(1, requiredPoints);
        ps.setString(2, userId);

        ps.executeUpdate();

        ps.close();
    }
}

