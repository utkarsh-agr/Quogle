package com.quogle.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quogle.entities.User;
import com.quogle.exceptions.ApiException;
//import com.quogle.exceptions.ApiException;
import com.quogle.payloads.JwtAuthRequest;
import com.quogle.payloads.JwtAuthResponse;
import com.quogle.payloads.UserDto;
import com.quogle.repositories.UserRepository;
import com.quogle.security.JwtTokenHelper;
import com.quogle.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request ) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
		
		
		String token=this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		response.setUserDto(this.modelMapper.map((User)userDetails, UserDto.class));
		
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}
	
	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			
		} catch (BadCredentialsException e) {
			System.out.println("invalid cedentials");
			throw new ApiException("Enter Valid username or password!!");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		
		UserDto registeredUser = this.userServices.registerUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/current_user")
	public ResponseEntity<UserDto> getUser(Principal principal){
		User user=this.userRepository.findByUserEmail(principal.getName()).get();
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	
	
	
	
	

}
