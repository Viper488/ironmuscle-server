package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.repository.TrainingRequestRepository;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingRequestService {
    private final UserService userService;
    private final TrainingRequestRepository trainingRequestRepository;
    private final JwtUtil jwtUtil;

    public TrainingRequestResponse getRequest(Long id){
        return trainingRequestRepository.findById(id).orElseThrow(() -> new IllegalStateException("Request not found")).detailedResponse();
    }

    public List<TrainingRequestResponse> getUserRequests(String header) {
        IronUserDto user = userService.getUserFromHeader(header).dto();

        return trainingRequestRepository.findByUserId(user.getId())
                .stream()
                .map(TrainingRequest::response)
                .sorted(Comparator.comparing(TrainingRequestResponse::getCreated_at).reversed())
                .collect(Collectors.toList());
    }

    public List<TrainingRequestResponse> getEmployeeRequests(String header, String status) {
        return trainingRequestRepository.findByEmployeeUsernameAndStatus(jwtUtil.extractUsername(header), status)
                .stream()
                .map(TrainingRequest::response)
                .sorted(Comparator.comparing(TrainingRequestResponse::getCreated_at).reversed())
                .collect(Collectors.toList());
    }

    public TrainingRequestResponse createRequest(String header, TrainingRequestDto trainingRequestDto) {
        IronUser user = userService.getUserFromHeader(header);
        return trainingRequestRepository.save(TrainingRequest.builder()
                .title(trainingRequestDto.getTitle())
                .description(trainingRequestDto.getDescription())
                .created_at(LocalDateTime.now())
                .user(user)
                .status("NEW")
                .build()).response();
    }

    public TrainingRequestResponse updateRequest(String header, Long id, TrainingRequestDto trainingRequestDto){
        TrainingRequest trainingRequest = trainingRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        if(trainingRequestDto.getTitle() != null)
            trainingRequest.setTitle(trainingRequestDto.getTitle());
        if(trainingRequestDto.getDescription() != null)
            trainingRequest.setDescription(trainingRequestDto.getDescription());
        if(trainingRequestDto.getStatus() != null) {
            trainingRequest.setStatus(trainingRequestDto.getStatus());
            if(trainingRequestDto.getStatus().equals("IN PROGRESS")) {
                IronUser employee = userService.getUserFromHeader(header);
                trainingRequest.setEmployee(employee);
            } else if (trainingRequestDto.getStatus().equals("DONE")) {
                trainingRequest.setResolved_at(LocalDateTime.now());
            }
        }
        return trainingRequestRepository.save(trainingRequest).detailedResponse();
    }

    public Page<TrainingRequest> getPaginatedRequests(Pageable paging) {
        return trainingRequestRepository.findByStatus("NEW", paging);
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        trainingRequestRepository.deleteById(requestId);
    }

    @Transactional
    public void deleteUserDoneRequests(String header) {
        trainingRequestRepository.deleteByUserUsernameAndStatus(jwtUtil.extractUsername(header), "DONE");
    }

/*    public CommentResponse addCommentToRequest(String header, Long requestId, CommentDto commentDto) {
        IronUser user = userService.getUserFromHeader(header);
        TrainingRequest trainingRequest = trainingRequestRepository.findById(requestId).orElseThrow(() -> new IllegalStateException("Request not found"));

        Comment comment = commentRepository.save(Comment.builder()
                            .creator(user)
                            .value(commentDto.getValue())
                            .created_at(LocalDateTime.now())
                            .trainingRequest(trainingRequest)
                            .build());

        commentRepository.save(comment);

        return comment.response();
    }

    public List<CommentResponse> getRequestComments(Long requestId) {
        List<Comment> comments = commentRepository.findByTrainingRequestId(requestId);

        return comments.stream().map(Comment::response).collect(Collectors.toList());
    }*/
}
