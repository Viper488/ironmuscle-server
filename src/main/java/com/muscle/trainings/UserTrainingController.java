package com.muscle.trainings;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.dto.UserTrainingDto;
import com.muscle.trainings.dto.UserTrainingHistoryDto;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.trainings.service.UserTrainingHistoryService;
import com.muscle.trainings.service.UserTrainingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserTrainingController {

    private final UserTrainingsService userTrainingsService;
    private final UserTrainingHistoryService userTrainingHistoryService;

    /**
     * Add training to user
     * @param header
     * @param id
     * @return
     */
    @PostMapping("/trainings/{id}")
    UserTrainingDto addTrainingToUser(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        return userTrainingsService.addTrainingToUser(header, id);
    }

    /**
     * Show all user trainings
     * @param header
     * @return
     */
    @GetMapping("/trainings")
    List<TrainingDto> getUserTrainings(@RequestHeader("Authorization") String header) {
        return userTrainingsService.getUserTrainings(header);
    }

    /**
     * Save user activity
     * @param header
     * @param trainingId
     * @return
     */
    @PostMapping("/history")
    UserTrainingHistoryDto saveUserActivity(@RequestHeader("Authorization") String header, @RequestParam("training") Long trainingId){
        return userTrainingHistoryService.saveUserActivity(header, trainingId);
    }


    /**
     * Show user history of trainings
     * @param header
     * @return
     */
    @GetMapping("/history")
    UserTrainingHistoryResponse getUserHistory(@RequestHeader("Authorization") String header){
        return userTrainingHistoryService.getUserTrainingHistory(header);
    }
}
