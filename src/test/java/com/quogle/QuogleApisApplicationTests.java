package com.quogle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.quogle.repositories.UserRepository;
import com.quogle.services.UserServices;

@SpringBootTest
class QuogleApisApplicationTests {
	
	@Autowired
	UserRepository userrepository;

	@Autowired
	UserServices userServices;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void repoTesting() {
		
		System.out.println(this.userrepository.getClass().getName());
		System.out.println(this.userrepository.getClass().getPackageName());
		System.out.println();
	}
	
	@Test
	void servicesTesting() {
		
		System.out.println(this.userServices.getClass().getName());
		System.out.println(this.userServices.getClass().getPackageName());
	}

	@Test
	void customTesting() {
		String file="utkarsh.jpg";
		
		System.out.println(file.lastIndexOf("."));
	}
	

}
