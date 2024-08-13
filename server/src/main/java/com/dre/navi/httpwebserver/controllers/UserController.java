package com.dre.navi.httpwebserver.controllers;

import com.dre.navi.httpwebserver.model.User;
import com.dre.navi.httpwebserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/navi/users")
    public String hello()
    {
        return "Hello";
    }


    @PostMapping("/navi/users/new")
    public String addUser(@RequestBody User user) throws SQLException
    {
        userService.addUser(user.getUserName());
        return user.getUserName() + "Added new user: ";
    }

    @GetMapping("/navi/users/all")
    public ResponseEntity<List<User>> getAllUsers() throws SQLException
    {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    @PostMapping("/navi/users/delete")
    public String deleteUser(@RequestBody User user) throws SQLException
    {
        userService.deleteUser(user.getId());
        System.out.println(user.getId() + "This is the id?");
        return "Deleted user: " + user.getUserName();
    }

    @PostMapping("/navi/users/updatepoints")
    public void updateRequiredPoints(@RequestParam("id") String id , @RequestParam("points") int points ) throws SQLException
    {
        System.out.println("Updating points for " + id + points );
        userService.updateRequiredPoints(points, id);
    }
}
