package com.muscle.trainings;

import com.muscle.trainings.dto.TrainingDetails;
import com.muscle.trainings.service.TrainingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TrainingsController {

    private final TrainingExerciseService trainingExerciseService;

    @GetMapping("/training/details/{id}")
    TrainingDetails getTrainingDetails(@PathVariable Long id) {
        return trainingExerciseService.getTrainingDetails(id);
    }
}
