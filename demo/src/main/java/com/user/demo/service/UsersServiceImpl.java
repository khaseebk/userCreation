package com.user.demo.service;
import com.user.demo.model.Users;
import com.user.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

    @Service
    public class UsersServiceImpl implements UsersService {

        private final UserRepository usersRepository;

        public UsersServiceImpl(UserRepository userRepository) {
            this.usersRepository = userRepository;
        }

        @Override
        public List<Users> getAllUsers() {
            return usersRepository.findAll();
        }

        @Override
        public Users createUser(Users user) {
            return usersRepository.save(user);
        }


        @Override
        public Users getUserById(Long id) {
            return usersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        }

    }
