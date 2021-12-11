package com.muscle.trainings.dto;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.response.UserSafeDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryDto {
    private Long id;
    private UserSafeDto user;
    private TrainingDto training;
    private LocalDateTime trainingDate;
    private Integer trainingTime;
}
