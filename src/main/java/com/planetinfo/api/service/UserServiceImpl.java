package com.planetinfo.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetinfo.api.entity.User;
import com.planetinfo.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> login(String name, String password) {
		return userRepository.login(name, password);
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void saveVote(String userName, Integer vote) {
		userRepository.saveVote(userName, vote);
	}
	
	@Override
	@Transactional
	public Optional<List<Integer>> getAllVotes() {
		return userRepository.getAllVotes();
	}

	@Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
