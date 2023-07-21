package com.quogle.services.impl;

//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quogle.configs.AppConstants;
import com.quogle.entities.Role;
import com.quogle.entities.User;
import com.quogle.exceptions.ResourceNotFoundException;
import com.quogle.payloads.UserDto;
import com.quogle.repositories.UserRepository;
import com.quogle.services.RoleServices;
import com.quogle.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	public RoleServices roleservices;

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		
		user.setUserPassword(this.passwordEncoder.encode(user.getUserPassword()));
		
		Role normalRole=this.roleservices.getRole(AppConstants.NORMAL_ROLE_ID);
		
		user.getUserRoles().add(normalRole);
		
		this.userRepository.save(user);
		
		return this.userTodto(user);
	}


	@Override
	public void deleteUserById(Integer userId) {
		
		User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		 
		this.userRepository.delete(user);

	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		 User databaseUser=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		 
		 databaseUser.setUserFullName(userDto.getUserFullName());
		 databaseUser.setUserEmail(userDto.getUserEmail());
		 databaseUser.setUserPassword(this.passwordEncoder.encode(userDto.getUserPassword()));
		 databaseUser.setUserAbout(userDto.getUserAbout());
		 
		 this.userRepository.save(databaseUser);
		
		 return this.userTodto(databaseUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		//User user=optionalUser.get();
		return this.userTodto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> listUsers = this.userRepository.findAll();
		
		List<UserDto> listUserDto=listUsers.stream().map(e->(this.userTodto(e))).collect(Collectors.toList());
		
//		listUsers.forEach(user->{
//			listUserDto.add(this.userTodto(user));
//		});
		return listUserDto;
	}
	
	public UserDto userTodto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto=new UserDto();
//		userDto.setUserId(user.getUserId());
//		userDto.setName(user.getName());
//		userDto.setUserPassword(user.getUserPassword());
//		userDto.setUserEmail(user.getUserEmail());
//		userDto.setUserAbout(user.getUserAbout());
		
		return userDto;
	}
	
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
//		user.setUserId(userDto.getUserId());
//		user.setUserName(userDto.getUserName());
//		user.setUserPassword(userDto.getUserPassword());
//		user.setUserEmail(userDto.getUserEmail());
//		user.setUserAbout(userDto.getUserAbout());
		
		return user;
	}

}
