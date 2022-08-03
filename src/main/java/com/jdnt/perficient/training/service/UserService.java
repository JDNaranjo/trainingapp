package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUsers();
    public User getUserById(Long id);
    public User createUser(User newUser);
    public User updateUser(Long id, User newUser);
    public String deleteUser(Long id);
}
