package com.muscle.trainings.other;

import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingHistory {
    private Long id;
    private String name;
    private byte[] image;
    private String difficulty;
    private Integer points;
    private LocalDateTime date;
    private Integer time;
}
