package com.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.dto.UserLogin;
import com.dashboard.entity.User;
import com.dashboard.service.UserService;

import jakarta.persistence.Entity;

@RestController
@RequestMapping("/app")
public class UserController {
	
	@Autowired
	UserService service;
	
	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User user){
		User saveUser = service.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> list(){
		List<User> list = service.list();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<User> getById(@PathVariable Integer id){
		User user = service.getById(id);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> update (@RequestBody User user){
		User update = service.update(user);
		return ResponseEntity.ok(update);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> remove(@PathVariable Integer id){
		User user = service.getById(id);
		service.remove(user.getUser_id());
		System.out.println("user name :"+user.getUsername());
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/login2")
	public ResponseEntity<User> login2(@RequestBody UserLogin login){
		User login2 = service.login2(login);
		return ResponseEntity.ok(login2);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserLogin login){
		User login2 = service.login(login);
		return ResponseEntity.ok(login2);
	}

	//this end point shows current logged profile name
	@GetMapping("/profile")
	public ResponseEntity<String> profile(Authentication authentication){
		String name = authentication.getName();
		return ResponseEntity.ok("login user :"+name);
	}
}
