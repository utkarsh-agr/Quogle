package com.quogle.services;

import java.util.List;

import com.quogle.payloads.PostDto;
import com.quogle.payloads.PostResponse;

public interface PostServices {
	
	
	//create
	PostDto creatingPost(PostDto postDto,Integer userId, Integer categoryId);
	
	//update
	PostDto updatingPost(PostDto postDto, Integer postId);
	
	//delete
	void deletingPost(Integer postId);
	
	//getPostById
	PostDto getPostById(Integer postId);
	
	//getAllPosts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//getAllPostsByCategory
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	//getAllPostsByUser
	List<PostDto> getAllPostsByUser(Integer userId);
	
	//search posts
	
	List<PostDto> searchPosts(String keyword);
	
	

}
