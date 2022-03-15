package com.example.emadpackage.models.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface adminRepository extends JpaRepository<admin, Integer>{
	
	admin findByUsername(String username);
}
