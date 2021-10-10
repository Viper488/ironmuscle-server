package com.muscle.trainings.service;

import com.muscle.trainings.dto.CommentDto;
import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.Comment;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.repository.CommentRepository;
import com.muscle.trainings.repository.TrainingRequestRepository;
import com.muscle.trainings.responses.CommentResponse;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingRequestService {
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final TrainingRequestRepository trainingRequestRepository;
    private final UserRepository userRepository;

    public TrainingRequestResponse getRequest(Long id){
        return trainingRequestRepository.findById(id).orElseThrow(() -> new IllegalStateException("Request not found")).detailedResponse();
    }

    public List<TrainingRequestResponse> getUserRequests(String header) {
        IronUserDto user = userService.getUserFromHeader(header).dto();

        return trainingRequestRepository.findByUserId(user.getId()).stream().map(TrainingRequest::response).collect(Collectors.toList());
    }

    public List<TrainingRequestResponse> getEmployeeRequests(String header) {
        IronUserDto employee = userService.getUserFromHeader(header).dto();
        return trainingRequestRepository.findByEmployeeId(employee.getId()).stream().map(TrainingRequest::response).collect(Collectors.toList());
    }

    public TrainingRequestResponse createRequest(String header, TrainingRequestDto trainingRequestDto) {
        IronUser user = userService.getUserFromHeader(header);
        IronUser employee = getFreeEmployee();
        return trainingRequestRepository.save(TrainingRequest.builder()
                .title(trainingRequestDto.getTitle())
                .description(trainingRequestDto.getDescription())
                .created_at(LocalDateTime.now())
                .user(user)
                .status("NEW")
                .employee(employee)
                .comments(new ArrayList<>())
                .build()).detailedResponse();
    }

    public TrainingRequestResponse updateRequest(Long id, TrainingRequestDto trainingRequestDto){
        TrainingRequest trainingRequest = trainingRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        if(trainingRequestDto.getTitle() != null)
            trainingRequest.setTitle(trainingRequestDto.getTitle());
        if(trainingRequestDto.getDescription() != null)
            trainingRequest.setDescription(trainingRequestDto.getDescription());
        if(trainingRequestDto.getStatus() != null) {
            trainingRequest.setStatus(trainingRequestDto.getStatus());
            if (trainingRequestDto.getStatus().equals("DONE"))
                trainingRequest.setResolved_at(LocalDateTime.now());
        }
        return trainingRequestRepository.save(trainingRequest).detailedResponse();
    }

    private IronUser getFreeEmployee() {

        Optional<Long> employeeId = trainingRequestRepository.selectEmployeeWithLessRequests();

        if(employeeId.isPresent()) {
            return userRepository.findById(employeeId.get()).orElseThrow(() -> new IllegalStateException("Employee not found"));
        } else {
            List<IronUser> employees = userRepository.findByRole("TRAINER");

            if(employees.size() > 0)
                return employees.get(0);
            else
                throw new IllegalStateException("Employee not found");
        }
    }

    public CommentResponse addCommentToRequest(String header, Long requestId, CommentDto commentDto) {
        IronUser user = userService.getUserFromHeader(header);
        TrainingRequest trainingRequest = trainingRequestRepository.findById(requestId).orElseThrow(() -> new IllegalStateException("Request not found"));

        Comment comment = commentRepository.save(Comment.builder()
                            .creator(user)
                            .value(commentDto.getValue())
                            .created_at(LocalDateTime.now())
                            .build());

        trainingRequest.getComments().add(comment);
        trainingRequestRepository.save(trainingRequest);

        return comment.response();
    }

    public List<CommentResponse> getRequestComments(Long requestId) {
        TrainingRequest trainingRequest = trainingRequestRepository.findById(requestId).orElseThrow(() -> new IllegalStateException("Request not found"));

        return trainingRequest.getComments().stream().map(Comment::response).collect(Collectors.toList());
    }
}
