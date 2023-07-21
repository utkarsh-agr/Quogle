package com.quogle.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="post_title", length=100)
	private String postTitle;
	
	@Column(name="post_content", length=10000)
	private String postContent;
	
	@Column(name="post_image_name")
	private String postImageName;
	
	@Column(name="posts_date")
	private Date postAddingDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User postUser;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category postCategory; 

	@OneToMany(mappedBy = "commentPost", cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<>();
}
