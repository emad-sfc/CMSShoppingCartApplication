package com.example.emadpackage.models.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String slug;

	private String description;

	private String image;

	private String price;

	@Column(name = "category_id")			//mapping category_id(of database) with categoryId. 
	private String categoryId;
	
	@CreationTimestamp						//This annotation automatically records date of creation
	@Column(name = "created_id")
	private LocalDateTime created_at;

	@UpdateTimestamp						//This annotation automatically records date updated.
	@Column(name = "updated_id")
	private LocalDateTime updated_at;

}
