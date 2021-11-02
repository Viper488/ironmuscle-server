package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.responses.TrainingRequestResponse;
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
    private String difficulty;
    private String bodyPart;
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
            name = "employee_id"
    )
    private IronUser employee;

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
                .build();

    }

    public TrainingRequestResponse detailedResponse(){
        return TrainingRequestResponse.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .difficulty(this.difficulty)
                .bodyPart(this.bodyPart)
                .created_at(this.created_at)
                .resolved_at(this.resolved_at)
                .user(this.user.response())
                .build();

    }

    public TrainingRequestResponse response(){
        return TrainingRequestResponse.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .difficulty(this.difficulty)
                .bodyPart(this.bodyPart)
                .created_at(this.created_at)
                .resolved_at(this.resolved_at)
                .build();

    }
}
