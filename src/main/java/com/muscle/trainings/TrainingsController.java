package com.muscle.trainings;

import com.muscle.trainings.dto.*;
import com.muscle.trainings.responses.TrainingDetails;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.trainings.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TrainingsController {

    private final TrainingsService trainingsService;
    private final ExerciseService exerciseService;
    private final TrainingExerciseService trainingExerciseService;
    private final UserTrainingsService userTrainingsService;
    private final UserTrainingHistoryService userTrainingHistoryService;
    private final TrainingRequestService trainingRequestService;

    /**
     * Get template trainings
     * @return
     */
    @GetMapping("/training/template")
    List<TrainingDto> getTemplateTrainings() {
        return trainingsService.getTemplateTrainings();
    }

    /**
     * Show all trainings
     * @return
     */
    @GetMapping("/training")
    List<TrainingDto> getTrainings() {
        return trainingsService.getTrainings();
    }

    /**
     * Show training including exercises
     * @param id
     * @return
     */
    @GetMapping("/training/{id}")
    TrainingDetails getTrainingDetails(@PathVariable Long id) {
        return trainingExerciseService.getTrainingDetails(id);
    }

    /**
     * Create training
     * @param header
     * @param trainingDto
     * @return
     */
    @PostMapping("/training")
    TrainingDto createTraining(@RequestHeader("Authorization") String header, @RequestBody TrainingDto trainingDto) {
        return trainingsService.saveTraining(header, trainingDto);
    }

    /**
     * Edit training data
     * @param trainingDto
     * @return
     */
    @PutMapping("/training")
    TrainingDto editTraining(@RequestBody TrainingDto trainingDto) {
        return trainingsService.editTraining(trainingDto);
    }

    /**
     * Add exercise to training
     * @param id
     * @param exercises
     * @return
     */
    @PostMapping("/training/{id}/exercises")
    void addTrainingExercises(@PathVariable Long id, @RequestBody List<AddExerciseRequest> exercises) {
        trainingExerciseService.addExercises(id, exercises);
    }

    /**
     * Edit training exercises
     * @param id
     * @param exercises
     */
    @PutMapping("/training/{id}/exercises")
    void editTrainingExercises(@PathVariable Long id, @RequestBody List<AddExerciseRequest> exercises) {
        trainingExerciseService.editExercises(id, exercises);
    }

    /**
     * Create exercise
     * @param exerciseDto
     * @return
     */
    @PostMapping("/exercise")
    ExerciseDto createExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.saveExercise(exerciseDto);
    }

    /**
     * Edit exercise data
     * @param exerciseDto
     * @return
     */
    @PutMapping("/exercise")
    ExerciseDto editExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.editExercise(exerciseDto);
    }

    /**
     * Show all exercises
     * @return
     */
    @GetMapping("/exercise")
    List<ExerciseDto> getExercises() {
        return exerciseService.getExercises();
    }

    /**
     * Add training to user
     * @param header
     * @param id
     * @return
     */
    @PostMapping("/user/trainings")
    UserTrainingDto addTrainingToUser(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        return userTrainingsService.addTrainingToUser(header, id);
    }

    /**
     * Show all user trainings
     * @param header
     * @return
     */
    @GetMapping("/user/trainings")
    List<TrainingDto> getUserTrainings(@RequestHeader("Authorization") String header) {
        return userTrainingsService.getUserTrainings(header);
    }

    /**
     * Save user activity
     * @param header
     * @param trainingId
     * @return
     */
    @PostMapping("/user/history")
    UserTrainingHistoryDto saveUserActivity(@RequestHeader("Authorization") String header, @RequestParam("training") Long trainingId){
        return userTrainingHistoryService.saveUserActivity(header, trainingId);
    }


    /**
     * Show user history of trainings
     * @param header
     * @return
     */
    @GetMapping("/user/history")
    UserTrainingHistoryResponse getUserHistory(@RequestHeader("Authorization") String header){
        return userTrainingHistoryService.getUserTrainingHistory(header);
    }

    /**
     * Create request for training by user
     * @param header
     * @param trainingRequestDto
     * @return
     */
    @PostMapping("/user/request")
    TrainingRequestDto createRequest(@RequestHeader("Authorization") String header, @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingRequestService.createRequest(header, trainingRequestDto);
    }

    /**
     * Get request by id
     * @param id
     * @return
     */
    @GetMapping("/user/request/{id}")
    TrainingRequestDto getRequest(@PathVariable Long id) {
        return trainingRequestService.getRequest(id);
    }

    /**
     * Edit request
     * @param id
     * @param trainingRequestDto
     * @return
     */
    @PutMapping("/user/request/{id}")
    TrainingRequestDto updateRequest(@PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingRequestService.updateRequest(id, trainingRequestDto);
    }

    /**
     * Get requests made by user
     * @param header
     * @return
     */
    @GetMapping("/user/requests")
    List<TrainingRequestDto> getUserRequests(@RequestHeader("Authorization") String header) {
        return trainingRequestService.getUserRequests(header);
    }

    /**
     * Get requests assigned to employee
     * @param header
     * @return
     */
    @GetMapping("/employee/requests")
    List<TrainingRequestDto> getEmployeeRequests(@RequestHeader("Authorization") String header) {
        return trainingRequestService.getEmployeeRequests(header);
    }

    /**
     * Comment on request
     * @param header
     * @param requestId
     * @param commentDto
     */
    @PutMapping("/user/request/comment")
    void addCommentToRequest(@RequestHeader("Authorization") String header, @RequestParam("request") Long requestId, @RequestBody CommentDto commentDto) {
        trainingRequestService.addCommentToRequest(header, requestId, commentDto);
    }

    //TODO: Request o nowy trening - done
    //TODO: Wysylanie requestow do trenera z najmniejszym oblozeniem??? innym kryterium??? - done
    //TODO: GET pojedynczego requesta - done
    //TODO: Zastanowic sie nad iloscia zwracanych danych z endpointow
}
