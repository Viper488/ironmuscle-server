package com.muscle.trainings;

import com.muscle.trainings.dto.CommentDto;
import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.responses.CommentResponse;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.trainings.service.TrainingRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Edit request
     * @param id
     * @param trainingRequestDto
     * @return
     */
    @PutMapping("/{id}")
    TrainingRequestResponse updateRequest(@PathVariable Long id, @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingRequestService.updateRequest(id, trainingRequestDto);
    }

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
     * Get requests assigned to employee
     * @param header
     * @return
     */
    @GetMapping("/employee")
    List<TrainingRequestResponse> getEmployeeRequests(@RequestHeader("Authorization") String header) {
        return trainingRequestService.getEmployeeRequests(header);
    }

    /**
     * Comment on request
     * @param header
     * @param id
     * @param commentDto
     */
    @PutMapping("/{id}/comment")
    CommentResponse addCommentToRequest(@RequestHeader("Authorization") String header, @PathVariable() Long id, @RequestBody CommentDto commentDto) {
        return trainingRequestService.addCommentToRequest(header, id, commentDto);
    }

    /**
     * Get request comments
     * @param id
     * @return
     */
    @GetMapping("/{id}/comments")
    List<CommentResponse> showComments(@PathVariable() Long id) {
        return trainingRequestService.getRequestComments(id);
    }
    //TODO: Komantarze sie duplikuja po dodaniu i tylko jak employee dodaje - done
    //TODO: GET Comments as another endpoint - done
}
