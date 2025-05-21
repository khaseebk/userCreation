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
 //   private UsersService userService;
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

    /* from postman or curl command
    curl -X POST http://localhost:8080/api/v1/users \
      -H "Content-Type: application/json" \
      -d '{"id":"101","name":"Alice","email":"alice@example.com"}
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        userService.createUser(user);
        //return "User created successfully";
        return ResponseEntity.status(201).body("User created successfully"); // HTTP 201
    }


/*
 Post is not allowed from Browser URL. We allways used Postman or Swagger for Post
  curl -X POST http://localhost:8080/api/v1/users/create \
     -d "id=102" -d "name=Ali" -d "email=ali@example.com"
    */
    @PostMapping("/create")
    public ResponseEntity<String> createUserFromParamsNotFromURL(@RequestParam Long id,
                                                       @RequestParam String name,
                                                       @RequestParam String email) {

        Users user = new Users(id, name, email);
        userService.createUser(user);
        return ResponseEntity.status(201).body("User created successfully1"); // HTTP 201

    }

    /*
      /* from postman or curl command
    curl -X POST http://localhost:8080/api/v1/users/createUsingRequestBody \
      -H "Content-Type: application/json" \
      -d '{"id":"103","name":"Bob","email":"bob@example.com"}'
     */
    @PostMapping("/createUsingRequestBody")
    public ResponseEntity<String> createUserFromParamsNotFromURL(@RequestBody Users user) {
        userService.createUser(user);
        return ResponseEntity.status(201).body("User created successfully by Post using Json createUsingRequestBody"); // HTTP 201

    }

  // from browser  http://localhost:8080/api/v1/users/createFromURLUsingGetMapping?id=104&name=Imo&email=imo@example.com
    // or using curl
    // curl -X GET http://localhost:8080/api/v1/users/createFromURLUsingGetMapping?id=105&name=don&email=don@example.com
    @GetMapping("/createFromURLUsingGetMapping") // ✅ Use @GetMapping for browser testing
    public ResponseEntity<String> createUserFromParams(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String email) {

        // Simulate saving user
        System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
        Users user = new Users(id, name, email);
        userService.createUser(user);
        return ResponseEntity.ok("User created: " + name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users have been deleted");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody Users updatedUser) {
        userService.updateUser(updatedUser);
        return ResponseEntity.ok("User updated successfully");
    }

}
