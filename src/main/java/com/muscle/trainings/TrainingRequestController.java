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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    TrainingRequestResponse createRequest(@RequestHeader("Authorization") String header, @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingRequestService.createRequest(header, trainingRequestDto);
    }

    /**
     * Get request by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    TrainingRequestResponse getRequest(@PathVariable Long id) {
        return trainingRequestService.getRequest(id);
    }

    /**
     * Delete request by id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    void deleteRequest(@PathVariable Long id) {
        trainingRequestService.deleteRequest(id);
    }

    /**
     * Delete done requests
     * @param header
     * @return
     */
    @DeleteMapping()
    void deleteDoneRequests(@RequestHeader("Authorization") String header) {
        trainingRequestService.deleteUserDoneRequests(header);
    }


//    /**
//     * Edit request
//     * @param id
//     * @param trainingRequestDto
//     * @return
//     */
//    @PutMapping("/{id}")
//    TrainingRequestResponse updateRequest(@PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) {
//        return trainingRequestService.updateRequest(id, trainingRequestDto);
//    }

    /**
     * Get requests made by user
     * @param header
     * @return
     */
    @GetMapping("/user")
    Map<String, Object> getUserRequests(@RequestHeader("Authorization") String header,
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

        return response;
    }

    /**
     * Edit request
     * @param id
     * @param trainingRequestDto
     * @return
     */
    @PutMapping("/{id}")
    TrainingRequestResponse updateRequestTrainer(@RequestHeader("Authorization") String header, @PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) throws IOException {
        return trainingRequestService.updateRequest(header, id, trainingRequestDto);
    }

    /**
     * Get all requests
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    Map<String, Object> getRequests(@RequestParam(defaultValue = "0") Integer page,
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

        return response;
    }
}
