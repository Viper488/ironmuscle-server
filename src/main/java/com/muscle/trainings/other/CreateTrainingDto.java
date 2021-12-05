package com.muscle.trainings.other;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingDto {
    private String name;
    private String type;
    private String image;
    private String difficulty;
    private Integer points;
}
