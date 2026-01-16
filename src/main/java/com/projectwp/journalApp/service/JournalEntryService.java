package com.projectwp.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.projectwp.journalApp.entity.User;
import com.projectwp.journalApp.exception.ResourceNotFoundException;
import com.projectwp.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projectwp.journalApp.entity.JournalEntry;
import com.projectwp.journalApp.repository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}

	@Transactional
	public void deleteById(ObjectId id, String userName) {
		JournalEntry entry = findById(id);
		User user = userService.findUserByUserName(userName);
		user.getJournalEntries().removeIf(x -> x.getId().equals(id));
		userRepository.save(user);
		if(entry != null) journalEntryRepository.deleteById(id);
	}
	
	public JournalEntry findById(ObjectId id) {
	   Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
	   if(journalEntry.isPresent()) {
		   return journalEntry.get();
	   }else {
		   throw new ResourceNotFoundException("journal entry not found with id " + id);
	   }
	}
	
	public JournalEntry updateJournalEntryById(ObjectId id, JournalEntry newEntry) {
		JournalEntry old = journalEntryRepository.findById(id).orElseThrow(
				() ->  new ResourceNotFoundException("journal not found with id " + id));
		old.setTitle(newEntry.getTitle() !=  null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
		old.setContent(newEntry.
				getContent()!= null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
		old.setDate(LocalDateTime.now());
		journalEntryRepository.save(old);
		return old;
	}
}

//controller --> service --> repository
// controller calls service and service interacts with the repository
