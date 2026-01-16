package com.projectwp.journalApp.controller;
import java.util.List;
import java.util.Optional;

import com.projectwp.journalApp.exception.ResourceNotFoundException;
import com.projectwp.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectwp.journalApp.entity.*;
import com.projectwp.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/{userName}")
	public User createEntry(@PathVariable String userName, @RequestBody JournalEntry myEntry) {
		return userService.saveEntry(userName, myEntry);
	}
	
	@GetMapping("/find/{id}")
	public JournalEntry findById(@PathVariable ObjectId id){
		return journalEntryService.findById(id);
	}
	
	@GetMapping("/all/{userName}")
	public List<JournalEntry> getAllJournalEntriesOfUser(@PathVariable String userName){

		User user = userService.findUserByUserName(userName);
		List<JournalEntry> allJournal = user.getJournalEntries();
		return allJournal;
	}
	
	@DeleteMapping("/delete/{userName}/{id}")
	public String deleteEntry(@PathVariable ObjectId id, @PathVariable String userName) {
		journalEntryService.deleteById(id, userName);
		return "Deleted journal entry with id : "+id;
	}
	
	@PutMapping("/update/{userName}/{id}")
	public JournalEntry updateEntryById(
			@PathVariable ObjectId id,
			@PathVariable String userName,
			@RequestBody JournalEntry newEntry
	) {
		JournalEntry updatedEntry = journalEntryService.updateJournalEntryById(id, newEntry);
		return updatedEntry;
	}
	
}
