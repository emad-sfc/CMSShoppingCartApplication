package com.example.emadpackage.models.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface pageRepository extends JpaRepository<page, Integer>{
	
	page findBySlug(String slug);
	
//	@Query("SELECT p from page p where p.id != :id and p.slug = :slug" )       //Same as line 13
//	page findBySlug(int id, String slug);
	
	page findBySlugAndIdNot(String slug, int id);
	
}