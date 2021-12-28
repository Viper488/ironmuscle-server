package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.responses.TrainingRequestResponse;
import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.response.UserResponse;
import com.muscle.user.response.UserSafeDto;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
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
            name = "iron_user_id"
    )
    private IronUser user;

    @ManyToOne
    @JoinColumn(
            name = "trainer_id"
    )
    private IronUser trainer;

    @OneToOne
    @JoinColumn(
            name = "training_id"
    )
    private Training training;

    public TrainingRequestResponse detailedResponse() {
        UserSafeDto trainer = null;
        if(this.trainer != null) {
            trainer = this.trainer.safeDto();
        }
        TrainingResponse training = null;
        if(this.training != null) {
            training = this.training.response();
        }


        return TrainingRequestResponse.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .difficulty(this.difficulty)
                .bodyPart(this.bodyPart)
                .created_at(this.created_at)
                .resolved_at(this.resolved_at)
                .user(this.user.safeDto())
                .trainer(trainer)
                .training(training)
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
