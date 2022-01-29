package com.example.emadpackage.models.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepository extends JpaRepository<category, Integer>{
	
	category findByName(String name);
	
	List<category> findAllByOrderBySortingAsc();

	category findBySlug(String slug);	

	

}
