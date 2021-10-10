package com.muscle.trainings;

import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.trainings.other.UserTrainingHistoryDetails;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.trainings.responses.UserTrainingResponse;
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
    UserTrainingResponse addTrainingToUser(@RequestHeader("Authorization") String header, @PathVariable Long id) throws Exception {
        return userTrainingsService.addTrainingToUser(header, id);
    }

    /**
     * Show all user trainings
     * @param header
     * @return
     */
    @GetMapping("/trainings")
    List<TrainingResponse> getUserTrainings(@RequestHeader("Authorization") String header) throws Exception {
        return userTrainingsService.getUserTrainings(header);
    }

    /**
     * Save user activity
     * @param header
     * @param trainingId
     * @return
     */
    @PostMapping("/history")
    UserTrainingHistoryResponse saveUserActivity(@RequestHeader("Authorization") String header, @RequestParam("training") Long trainingId) throws Exception {
        return userTrainingHistoryService.saveUserActivity(header, trainingId);
    }


    /**
     * Show user history of trainings
     * @param header
     * @return
     */
    @GetMapping("/history")
    UserTrainingHistoryDetails getUserHistory(@RequestHeader("Authorization") String header) throws Exception {
        return userTrainingHistoryService.getUserTrainingHistory(header);
    }
}
