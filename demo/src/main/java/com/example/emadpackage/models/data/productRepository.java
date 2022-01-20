package com.example.emadpackage.models.data;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepository extends JpaRepository<product, Integer>{

	product findBySlug(String slug);

	product findBySlugAndIdNot(String slug, int id);
	
	Page<product> findAll(Pageable pagable);     //Here page is from org.springframework.data.domain.Page; and not page entity that we created.

}
