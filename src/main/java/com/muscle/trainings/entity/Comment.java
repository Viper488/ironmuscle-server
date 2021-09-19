package com.muscle.trainings.entity;

import com.muscle.trainings.dto.CommentDto;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class Comment {

    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private IronUser creator;
    private String value;
    private LocalDateTime created_at;

    public CommentDto dto(){
        return CommentDto.builder()
                .id(this.id)
                .creator(this.creator)
                .value(this.value)
                .created_at(this.created_at)
                .build();
    }
}
