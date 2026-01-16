package com.projectwp.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.projectwp.journalApp.entity.User;

import com.projectwp.journalApp.entity.JournalEntry;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId>{
    Optional<User> findUserByUserName(String userName);
}
