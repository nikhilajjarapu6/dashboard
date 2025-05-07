package com.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.dao.Userdao;
import com.dashboard.dto.PasswordChangeRequest;
import com.dashboard.dto.UserLogin;
import com.dashboard.entity.User;
import com.dashboard.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	Userdao userdao;
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	public User saveUser(User user) {
		return userdao.saveUser(user);
	}
	
	public List<User> list(){
		return userdao.list();
	}
	
	public User getById(Integer id) {
		return userdao.getById(id);
	}
	
	public User update(User user) {
	    return userdao.update(user);
	}
	public void remove(Integer id) {
		userdao.remove(id);
	}
	
	public User login2(UserLogin login) {
	    return repo.findByEmailAndPassword(login.getUsername(), login.getPassword())
	        .or(() -> repo.findByUsernameAndPassword(login.getUsername(), login.getPassword()))
	        .orElseThrow(() -> new RuntimeException("Invalid username/email or password"));
	}
	
	public User login(UserLogin login) {
		User user = repo.findByUsername(login.getUsername())
			.orElseThrow(()->new RuntimeException("failed to fetch user"));
		
		if (!encoder.matches(login.getPassword(), user.getPassword())) {
		    throw new RuntimeException("Invalid credentials"); 
		}

			
		return user;	
		
	}
	
	public User passwordUpdate(Authentication authentication, PasswordChangeRequest request) {
		String name = authentication.getName();
		System.out.println("reqyest pass "+request.getNewPass());
		User user = repo.findByUsername(name)
			.orElseThrow(()->new RuntimeException("failed to fetch user"));
		
		System.out.println(user.getPassword());
		if(!encoder.matches(request.getOldPass(),user.getPassword())) {
			throw new IllegalArgumentException("Old password is incorrect");
		}
		
		String newPass = encoder.encode(request.getNewPass());
		user.setPassword(newPass);
		return repo.save(user);
	}
	
	

	
	
}
