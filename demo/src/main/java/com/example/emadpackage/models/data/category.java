package com.example.emadpackage.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="categories")
@Data
public class category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min=2, message="Name must contain atleast 2 characters.")
	private String name;
	
	
	private String slug;
	
	private int sorting;

}
