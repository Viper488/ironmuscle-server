package com.muscle.trainings.other;

import com.muscle.trainings.responses.TrainingResponse;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDetails {
    private TrainingResponse training;
    private List<ExerciseResponse> exercises;
}
