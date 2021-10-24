package com.muscle.trainings;

import com.muscle.trainings.other.TrainingHistory;
import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.trainings.other.UserTrainingHistoryDetails;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.trainings.responses.UserTrainingResponse;
import com.muscle.trainings.service.PointService;
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
    private final PointService pointService;

    /**
     * Add training to user
     * @param header
     * @param id
     * @return
     */
    @PostMapping("/trainings/{id}")
    UserTrainingResponse addTrainingToUser(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        return userTrainingsService.addTrainingToUser(header, id);
    }

    /**
     * Show all user trainings
     * @param header
     * @return
     */
    @GetMapping("/trainings")
    List<TrainingResponse> getUserTrainings(@RequestHeader("Authorization") String header) {
        return userTrainingsService.getUserTrainings(header);
    }

    /**
     * Save user activity
     * @param header
     * @param trainingId
     * @return
     */
    @PostMapping("/history")
    UserTrainingHistoryResponse saveUserActivity(@RequestHeader("Authorization") String header, @RequestParam("training") Long trainingId, @RequestParam("time") Integer time) {
        return userTrainingHistoryService.saveUserActivity(header, trainingId, time);
    }


    /**
     * Show user history of trainings
     * @param header
     * @return
     */
    @GetMapping("/history")
    List<TrainingHistory> getUserHistory(@RequestHeader("Authorization") String header, @RequestParam("y") int year, @RequestParam("m") int month) {
        return userTrainingHistoryService.getUserTrainingHistory(header, year, month);
    }

    /**
     * Get user points
     * @param header
     * @return
     */
    @GetMapping("/points")
    Integer getUserPoints(@RequestHeader("Authorization") String header) {
        return pointService.getUserPoints(header);
    }
}
