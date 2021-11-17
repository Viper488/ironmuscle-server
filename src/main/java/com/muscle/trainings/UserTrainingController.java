package com.muscle.trainings;

import com.muscle.trainings.other.TrainingHistory;
import com.muscle.trainings.responses.*;
import com.muscle.trainings.service.PointService;
import com.muscle.trainings.service.UserTrainingHistoryService;
import com.muscle.trainings.service.UserTrainingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
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
     * Show all user trainings
     * @param header
     * @return
     */
    @GetMapping("/trainings")
    List<TrainingResponse> getUserTrainings(@RequestHeader("Authorization") String header) {
        return userTrainingsService.getUserTrainings(header);
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
                        .points(tuple.get(2, Integer.class))
                        .build()).collect(Collectors.toList());;

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
