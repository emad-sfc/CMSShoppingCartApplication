package com.example.emadpackage.models.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepository extends JpaRepository<product, Integer>{

	product findBySlug(String slug);

	product findBySlugAndIdNot(String slug, int id);

}
