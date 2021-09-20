package com.muscle.trainings;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    /**
     * Create exercise
     * @param exerciseDto
     * @return
     */
    @PostMapping()
    ExerciseDto createExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.saveExercise(exerciseDto);
    }

    /**
     * Edit exercise data
     * @param exerciseDto
     * @return
     */
    @PutMapping()
    ExerciseDto editExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.editExercise(exerciseDto);
    }

    /**
     * Show all exercises
     * @return
     */
    @GetMapping()
    List<ExerciseDto> getExercises() {
        return exerciseService.getExercises();
    }

}
