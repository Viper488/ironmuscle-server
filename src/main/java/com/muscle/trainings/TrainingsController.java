package com.muscle.trainings;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.responses.TrainingDetails;
import com.muscle.trainings.service.TrainingExerciseService;
import com.muscle.trainings.service.UserTrainingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TrainingsController {

    private final TrainingExerciseService trainingExerciseService;
    private final UserTrainingsService userTrainingsService;

    @GetMapping("/training/details/{id}")
    TrainingDetails getTrainingDetails(@PathVariable Long id) {
        return trainingExerciseService.getTrainingDetails(id);
    }

    @GetMapping("/user/trainings")
    List<TrainingDto> getUserTrainings(@RequestHeader("Authorization") String header) {
        return userTrainingsService.getUserTrainings(header);
    }


}
