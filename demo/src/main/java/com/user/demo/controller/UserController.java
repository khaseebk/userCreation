package com.user.demo.controller;

import com.user.demo.model.Users;
import com.user.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UsersService userService;

    // 🔽 Field Injection is not recommended
//    @Autowired
//    private UsersService userService;
    @Autowired // Constructor Injection is recommened For larger or production apps, prefer constructor injection for maintainability and testability.
    public UserController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> getUsers() {
        //return userService.getAllUsers();
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // HTTP 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        userService.createUser(user);
        //return "User created successfully";
        return ResponseEntity.status(201).body("User created successfully"); // HTTP 201
    }
}
