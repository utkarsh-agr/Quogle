package com.quogle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quogle.entities.Category;
import com.quogle.entities.Post;
import com.quogle.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>  {
	
	List<Post> findBypostUser(User user);
	List<Post> findBypostCategory(Category category);
	
	List<Post> findByPostTitleContaining(String title);

}
