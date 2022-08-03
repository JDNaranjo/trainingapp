package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        return new ResponseEntity<User>(userService.createUser(newUser), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser){
        return new ResponseEntity<User>(userService.updateUser(id, newUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
    }

}