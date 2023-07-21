package com.quogle;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.quogle.configs.AppConstants;
import com.quogle.entities.Role;
import com.quogle.repositories.RoleRepository;



@SpringBootApplication
public class QuogleApisApplication implements CommandLineRunner {
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	public RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuogleApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelmapper() {
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("hello"));
		
		Role normal=new Role();
		Role admin=new Role();
		
		normal.setRoleId(AppConstants.NORMAL_ROLE_ID);
		normal.setRoleName("NORMAL_USER");
		
		admin.setRoleId(AppConstants.ADMIN_ROLE_ID);
		admin.setRoleName("ADMIN_USER");
		
		List<Role> roles = List.of(normal,admin);
		
		List<Role> result = this.roleRepository.saveAll(roles);
		
		result.forEach(role->System.out.println(role.getRoleName()));
	}
	
	

}
