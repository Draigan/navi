package com.dre.navi.sql;

import com.dre.navi.httpwebserver.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PostgresJDBC {

    private Connection connection;
    private static PostgresJDBC instance;

    public PostgresJDBC()
    {
    }

    public static PostgresJDBC getInstance(){
        if (instance == null) {
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
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        connection = DriverManager.getConnection(url, user, password);
    }
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void selectAllFromTasks() throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("select * from tasks");
//            ps.setString(1, "Hayward");
//            ps.setInt(1, 1);

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next()) {
            // Retrieve data from the result set
            String id = rs.getString("user_id");
            int points = rs.getInt("points");
            String taskName = rs.getString("task_name");

            // Do something with the data
//                System.out.println("Id: " + id + ", Age: " + age + ", Name: " + name);
            System.out.println("Id: " + id + ", Points: " + points + ", Task Name: " + taskName);
        }

        // Close resources
        rs.close();
        ps.close();
    }
    public List<User> selectAllFromUsers() throws SQLException
    {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from users");

        // Execute a SELECT query
        ResultSet rs = ps.executeQuery();

        // Process the result set
        while (rs.next()) {
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


}
