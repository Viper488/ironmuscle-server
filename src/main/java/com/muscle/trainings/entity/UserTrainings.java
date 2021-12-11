package com.muscle.trainings.entity;

import com.muscle.trainings.dto.UserTrainingDto;
import com.muscle.trainings.responses.UserTrainingResponse;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class UserTrainings {

    @Id
    @SequenceGenerator(
            name = "user_trainings_sequence",
            sequenceName = "user_trainings_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_trainings_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private IronUser ironUser;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "training_id"
    )
    private Training training;

    public UserTrainingResponse response() {
        return UserTrainingResponse.builder()
                .id(this.id)
                .user(this.ironUser.safeDto())
                .training(this.training.response())
                .build();
    }
}
