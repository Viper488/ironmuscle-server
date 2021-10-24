package com.muscle.trainings.dto;

import com.muscle.user.dto.IronUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private IronUserDto creator;
    private String value;
    private LocalDateTime created_at;
    private TrainingRequestDto trainingRequest;
}
