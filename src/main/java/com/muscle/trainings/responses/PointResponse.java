package com.muscle.trainings.responses;

import com.muscle.user.response.IronUserResponse;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PointResponse {
    Long id;
    Integer points;
    IronUserResponse user;
}
