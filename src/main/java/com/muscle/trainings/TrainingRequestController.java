package com.muscle.trainings;

import com.muscle.trainings.dto.CommentDto;
import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.responses.CommentResponse;
import com.muscle.trainings.responses.RankingResponse;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.trainings.service.TrainingRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    List<TrainingRequestResponse> getUserRequests(@RequestHeader("Authorization") String header) {
        return trainingRequestService.getUserRequests(header);
    }

    /**
     * Edit request
     * @param id
     * @param trainingRequestDto
     * @return
     */
    @PutMapping("/{id}")
    TrainingRequestResponse updateRequestEmployee(@RequestHeader("Authorization") String header, @PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingRequestService.updateRequest(header, id, trainingRequestDto);
    }


    /**
     * Get requests assigned to employee by status (IN PROGRESS or DONE)
     * @param header
     * @return
     */
    @GetMapping("/employee")
    List<TrainingRequestResponse> getEmployeeRequests(@RequestHeader("Authorization") String header, @RequestParam("status") String status) {
        return trainingRequestService.getEmployeeRequests(header, status);
    }

    /**
     * Get all requests
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    Map<String, Object> getRequests(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "20") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<TrainingRequest> requestPage = trainingRequestService.getPaginatedRequests(paging);

        List<TrainingRequestResponse> requestsList = requestPage.getContent()
                .stream().map(TrainingRequest::response).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("requests", requestsList);
        response.put("currentPage", requestPage.getNumber());
        response.put("totalItems", requestPage.getTotalElements());
        response.put("totalPages", requestPage.getTotalPages());

        return response;
    }


/*    *//**
     * Comment on request
     * @param header
     * @param id
     * @param commentDto
     *//*
    @PutMapping("/{id}/comment")
    CommentResponse addCommentToRequest(@RequestHeader("Authorization") String header, @PathVariable() Long id, @RequestBody CommentDto commentDto) {
        return trainingRequestService.addCommentToRequest(header, id, commentDto);
    }

    *//**
     * Get request comments
     * @param id
     * @return
     *//*
    @GetMapping("/{id}/comments")
    List<CommentResponse> showComments(@PathVariable() Long id) {
        return trainingRequestService.getRequestComments(id);
    }*/
}
