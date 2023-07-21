package com.quogle.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer categoryId;
	
	@Column(name="categories_title", nullable = false)
	private String categoryTitle;
	
	@Column(name="categories_description", length=1000)
	private String categoryDescription;
	
	@OneToMany(mappedBy = "postCategory", cascade = CascadeType.ALL)
	private List<Post> categoryPosts=new ArrayList<>();
	

}
