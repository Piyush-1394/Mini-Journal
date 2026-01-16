package com.projectwp.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.projectwp.journalApp.entity.JournalEntry;
import com.projectwp.journalApp.exception.ConflictException;
import com.projectwp.journalApp.exception.ResourceNotFoundException;
import com.projectwp.journalApp.repository.JournalEntryRepository;
import com.projectwp.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projectwp.journalApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public User saveUser(User user) {
        Optional<User> existingUser = userRepository.findUserByUserName(user.getUserName());
        if(existingUser.isEmpty()){userRepository.save(user);}
        else{throw new ConflictException("User already exists with username "+ user.getUserName());}
        return user;
    }

    @Transactional
    public User saveEntry(String userName, JournalEntry journalEntry) {
        User user = userRepository.findUserByUserName(userName).orElseThrow(
                ()-> new ResourceNotFoundException("User not found :("));
        journalEntry.setDate(LocalDateTime.now());
        user.getJournalEntries().add(journalEntryRepository.save(journalEntry));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

//    public Optional<User> findUserById(ObjectId id){ return userRepository.findById(id);}

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }

    @Transactional
    public void updateUserByUserName(String userName, User user){
        User userInDb = findUserByUserName(userName);

        // If the request contains a new username, check if it's taken (and not the same user)
        if (user.getUserName() != null && !user.getUserName().equals(userName)) {
            if (userRepository.findUserByUserName(user.getUserName()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            userInDb.setUserName(user.getUserName());
        }

        // Update fields
        if (user.getPassword() != null) {
            userInDb.setPassword(user.getPassword());
        }

        // Save the existing object with same _id (✔ update, not insert)
        userRepository.save(userInDb);
        System.out.println("User updated successfully..");
    }

}

//controller --> service --> repository
// controller calls service and service interacts with the repository
