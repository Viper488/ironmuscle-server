package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
