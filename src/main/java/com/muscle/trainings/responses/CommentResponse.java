package com.muscle.trainings.responses;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.response.IronUserResponse;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private IronUserResponse creator;
    private String value;
    private LocalDateTime created_at;
}
