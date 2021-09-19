package com.muscle.trainings.dto;

import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private IronUser creator;
    private String value;
    private LocalDateTime created_at;
}
