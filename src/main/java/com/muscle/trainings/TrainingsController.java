package com.muscle.trainings;

import com.muscle.trainings.dto.*;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.other.TrainingDetails;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.trainings.service.*;
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
@RequestMapping("/api/v1/training")
public class TrainingsController {

    private final TrainingsService trainingsService;
    private final TrainingExerciseService trainingExerciseService;

    /**
     * Get trainings by type
     * @return
     */
    @GetMapping("/type/{type}")
    List<TrainingResponse> getTrainingsByType(@PathVariable String type) {
        return trainingsService.getTrainingsByType(type);
    }

    /**
     * Get all trainings
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    Map<String, Object> getTrainings(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "100") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Training> trainingPage = trainingsService.getPaginatedTrainings(paging);

        List<TrainingResponse> trainingsList = trainingPage.getContent()
                .stream().map(Training::response).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("trainings", trainingsList);
        response.put("currentPage", trainingPage.getNumber());
        response.put("totalItems", trainingPage.getTotalElements());
        response.put("totalPages", trainingPage.getTotalPages());

        return response;
    }

    /**
     * Show training including exercises
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    TrainingDetails getTrainingDetails(@PathVariable Long id) {
        return trainingExerciseService.getTrainingDetails(id);
    }

    /**
     * Create training
     * @param trainingDto
     * @return
     */
    @PostMapping()
    TrainingResponse createTraining(@RequestBody TrainingDto trainingDto) {
        return trainingsService.saveTraining(trainingDto);
    }

    /**
     * Edit training data
     * @param trainingDto
     * @return
     */
    @PutMapping()
    TrainingResponse editTraining(@RequestBody TrainingDto trainingDto) {
        return trainingsService.editTraining(trainingDto);
    }

    /**
     * Add exercise to training
     * @param id
     * @param exercises
     * @return
     */
    @PostMapping("/{id}/exercises")
    void addTrainingExercises(@PathVariable Long id, @RequestBody List<AddExerciseRequest> exercises) {
        trainingExerciseService.addExercises(id, exercises);
    }

    /**
     * Edit training exercises
     * @param id
     * @param exercises
     */
    @PutMapping("/{id}/exercises")
    void editTrainingExercises(@PathVariable Long id, @RequestBody List<AddExerciseRequest> exercises) {
        trainingExerciseService.editExercises(id, exercises);
    }
}
