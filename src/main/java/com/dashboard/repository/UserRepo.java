package com.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.dashboard.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	
//	@Query("SELECT U FROM USER.U WHERE U.username:username AND U.password:password")
//	User login(String username, String password);
	
	Optional<User> findByUsernameAndPassword(String username,String password);
	Optional<User> findByEmailAndPassword(String email,String password);
	Optional<User> findByUsername(String username);
}
