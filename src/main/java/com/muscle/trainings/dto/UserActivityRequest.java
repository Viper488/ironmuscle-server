package com.muscle.trainings.dto;

import lombok.*;

import java.util.Date;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityRequest {
    Long trainingId;
    Date date;
}
