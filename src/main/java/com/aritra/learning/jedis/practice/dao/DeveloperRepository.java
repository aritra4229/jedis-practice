package com.aritra.learning.jedis.practice.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.aritra.learning.jedis.practice.constants.DeveloperConstants;
import com.aritra.learning.jedis.practice.model.Developer;

@Repository
public class DeveloperRepository {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	@Qualifier("listOperations")
	private ListOperations<String, Developer> listOperations;
	
	@Autowired
	@Qualifier("setOperations")
	private SetOperations<String, Developer> setOperations;
	
	@Autowired
	@Qualifier("hashOperations")
	private HashOperations<String, String, Developer> hashOperations;
	
	//************String************
	public void saveDeveloperAsString(String key, String developerStr) {
		redisTemplate.opsForValue().set(key, developerStr);
		redisTemplate.expire(key, 60, TimeUnit.SECONDS);
	}
	
	public String getDeveloperAsString(String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}
	
	//************List***************
	
	public void addDeveloperToList (Developer developer) {
		listOperations.leftPush(DeveloperConstants.DEV_LIST_KEY, developer);
	}
	
	public List<Developer> getAllDevelopersFromList() {
		return listOperations.range(DeveloperConstants.DEV_LIST_KEY, 0, -1);
	}
	
	public Long getNoOfDevelopersFromList() {
		return listOperations.size(DeveloperConstants.DEV_LIST_KEY);
	}
	
	//************Set***************
	public void addDeveloperToSet (Developer... developers) {
		setOperations.add(DeveloperConstants.DEV_SET_KEY, developers);
	}
	
	public Set<Developer> getAllDevelopersFromSet () {
		return setOperations.members(DeveloperConstants.DEV_SET_KEY);
	}
	
	public Boolean isMemberOfSet (Developer developer) {
		return setOperations.isMember(DeveloperConstants.DEV_SET_KEY, developer);
	}
	
	//************Hash***************
	public void saveDeveloperInHash (Developer developer) {
		hashOperations.put(DeveloperConstants.DEV_HASH_KEY, developer.getId(), developer);
	}
	
	public Developer getDeveloperFromHash (String id) {
		return hashOperations.get(DeveloperConstants.DEV_HASH_KEY, id);
	}
	
	public Map<String,Developer> getAllItemFromHash () {
		return hashOperations.entries(DeveloperConstants.DEV_HASH_KEY);
	}
	
	public Long deleteFromHash (String id) {
		return hashOperations.delete(DeveloperConstants.DEV_HASH_KEY, id);
	}
}
