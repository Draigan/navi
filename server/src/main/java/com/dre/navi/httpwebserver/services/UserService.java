package com.dre.navi.httpwebserver.services;

import com.dre.navi.httpwebserver.model.User;
import com.dre.navi.sql.PostgresJDBC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService
{
    PostgresJDBC db = PostgresJDBC.getInstance();

    public List<User> getAllUsers() throws SQLException
    {
        return db.selectAllFromUsers();
    }
    public void addUser(String userName) throws SQLException
    {
        String id = UUID.randomUUID().toString();
        db.addUser(id,userName.toUpperCase());
    }
    public void deleteUser(String id) throws SQLException
    {
        db.deleteUser(id);
    }
}
