package com.muscle.trainings.dto;

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
