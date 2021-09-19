package com.muscle.trainings.dto;

import com.muscle.trainings.entity.Comment;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequestDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime resolved_at;
    private IronUserDto user;
    private IronUserDto employee;
    private List<CommentDto> comments = new ArrayList<>();
}
