package com.user.demo.service;
import com.user.demo.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();
    Users createUser(Users user);
    Users getUserById(Long id);
}