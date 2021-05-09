package com.aritra.learning.jedis.practice.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aritra.learning.jedis.practice.model.Developer;
import com.aritra.learning.jedis.practice.service.DeveloperService;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
	
	@Autowired
	private DeveloperService developerService;
	
	@PostMapping()
	public Boolean saveDeveloper (@RequestBody Developer developer) throws Exception {
		developerService.saveDeveloper(developer);
		return Boolean.TRUE;
	}
	
	@GetMapping("/{id}")
	public Developer getDeveloper (@PathVariable("id") String id) throws Exception {
		return developerService.getDeveloper(id);
	}
	
	@PostMapping("/list/add")
	public Boolean addDeveloper (@RequestBody Developer developer) throws Exception {
		developerService.addDeveloperToList(developer);
		return Boolean.TRUE;
	}
	
	@GetMapping("/list/fetch-all")
	public List<Developer> fetchAllDeveloper () throws Exception {
		return developerService.getAllDevelopersFromList();
	}
	
	@GetMapping("/list/size")
	public Long getSize () throws Exception {
		return developerService.getNoOfDevelopersFromList();
	}
	
	@PostMapping("/set/add")
	public Boolean addDeveloperToSet (@RequestBody Developer... developers) throws Exception {
		developerService.addDeveloperToSet(developers);
		return Boolean.TRUE;
	}
	
	@GetMapping("/set/fetch-all")
	public Set<Developer> fetchAllDeveloperFromSet () throws Exception {
		return developerService.getAllDevelopersFromSet();
	}
	
	@PostMapping("/set/check-membership")
	public Boolean isMemberOfSet (@RequestBody Developer developer) throws Exception {
		return developerService.isMemberOfSet(developer);
	}
	
	@PostMapping("/hash/save")
	public Boolean saveDeveloperToHash (@RequestBody Developer developer) throws Exception {
		developerService.saveDeveloperInHash(developer);
		return Boolean.TRUE;
	}
	
	@GetMapping("hash/retrieve/{id}")
	public Developer getDeveloperFromHash (@PathVariable("id") String id) throws Exception {
		return developerService.getDeveloperFromHash(id);
	}
	
	@GetMapping("hash/delete/{id}")
	public Boolean deleteDeveloperFromHash (@PathVariable("id") String id) throws Exception {
		return developerService.deleteFromHash(id)>0L;
	}
	
	@GetMapping("hash/retrieve-all")
	public Map<String, Developer> getAllDevelopersFromHash() {
		return developerService.getAllItemFromHash();
	}
	
}
