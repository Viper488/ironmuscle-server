package com.muscle.trainings;

import com.muscle.trainings.entity.Training;
import com.muscle.trainings.other.TrainingHistory;
import com.muscle.trainings.responses.*;
import com.muscle.trainings.service.PointService;
import com.muscle.trainings.service.UserTrainingHistoryService;
import com.muscle.trainings.service.UserTrainingsService;
import javassist.bytecode.ByteArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
     * @param user
     * @param training
     * @return
     */
    @PostMapping("/trainings/add")
    UserTrainingResponse addTrainingToUser(@RequestParam Long user, @RequestParam Long training) {
        return userTrainingsService.addTrainingToUser(user, training);
    }

    /**
     * Get all user trainings
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/trainings")
    Map<String, Object> getUserTrainings(@RequestHeader("Authorization") String header,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "100") Integer size,
                                     @RequestParam(defaultValue = "") String type,
                                     @RequestParam(defaultValue = "") String query) {
        Pageable paging = PageRequest.of(page, size);
        Page<Training> trainingPage = userTrainingsService.getPaginatedUserTrainings(paging, header, type, query);

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
     * Delete user training
     * @param header
     * @return
     */
    @DeleteMapping("/training/{id}")
    void deleteUserTraining(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        userTrainingsService.deleteUserTraining(header, id);
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
     * Get ranking
     * @return
     */
    @GetMapping("/ranking/list")
    Map<String, Object> getRanking(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "100") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Tuple> rankingPage = pointService.getPaginatedRanking(paging);

        List<RankingResponse> rankingList = rankingPage.getContent().stream()
                .map(tuple -> RankingResponse.builder()
                        .rank(tuple.get(0, BigInteger.class).longValue())
                        .username(tuple.get(1, String.class))
                        .icon(tuple.get(2, byte[].class))
                        .points(tuple.get(3, Integer.class))
                        .build()).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("ranking", rankingList);
        response.put("currentPage", rankingPage.getNumber());
        response.put("totalItems", rankingPage.getTotalElements());
        response.put("totalPages", rankingPage.getTotalPages());

        return response;
    }

    /**
     * Get user ranking
     * @return
     */
    @GetMapping("/ranking")
    RankingResponse getUserRank(@RequestHeader("Authorization") String header) {
        return pointService.getUserRank(header);
    }
}
