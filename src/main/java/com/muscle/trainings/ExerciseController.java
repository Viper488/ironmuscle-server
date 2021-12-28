package com.muscle.trainings;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.service.ExerciseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.muscle.user.util.JwtUtil.generateErrorBody;

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
    @ApiOperation("Tworzy ćwiczenie")
    @PostMapping()
    ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto) {
        return ResponseEntity.ok(exerciseService.saveExercise(exerciseDto));
    }

    /**
     * Edit exercise data
     * @param exerciseDto
     * @return
     */
    @ApiOperation("Edytuje ćwiczenie")
    @PutMapping()
    ResponseEntity<?> editExercise(@RequestBody ExerciseDto exerciseDto) {
        try {
            return ResponseEntity.ok(exerciseService.editExercise(exerciseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Show all exercises
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("Pobiera ćwiczenia")
    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getExercises(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "100") Integer size,
                                     @RequestParam(defaultValue = "") String query) {
        Pageable paging = PageRequest.of(page, size);
        Page<Exercise> exercisesPage = exerciseService.getPaginatedExercises(paging, query);

        List<ExerciseDto> exercisesList = exercisesPage.getContent()
                .stream().map(Exercise::dto).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("exercises", exercisesList);
        response.put("currentPage", exercisesPage.getNumber());
        response.put("totalItems", exercisesPage.getTotalElements());
        response.put("totalPages", exercisesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}
