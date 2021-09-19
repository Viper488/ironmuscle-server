package com.muscle.trainings.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingHistory {
    private Long id;
    private String name;
    private String image;
    private String difficulty;
    private Integer points;
    private LocalDateTime date;
}
