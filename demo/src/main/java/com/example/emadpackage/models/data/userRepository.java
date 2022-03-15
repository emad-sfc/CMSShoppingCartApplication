package com.example.emadpackage.models.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Integer>{
	
	user findByUsername(String username);	
}
