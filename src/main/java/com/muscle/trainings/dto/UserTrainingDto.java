package com.muscle.trainings.dto;

import com.muscle.user.dto.IronUserDto;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingDto {
    Long id;
    IronUserDto user;
    TrainingDto training;
}
