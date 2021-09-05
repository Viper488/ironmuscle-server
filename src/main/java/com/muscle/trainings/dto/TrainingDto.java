package com.muscle.trainings.dto;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {
    private Long id;
    private String name;
    private String image;
    private String difficulty;
}
