package com.quogle.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quogle.configs.AppConstants;
import com.quogle.payloads.ApiResponse;
import com.quogle.payloads.PostDto;
import com.quogle.payloads.PostResponse;
import com.quogle.services.FileService;
import com.quogle.services.PostServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/apis")
public class PostController {

	@Autowired
	PostServices postServices;
	
	@Value("${project.image}")
	private String path;
	
	@Autowired
	private FileService fileService;

	// create
	@PostMapping("/create_post/user_id/{userId}/category_id/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {
		PostDto returnedpostDto = this.postServices.creatingPost(postDto, userId, categoryId);

		return new ResponseEntity<>(returnedpostDto, HttpStatus.ACCEPTED);

	}

	// update
	@PutMapping("/update_post/post_id/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
		PostDto returnedPostDto = this.postServices.updatingPost(postDto, postId);
		return new ResponseEntity<>(returnedPostDto, HttpStatus.ACCEPTED);
	}

	// delete
	@DeleteMapping("/delete_post/post_id/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		this.postServices.deletingPost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully", true), HttpStatus.ACCEPTED);
	}

	// getPostById
	@GetMapping("/post/post_id/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
		PostDto postDto = this.postServices.getPostById(postId);

		return ResponseEntity.of(Optional.of(postDto));

	}

	// getAllPosts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value= "sortBy", defaultValue= AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value= "sortDir", defaultValue= AppConstants.SORT_DIR, required=false) String sortDir) {
		PostResponse postResponse= this.postServices.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}

	// getAllPostsByUser
	@GetMapping("/posts/user_id/{userId}")
	public ResponseEntity<List<PostDto>> getPostsOfUser(@PathVariable("userId") Integer userId) {
		List<PostDto> list = this.postServices.getAllPostsByUser(userId);
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(list));
	}

	// getAllPostOfCategory
	@GetMapping("/posts/category_id/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsOfCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> list = this.postServices.getAllPostsByCategory(categoryId);
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(list));
	}
	
	//search with keyword
	@GetMapping("/posts/search")
	public ResponseEntity<List<PostDto>> searchPost(@RequestParam(value="title", required=false) String keyword){
		List<PostDto> list=this.postServices.searchPosts(keyword);
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return new ResponseEntity<List<PostDto>>(list,HttpStatus.OK);
	}
	
	@PostMapping("/posts/upload/image/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable("postId") Integer postId) throws IOException{
		
		PostDto postDto=this.postServices.getPostById(postId);
		
		String filename=this.fileService.uploadImage(path, multipartFile);
		
		postDto.setPostImageName(filename);
		
		PostDto postDto2=this.postServices.updatingPost(postDto, postId);
		
		return new ResponseEntity<PostDto>(postDto2,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		
		InputStream is=this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(is, response.getOutputStream());
		
	}

}
