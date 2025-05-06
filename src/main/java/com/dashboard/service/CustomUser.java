package com.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dashboard.repository.UserRepo;

@Service
public class CustomUser implements UserDetailsService {
	
	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.dashboard.entity.User fetchedUser = repo.findByUsername(username)
			.orElseThrow(()-> new RuntimeException("user not found"));
		
		return new User(
				fetchedUser.getUsername(),
				fetchedUser.getPassword(),
				List.of(new SimpleGrantedAuthority("USER"))
				);
		
	}
	
	
	
	
}
