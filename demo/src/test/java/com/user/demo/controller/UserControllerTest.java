
package com.user.demo.controller;
import com.user.demo.security.JwtFilter;
import com.user.demo.util.JwtUtil;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.demo.model.Users;
import com.user.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) // ✅ disables Spring Security for test
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // ✅ Still safe to use in Spring Boot 3.4.5
    private UsersService usersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUserById_ReturnsUser() throws Exception {
        Users user = new Users(1L, "Alice", "alice@example.com");
        when(usersService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void testCreateUser_Returns201Created() throws Exception {
        Users user = new Users(2L, "Bob", "bob@example.com");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<Users> users = Arrays.asList(
                new Users(1L, "John", "john@example.com"),
                new Users(2L, "Jane", "jane@example.com")
        );
        when(usersService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testCreateUser() throws Exception {
        Users user = new Users(1L, "John", "john@example.com");
        when(usersService.createUser(Mockito.any(Users.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    public void testGetUserById() throws Exception {
        Users user = new Users(1L, "John", "john@example.com");
        when(usersService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Users user = new Users(1L, "Updated", "updated@example.com");

        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));
    }


}