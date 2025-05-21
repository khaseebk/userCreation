package com.user.demo.service;
import com.user.demo.model.Users;
import com.user.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        }

        @Override
        public void deleteUser(Long id) {
            if (!usersRepository.existsById(id)) {
                throw new EntityNotFoundException("User not found with ID: " + id);
            }
            usersRepository.deleteById(id);
        }

        @Override
        public void deleteAllUsers() {
            usersRepository.deleteAll();
        }

        @Override
        public void updateUser(Users updatedUser) {
            Users existingUser = usersRepository.findById(updatedUser.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + updatedUser.getId()));

            // Update fields as needed
            existingUser.setName(updatedUser.getName());

            usersRepository.save(existingUser);
        }


    }
