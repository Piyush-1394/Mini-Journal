package com.projectwp.journalApp.controller;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@PostMapping
	public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {  
		journalEntryService.saveEntry(myEntry);
		return myEntry;
	}
	
	@GetMapping("/find/{id}")
	public void findById(@PathVariable ObjectId id){
		journalEntryService.findById(id);
	}
	
	@GetMapping
	public List<JournalEntry> getAll(){
		return journalEntryService.getAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteEntry(@PathVariable ObjectId id) {
		journalEntryService.deleteById(id);
		return "Deleted journal entry with id : "+id;
	}
	
	@PutMapping("/update/{id}")
	public JournalEntry updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
		JournalEntry updatedEntry = journalEntryService.updateJournalEntryById(id, newEntry);
		return updatedEntry;
	}
	
}
