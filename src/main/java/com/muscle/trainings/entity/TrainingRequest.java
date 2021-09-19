package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class TrainingRequest {

    @Id
    @SequenceGenerator(
            name = "training_request_sequence",
            sequenceName = "training_request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "training_request_sequence"
    )
    private Long id;

    private String title;
    private String description;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime resolved_at;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private IronUser user;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "employee_id"
    )
    private IronUser employee;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public TrainingRequestDto dto(){
        return TrainingRequestDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .created_at(this.created_at)
                .resolved_at(this.resolved_at)
                .user(this.user.dto())
                .employee(this.employee.dto())
                .comments(this.comments.stream().map(Comment::dto).collect(Collectors.toList()))
                .build();

    }
}
