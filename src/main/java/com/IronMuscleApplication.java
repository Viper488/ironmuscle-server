package com;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.service.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class IronMuscleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IronMuscleApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new RoleDto(null, "USER"));
			userService.saveRole(new RoleDto(null, "EMPLOYEE"));
			userService.saveRole(new RoleDto(null, "ADMIN"));

/*			userService.saveUser(new IronUserDto(null, "Nick", "Gage","FN" , "nick@gmail.com", "1234", false, true , new ArrayList<>()));
			userService.saveUser(new IronUserDto(null, "Matt", "Jackson", "YoungBuck" , "matt@gmail.com", "1234", false, true , new ArrayList<>()));
			userService.saveUser(new IronUserDto(null, "Adam", "Page" ,"Hangman" , "adam@gmail.com", "1234", false, true , new ArrayList<>()));
			userService.saveUser(new IronUserDto(null, "Ricky", "Starks", "Absolute" , "ricky@gmail.com", "1234", false, true , new ArrayList<>()));

			userService.addRoleToUser("FN" , "USER");
			userService.addRoleToUser("YoungBuck" , "USER");
			userService.addRoleToUser("Hangman" , "TRAINER");
			userService.addRoleToUser("Absolute" , "ADMIN");*/
		};
	}

/*	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
}
