package com.quogle.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quogle.entities.Category;
import com.quogle.entities.Post;
import com.quogle.entities.User;
import com.quogle.exceptions.ResourceNotFoundException;
import com.quogle.payloads.PostDto;
import com.quogle.payloads.PostResponse;
import com.quogle.repositories.CategoryRepository;
import com.quogle.repositories.PostRepository;
import com.quogle.repositories.UserRepository;
import com.quogle.services.PostServices;

@Service
public class PostServicesImpl implements PostServices {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public PostDto creatingPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		Post post=this.modelMapper.map(postDto, Post.class);
		
		//Post post=new Post();
		
//		post.setPostContent(postDto.getPostContent());
//		post.setPostTitle(postDto.getPostTitle());
		
		post.setPostAddingDate(new Date());
		post.setPostImageName(postDto.getPostImageName());
		post.setPostCategory(category);
		post.setPostUser(user);
		
		this.postRepository.save(post);
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatingPost(PostDto postDto, Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id",postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImageName(postDto.getPostImageName());
		
		this.postRepository.save(post);
		
		return this.modelMapper.map(post,PostDto.class);
		
	}

	@Override
	public void deletingPost(Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id",postId));
		this.postRepository.delete(post);

	}

	@Override
	public PostDto getPostById(Integer postId) { 
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id",postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort=null;
		
		if(sortDir.equals("desc")) {
			sort=Sort.by(sortBy).descending();
		}else {
			sort=Sort.by(sortBy).ascending();
		}
		
		//import data.domain.pageable 
		Pageable p= PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> list= pagePost.getContent();
		
		List<PostDto> postDtoList=list.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setList(postDtoList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		
		postResponse.setLastPage(pagePost.isLast());
		
		postResponse.setFirstPage(pagePost.isFirst());
		
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		List<Post> list= this.postRepository.findBypostCategory(category);
		
		List<PostDto> postDtoList=list.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			
		return postDtoList;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		List<Post> list= this.postRepository.findBypostUser(user);
		List<PostDto> postDtoList=list.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	    
		List<Post> postList= this.postRepository.findByPostTitleContaining(keyword);
		
		List<PostDto> postDtoList=postList.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtoList;
	}
	
	

}
