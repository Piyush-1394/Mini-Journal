package com.projectwp.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.projectwp.journalApp.entity.JournalEntry;
import com.projectwp.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	public void saveEntry(JournalEntry journalEntry) {
		journalEntry.setDate(LocalDateTime.now());
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	public void deleteById(ObjectId id) {
		journalEntryRepository.deleteById(id);
	}
	
	public ResponseEntity<JournalEntry> findById(ObjectId id) {
	   Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
	   if(journalEntry.isPresent()) {
		   return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
	   }
	   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public JournalEntry updateJournalEntryById(ObjectId id, JournalEntry newEntry) {
		JournalEntry old = journalEntryRepository.findById(id).orElse(null);
		if(old != null) {
			old.setTitle(old.getTitle() !=  null && old.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
			old.setContent(old.getContent()!= null && old.getContent().equals("") ? newEntry.getContent() : old.getContent());
		}
		journalEntryRepository.save(old);
		return old;
	}
}

//controller --> service --> repository
// controller calls service and service interacts with the repository
