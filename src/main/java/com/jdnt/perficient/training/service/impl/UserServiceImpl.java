package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.repository.UserRepository;
import com.jdnt.perficient.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User newUser){
        if (newUser != null){
            return userRepository.save(newUser);
        }else {
            throw new UserNotCreatedException("User can not be null");
        }
    }

    public User updateUser(Long id, User newUser) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotUpdatedException(id));

            user.setEmail(newUser.getEmail());
            user.setLastName(newUser.getLastName());
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());
            user.setUsername(newUser.getUsername());

            return userRepository.save(user);

    }

    public String deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User deleted";
        }
        throw new UserNotDeletedException(id);
    }
}