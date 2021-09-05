package com.muscle.trainings.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingExerciseDto {
    private Long id;
    private TrainingDto training;
    private ExerciseDto exercise;
    private Integer time;
    private Integer repetitions;
}
