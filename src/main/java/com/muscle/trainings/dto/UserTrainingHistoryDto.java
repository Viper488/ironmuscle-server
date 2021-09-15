package com.muscle.trainings.dto;

import com.muscle.user.dto.IronUserDto;
import lombok.*;
import java.util.Date;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryDto {
    private Long id;
    private IronUserDto user;
    private TrainingDto training;
    private Date date;
}
