
package com.user.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.demo.model.Users;
import com.user.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
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
}