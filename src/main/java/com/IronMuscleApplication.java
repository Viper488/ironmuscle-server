package com;

import com.muscle.trainings.repository.*;
import com.muscle.trainings.service.ExerciseService;
import com.muscle.trainings.service.TrainingExerciseService;
import com.muscle.trainings.service.TrainingsService;
import com.muscle.trainings.service.UserTrainingsService;
import com.muscle.user.repository.ConfirmationTokenRepository;
import com.muscle.user.repository.RoleRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.service.impl.RegistrationService;
import com.muscle.user.service.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EnableJpaRepositories(basePackageClasses = {
		UserRepository.class,
		RoleRepository.class,
		ConfirmationTokenRepository.class,
		ExerciseRepository.class,
		TrainingsRepository.class,
		TrainingExerciseRepository.class,
		UserTrainingsRepository.class,
		UserTrainingHistoryRepository.class
})*/
public class IronMuscleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IronMuscleApplication.class, args);
	}

/*	@Bean
	CommandLineRunner run(UserTrainingsService userTrainingsService, TrainingExerciseService trainingExerciseService, TrainingsService trainingsService, ExerciseService exerciseService, UserService userService, RegistrationService registrationService) {
		return args -> {

		};
	}*/
}
