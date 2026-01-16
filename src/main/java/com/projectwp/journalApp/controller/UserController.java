package com.projectwp.journalApp.controller;
import java.util.List;
import java.util.Optional;

import com.projectwp.journalApp.exception.ResourceNotFoundException;
import com.projectwp.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projectwp.journalApp.entity.*;
import com.projectwp.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @GetMapping("/find/{userName}")
    public User findUserByUserName(@PathVariable String userName){
         return userService.findUserByUserName(userName);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable ObjectId id) {
        userService.deleteUserById(id);
        return "Deleted user with id : "+id;
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName,@RequestBody User user){
        userService.updateUserByUserName(userName, user);
        return new ResponseEntity<>("User updated successfully..!! ", HttpStatus.OK);
    }
}
