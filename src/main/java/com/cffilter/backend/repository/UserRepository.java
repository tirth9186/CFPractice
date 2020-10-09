package com.cffilter.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cffilter.backend.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	Boolean existsUserByUsername(String username);
	
	Boolean existsUserByEmail(String email);
	
	Boolean existsUserByHandle(String handle);
	
}
