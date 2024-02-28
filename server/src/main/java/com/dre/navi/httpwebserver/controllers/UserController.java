package com.dre.navi.httpwebserver.controllers;

import com.dre.navi.httpwebserver.model.User;
import com.dre.navi.httpwebserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String hello()
    {
        return "Hello";
    }

    @GetMapping("/navi/users")
    public String hello2()
    {
        return "Hello from navi";
    }

    @PostMapping("/users/new")
    public String addUser(@RequestBody User user) throws SQLException
    {
        userService.addUser(user.getUserName());
        return "Added new user: " + user.getUserName();
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() throws SQLException
    {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestBody User user) throws SQLException
    {
        userService.deleteUser(user.getId());
        System.out.println(user.getId() + "This is the id?");
        return "Deleted user: " + user.getUserName();
    }
}
