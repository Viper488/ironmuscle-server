package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.repository.TrainingRequestRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.responses.TrainingRequestResponse;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingRequestService {
    private final UserService userService;
    private final TrainingRequestRepository trainingRequestRepository;
    private final TrainingsRepository trainingsRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TrainingRequestResponse getRequest(Long id) {
        return trainingRequestRepository.findById(id).orElseThrow(() -> new IllegalStateException("Request not found")).detailedResponse();
    }
    @Transactional
    public Page<TrainingRequest> getPaginatedUserRequests(Pageable pageable, String header, String status, String query) {
        IronUser user = userService.getUserFromHeader(header);
        if(user.getRoles().stream().filter(role -> role.getName().equals("TRAINER")).count() >= 1)
            return trainingRequestRepository.findByTrainerIdAndStatusAndQuery(user.getId(), status, query, pageable);
        else
            return trainingRequestRepository.findByUserIdAndStatusAndQuery(user.getId(), status, query, pageable);
    }

    public TrainingRequestResponse createRequest(String header, TrainingRequestDto trainingRequestDto) {
        IronUser user = userService.getUserFromHeader(header);
        return trainingRequestRepository.save(TrainingRequest.builder()
                .title(trainingRequestDto.getTitle())
                .description(trainingRequestDto.getDescription())
                .bodyPart(trainingRequestDto.getBodyPart())
                .difficulty(trainingRequestDto.getDifficulty())
                .created_at(LocalDateTime.now())
                .user(user)
                .status(trainingRequestDto.getStatus())
                .build()).response();
    }

    public TrainingRequestResponse updateRequest(String header, Long id, TrainingRequestDto trainingRequestDto) {
        TrainingRequest trainingRequest = trainingRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        if(trainingRequest.getTrainer() == null) {
            IronUser trainer = userService.getUserFromHeader(header);
            trainingRequest.setTrainer(trainer);
        }

        trainingRequest.setStatus(trainingRequestDto.getStatus());
        if (trainingRequest.getStatus().equals("done")) {
            trainingRequest.setResolved_at(LocalDateTime.now());
        }

        if (trainingRequestDto.getTraining() != null) {
            Training training = trainingsRepository.findById(trainingRequestDto.getTraining().getId()).orElseThrow(()-> new IllegalStateException("Training not found"));
            trainingRequest.setTraining(training);
        }

        return trainingRequestRepository.save(trainingRequest).detailedResponse();
    }

    @Transactional
    public Page<TrainingRequest> getPaginatedRequests(Pageable pageable, String status, String query) {
        return trainingRequestRepository.findByStatusAndQuery(status, query, pageable);
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        trainingRequestRepository.deleteById(requestId);
    }

    @Transactional
    public void deleteUserDoneRequests(String header) {
        trainingRequestRepository.deleteByUserUsernameAndStatus(jwtUtil.extractUsername(header), "DONE");
    }
}
