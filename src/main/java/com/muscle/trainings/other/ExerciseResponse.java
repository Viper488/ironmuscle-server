package com.muscle.trainings.other;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private String gif;
    private String video;
    private Integer time;
    private Integer repetitions;

}
