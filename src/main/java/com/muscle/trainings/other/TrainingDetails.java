package com.muscle.trainings.other;

import com.muscle.trainings.dto.TrainingDto;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDetails {
    private TrainingDto training;
    private List<ExerciseDetails> exercises;
}
