package com.user.demo.service;

import com.user.demo.model.Users;
import com.user.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

        @Test
        void testGetUserById_UserExists() {
        // Arrange
        UserRepository mockRepository = Mockito.mock(UserRepository.class);
        UsersServiceImpl userService = new UsersServiceImpl(mockRepository);

        Users mockUser = new Users(1L, "Alice", "alice@example.com");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        Users result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
        verify(mockRepository, times(1)).findById(1L);
    }

        @Test
        void testGetUserById_UserNotFound() {
        // Arrange
        UserRepository mockRepository = Mockito.mock(UserRepository.class);
        UsersServiceImpl userService = new UsersServiceImpl(mockRepository);

        when(mockRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(2L);
        });

        assertEquals("User not found with id: 2", exception.getMessage());
        verify(mockRepository, times(1)).findById(2L);
    }
}