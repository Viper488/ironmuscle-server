package com.muscle.trainings;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.trainings.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    Map<String, Object> getExercises(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "100") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Exercise> exercisesPage = exerciseService.getPaginatedExercises(paging);

        List<ExerciseDto> exercisesList = exercisesPage.getContent()
                .stream().map(Exercise::dto).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("exercises", exercisesList);
        response.put("currentPage", exercisesPage.getNumber());
        response.put("totalItems", exercisesPage.getTotalElements());
        response.put("totalPages", exercisesPage.getTotalPages());

        return response;
    }

}
