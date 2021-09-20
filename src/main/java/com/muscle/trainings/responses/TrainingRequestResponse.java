package com.muscle.trainings.responses;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.response.IronUserResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequestResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime resolved_at;
    private IronUserResponse user;
    private IronUserResponse employee;
}
