package com.muscle.trainings.dto;

import com.muscle.user.dto.IronUserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequestDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String difficulty;
    private String bodyPart;
    private LocalDateTime created_at;
    private LocalDateTime resolved_at;
    private IronUserDto user;
    private IronUserDto trainer;
    private List<CommentDto> comments;
}
