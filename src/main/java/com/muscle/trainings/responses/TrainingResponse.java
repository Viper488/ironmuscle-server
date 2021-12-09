package com.muscle.trainings.responses;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] image;
    private String difficulty;
    private Integer points;
}
