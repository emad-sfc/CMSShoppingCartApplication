package com.example.emadpackage.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="pages")
@Data
public class page {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min=2, message = "Title must be 2 characters Long")
	private String title;
	
	private String slug;
	
	@Size(min=5, message = "Content must be 5 characters Long")
	private String content;
	
	private int sorting;

}
