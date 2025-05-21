package com.user.demo.service;
import com.user.demo.model.Users;
import com.user.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

    private UserRepository userRepository;
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usersService = new UsersServiceImpl(userRepository);
    }

        @Test
        void testGetUserById_UserExists() {

        Users mockUser = new Users(1L, "Alice", "alice@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        Users result = usersService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

        @Test
        void testGetUserById_UserNotFound() {


        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usersService.getUserById(2L);
        });

        assertEquals("User not found with ID: 2", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }

//-----------------

    @Test
    void testGetAllUsers() {
        List<Users> users = Arrays.asList(new Users(1L, "John", "john@example.com"));
        when(userRepository.findAll()).thenReturn(users);

        List<Users> result = usersService.getAllUsers();
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }
    @Test
    void testCreateUser() {
        Users user = new Users(1L, "Jane", "jane@example.com");
        when(userRepository.save(user)).thenReturn(user);

        Users result = usersService.createUser(user);
        assertEquals("Jane", result.getName());
    }
    @Test
    void testGetUserById_Success() {
        Users user = new Users(1L, "Ali", "ali@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Users result = usersService.getUserById(1L);
        assertEquals("Ali", result.getName());
    }
    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.getUserById(1L));
    }
    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        usersService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> usersService.deleteUser(1L));
    }

    @Test
    void testUpdateUser_Success() {
        Users existing = new Users(1L, "Old", "old@example.com");
        Users updated = new Users(1L, "New", "new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        usersService.updateUser(updated);

        assertEquals("New", existing.getName());
    }




}