package com.dashboard.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dashboard.entity.User;
import com.dashboard.repository.UserRepo;

@Component
public class Userdao {
	@Autowired
	UserRepo repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	Logger logger=LoggerFactory.getLogger(Userdao.class);
	
	public User saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public List<User> list(){
		return repo.findAll();
	}
	
	public User getById(Integer id) {
		return repo.findById(id).orElseThrow(()-> new RuntimeException("user not foun with id"));
	}
	
	public User update(User user) {
		User fetchedUser = repo.findById(user.getUser_id()).orElseThrow(()-> new RuntimeException("user not foun with id"));
		fetchedUser.setUsername(user.getUsername());
	    fetchedUser.setPassword(user.getPassword());
	    fetchedUser.setEmail(user.getEmail());
	    fetchedUser.setDateOfJoin(user.getDateOfJoin());
	    fetchedUser.setSalary(user.getSalary());
	    fetchedUser.setRoles(user.getRoles()); // Update roles if needed

	    // Save the updated user back to the database
	    return repo.save(fetchedUser);
	}
	public void remove(Integer id) {
		System.out.println("id");
		logger.debug("remove method invoked with id "+id); 
		User fetchedUser = repo.findById(id).orElseThrow(()-> new RuntimeException("user not foun with id"));
		System.out.println(fetchedUser.getEmail());
		repo.delete(fetchedUser);
	}
}
