package com.dre.navi.sql;

import com.dre.navi.httpwebserver.model.Task;
import com.dre.navi.httpwebserver.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
        String url = "jdbc:postgresql://localhost:5432/navi";
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

    public List<User> selectAllFromUsers() throws SQLException
    {
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

    public void addUser(String id, String userName) throws SQLException
    {
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
        PreparedStatement ps = connection.prepareStatement(
                "update tasks set index_order = " +
                        "case " +
                        "when index_order = ? then ? " +
                        "when index_order = ? then ? " +
                        "end " +
                        "where index_order in (?,?)");
        ps.setInt(1, indexOne);
        ps.setInt(2, indexTwo);
        ps.setInt(3,indexTwo);
        ps.setInt(4, indexOne);
        ps.setInt(5, indexOne);
        ps.setInt(6, indexTwo);

        // Execute INSERT
        ps.executeUpdate();

//        update tasks set index_order =
//            case when index_order = 2 then 1
//            when index_order = 1 then 2
//            end
//                    where index_order in (2,1) ;

        // Close resources
        ps.close();
    }

}
