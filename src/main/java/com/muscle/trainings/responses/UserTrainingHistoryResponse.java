package com.muscle.trainings.responses;

import com.muscle.trainings.other.TrainingHistory;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.response.IronUserResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryResponse {
    Long id;
    IronUserResponse user;
    TrainingResponse training;
    LocalDateTime trainingDate;
}
