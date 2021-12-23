package com.muscle.trainings;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.trainings.service.TrainingRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.muscle.user.util.JwtUtil.generateErrorBody;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request")
public class TrainingRequestController {

    private final TrainingRequestService trainingRequestService;

    /**
     * Create request for training by user
     * @param header
     * @param trainingRequestDto
     * @return
     */
    @PostMapping()
    ResponseEntity<?> createRequest(@RequestHeader("Authorization") String header, @RequestBody TrainingRequestDto trainingRequestDto) {
        try {
            return ResponseEntity.ok(trainingRequestService.createRequest(header, trainingRequestDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Get request by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    ResponseEntity<?> getRequest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(trainingRequestService.getRequest(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Delete request by id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        trainingRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete done requests
     * @param header
     * @return
     */
    @DeleteMapping()
    ResponseEntity<?> deleteDoneRequests(@RequestHeader("Authorization") String header) {
        trainingRequestService.deleteUserDoneRequests(header);
        return ResponseEntity.ok().build();
    }

    /**
     * Get requests made by user
     * @param header
     * @return
     */
    @GetMapping("/user")
    ResponseEntity<Map<String, Object>> getUserRequests(@RequestHeader("Authorization") String header,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "100") Integer size,
                                         @RequestParam(defaultValue = "") String status,
                                         @RequestParam(defaultValue = "") String query) {
        Pageable paging = PageRequest.of(page, size);
        Page<TrainingRequest> requestPage = trainingRequestService.getPaginatedUserRequests(paging, header, status, query);

        List<TrainingRequestResponse> requestsList = requestPage.getContent()
                .stream().map(TrainingRequest::detailedResponse).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("requests", requestsList);
        response.put("currentPage", requestPage.getNumber());
        response.put("totalItems", requestPage.getTotalElements());
        response.put("totalPages", requestPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    /**
     * Edit request
     * @param id
     * @param trainingRequestDto
     * @return
     */
    @PutMapping("/{id}")
    ResponseEntity<?> updateRequestTrainer(@RequestHeader("Authorization") String header, @PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) throws IOException {
        try {
            return ResponseEntity.ok(trainingRequestService.updateRequest(header, id, trainingRequestDto));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Get all requests
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getRequests(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "100") Integer size,
                                    @RequestParam(defaultValue = "new") String status,
                                    @RequestParam(defaultValue = "") String query) {
        Pageable paging = PageRequest.of(page, size);
        Page<TrainingRequest> requestPage = trainingRequestService.getPaginatedRequests(paging, status, query);

        List<TrainingRequestResponse> requestsList = requestPage.getContent()
                .stream().map(TrainingRequest::detailedResponse).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("requests", requestsList);
        response.put("currentPage", requestPage.getNumber());
        response.put("totalItems", requestPage.getTotalElements());
        response.put("totalPages", requestPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
