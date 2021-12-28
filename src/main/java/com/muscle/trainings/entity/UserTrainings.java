package com.muscle.trainings.entity;

import com.muscle.trainings.responses.UserTrainingResponse;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
@IdClass(UserTrainingId.class)
public class UserTrainings implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "iron_user_id"
    )
    private IronUser ironUser;

    @Id
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "training_id"
    )
    private Training training;

    public UserTrainingResponse response() {
        return UserTrainingResponse.builder()
                .user(this.ironUser.safeDto())
                .training(this.training.response())
                .build();
    }
}
