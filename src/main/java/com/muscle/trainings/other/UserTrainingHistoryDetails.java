package com.muscle.trainings.other;

import com.muscle.user.response.IronUserResponse;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryDetails {
    Long id;
    IronUserResponse user;
    List<TrainingHistory> trainingHistory;
}
