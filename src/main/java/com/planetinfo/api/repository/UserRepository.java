package com.planetinfo.api.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planetinfo.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2")
	Optional<User> login(String name, String password);
	
	@Query("SELECT u.vote FROM User u")
	Optional<List<Integer>> getAllVotes();
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET vote=?2 WHERE username=?1")
	void saveVote(String userName, Integer vote);
	
	User findByUsername(String username);

}
