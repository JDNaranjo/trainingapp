package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
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
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User: "+id+" not found"));
    }

    public User createUser(User newUser){
        if (newUser != null){
            return userRepository.save(newUser);
        }else {
            throw new NotCreatedException("User can not be null");
        }
    }

    public User updateUser(Long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotUpdatedException("User "+ id +" was not found"));

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
        throw new NotDeletedException("User: "+id+" not found to be deleted");
    }
}