package com;

import com.muscle.trainings.dto.AddExerciseRequest;
import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.repository.ExerciseRepository;
import com.muscle.trainings.repository.TrainingExerciseRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingsRepository;
import com.muscle.trainings.service.ExerciseService;
import com.muscle.trainings.service.TrainingExerciseService;
import com.muscle.trainings.service.TrainingsService;
import com.muscle.trainings.service.UserTrainingsService;
import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.dto.RoleDto;
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
@EnableJpaRepositories(basePackageClasses = {
		UserRepository.class,
		RoleRepository.class,
		ConfirmationTokenRepository.class,
		ExerciseRepository.class,
		TrainingsRepository.class,
		TrainingExerciseRepository.class,
		UserTrainingsRepository.class
})
public class IronMuscleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IronMuscleApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserTrainingsService userTrainingsService, TrainingExerciseService trainingExerciseService, TrainingsService trainingsService, ExerciseService exerciseService, UserService userService, RegistrationService registrationService) {
		return args -> {
			userService.saveRole(new RoleDto(null, "USER"));
			userService.saveRole(new RoleDto(null, "EMPLOYEE"));
			userService.saveRole(new RoleDto(null, "ADMIN"));

			String token = registrationService.register(new RegistrationRequestDto("admin",  "admin@admin.com", "admin"));
			registrationService.confirmToken(token);

			exerciseService.saveExercise(new ExerciseDto(null, "Jumping jacks", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));
			exerciseService.saveExercise(new ExerciseDto(null, "Abdominal crunches", "https://i.imgur.com/UJAnRhJ.gif", "_YVhhXc2pSY"));
			exerciseService.saveExercise(new ExerciseDto(null, "Russian twist", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "l4kQd9eWclE"));
			exerciseService.saveExercise(new ExerciseDto(null, "Mountain climber", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));
			exerciseService.saveExercise(new ExerciseDto(null, "Heel touch", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));
			exerciseService.saveExercise(new ExerciseDto(null, "Leg raises",  "https://i.imgur.com/JtgnFH1.gif", "l4kQd9eWclE"));
			exerciseService.saveExercise(new ExerciseDto(null, "Plank", "https://i.pinimg.com/originals/cf/b5/67/cfb5677a755fe7288b608a4fec6f09a0.gif", "y1hXARQhHZM"));
			exerciseService.saveExercise(new ExerciseDto(null, "Cobra stretch", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));
			exerciseService.saveExercise(new ExerciseDto(null, "Spine lumbar twist stretch left", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));
			exerciseService.saveExercise(new ExerciseDto(null, "Spine lumbar twist stretch right", "https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif", "1b98WrRrmUs"));

			trainingsService.saveTraining(new TrainingDto(null, "Abdominal Beginner", "placeholder", "Beginner"));
			trainingsService.saveTraining(new TrainingDto(null, "Abdominal Intermediate", "placeholder", "Intermediate"));

			trainingExerciseService.save( new AddExerciseRequest(1L, 1L, 20, 0));
			trainingExerciseService.save( new AddExerciseRequest(1L, 2L, 0, 20));
			trainingExerciseService.save( new AddExerciseRequest(1L, 3L, 0, 32));
			trainingExerciseService.save( new AddExerciseRequest(1L, 4L, 0, 30));
			trainingExerciseService.save( new AddExerciseRequest(1L, 5L, 0, 20));
			trainingExerciseService.save( new AddExerciseRequest(2L, 6L, 0, 16));
			trainingExerciseService.save( new AddExerciseRequest(2L, 7L, 30, 0));
			trainingExerciseService.save( new AddExerciseRequest(2L, 8L, 30, 0));
			trainingExerciseService.save( new AddExerciseRequest(2L, 9L, 30, 0));
			trainingExerciseService.save( new AddExerciseRequest(2L, 10L, 30, 0));

			userTrainingsService.addTrainingToUser("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA2ODUwOCwiaWF0IjoxNjMxMDMyNTA4fQ.wJPRfIBiTNLpCYyjqqf9fiXdUjVg6WzkjztkR2nWmNE", 1L);
			userTrainingsService.addTrainingToUser("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA2ODUwOCwiaWF0IjoxNjMxMDMyNTA4fQ.wJPRfIBiTNLpCYyjqqf9fiXdUjVg6WzkjztkR2nWmNE", 2L);
			/*userService.saveUser(new IronUserDto(null, "Matt", "Jackson", "YoungBuck" , "matt@gmail.com", "1234", false, true , new ArrayList<>()));
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
