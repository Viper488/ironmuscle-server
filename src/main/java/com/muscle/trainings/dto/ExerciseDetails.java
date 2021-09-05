package com.muscle.trainings.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDetails {
    private Long id;
    private String name;
    private String gif;
    private String video;
    private Integer time;
    private Integer repetitions;

}
