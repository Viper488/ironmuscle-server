package com.muscle.trainings;

import com.muscle.trainings.entity.Training;
import com.muscle.trainings.responses.*;
import com.muscle.trainings.service.PointService;
import com.muscle.trainings.service.UserTrainingHistoryService;
import com.muscle.trainings.service.UserTrainingsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @ApiOperation("Dodaje trening do treningów użytkownika")
    @PostMapping("/trainings/add")
    ResponseEntity<?> addTrainingToUser(@RequestParam Long user, @RequestParam Long training) {
        try {
            log.info("Adding training to user");
            return ResponseEntity.ok(userTrainingsService.addTrainingToUser(user, training));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Get all user trainings
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("Pobiera listę treningów uzytkownika")
    @GetMapping("/trainings")
    ResponseEntity<Map<String, Object>> getUserTrainings(@RequestHeader("Authorization") String header,
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

        return ResponseEntity.ok(response);
    }

    /**
     * Delete user training
     * @param header
     * @return
     */
    @ApiOperation("Usuwa trening użytkownika")
    @DeleteMapping("/training/{id}")
    ResponseEntity<?> deleteUserTraining(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        try {
            userTrainingsService.deleteUserTraining(header, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Save user activity
     * @param header
     * @param trainingId
     * @return
     */
    @ApiOperation("Zapisuje treining w historii użytkownika")
    @PostMapping("/history")
    ResponseEntity<?> saveUserActivity(@RequestHeader("Authorization") String header, @RequestParam("training") Long trainingId, @RequestParam("time") Integer time) {
        try {
            return ResponseEntity.ok(userTrainingHistoryService.saveUserActivity(header, trainingId, time));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }


    /**
     * Show user history of trainings
     * @param header
     * @return
     */
    @ApiOperation("Pobiera historie treningów uzytkownika")
    @GetMapping("/history")
    ResponseEntity<?> getUserHistory(@RequestHeader("Authorization") String header, @RequestParam("y") int year, @RequestParam("m") int month) {
        try {
            return ResponseEntity.ok(userTrainingHistoryService.getUserTrainingHistory(header, year, month));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Get ranking
     * @return
     */
    @ApiOperation("Pobiera ranking uzytkowników")
    @GetMapping("/ranking/list")
    ResponseEntity<Map<String, Object>> getRanking(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "100") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(pointService.getPaginatedRanking(paging));
    }

    /**
     * Get user ranking
     * @return
     */
    @ApiOperation("Pobiera miejce użytkownika w rankingu")
    @GetMapping("/ranking")
    ResponseEntity<?> getUserRank(@RequestHeader("Authorization") String header) {
        try {
            return ResponseEntity.ok(pointService.getUserRank(header));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }
}
