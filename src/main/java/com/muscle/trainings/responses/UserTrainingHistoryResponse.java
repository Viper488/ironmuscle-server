package com.muscle.trainings.responses;

import com.muscle.user.dto.IronUserDto;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryResponse {
    IronUserDto user;
    List<TrainingHistory> trainingHistory;
}
