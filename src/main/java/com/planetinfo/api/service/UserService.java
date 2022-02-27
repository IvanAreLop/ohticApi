package com.planetinfo.api.service;

import java.util.List;
import java.util.Optional;

import com.planetinfo.api.entity.User;

public interface UserService {
	
	public Optional<User> login(String name, String password);
	
	public User saveUser(User user);
	
	public void saveVote(String userName, Integer vote);
	
	public Optional<List<Integer>> getAllVotes();
	
}
