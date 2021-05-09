package com.aritra.learning.jedis.practice.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aritra.learning.jedis.practice.dao.DeveloperRepository;
import com.aritra.learning.jedis.practice.model.Developer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeveloperService {
	
	@Autowired
	private DeveloperRepository developerRepo;
	
	public void saveDeveloper(Developer developer) throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String developerAsString = objMapper.writeValueAsString(developer);
		developerRepo.saveDeveloperAsString(developer.getId(), developerAsString);
	}
	
	public Developer getDeveloper(String key) throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String developerAsString = developerRepo.getDeveloperAsString(key);
		return objMapper.readValue(developerAsString, Developer.class);
	}
	
	public void addDeveloperToList (Developer developer) {
		developerRepo.addDeveloperToList(developer);
	}
	
	public List<Developer> getAllDevelopersFromList() {
		return developerRepo.getAllDevelopersFromList();
	}
	
	public Long getNoOfDevelopersFromList() {
		return developerRepo.getNoOfDevelopersFromList();
	}
	
	public void addDeveloperToSet (Developer... developers) {
		developerRepo.addDeveloperToSet(developers);
	}
	
	public Set<Developer> getAllDevelopersFromSet () {
		return developerRepo.getAllDevelopersFromSet();
	}
	
	public Boolean isMemberOfSet (Developer developer) {
		return developerRepo.isMemberOfSet(developer);
	}
	
	public void saveDeveloperInHash (Developer developer) {
		developerRepo.saveDeveloperInHash(developer);
	}
	
	public Developer getDeveloperFromHash (String id) {
		return developerRepo.getDeveloperFromHash(id);
	}
	
	public Map<String,Developer> getAllItemFromHash () {
		return developerRepo.getAllItemFromHash();
	}
	
	public Long deleteFromHash (String id) {
		return developerRepo.deleteFromHash(id);
	}
	
}
