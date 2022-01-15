package com.muscle.trainings.entity;

import com.muscle.trainings.dto.UserTrainingHistoryDto;
import com.muscle.trainings.other.TrainingHistory;
import com.muscle.user.entity.IronUser;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class UserTrainingHistory {

    @Id
    @SequenceGenerator(
            name = "user_training_history_sequence",
            sequenceName = "user_training_history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_training_history_sequence"
    )
    private Long id;

    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "iron_user_id"
    )
    private IronUser user;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "training_id"
    )
    private Training training;
    private LocalDateTime trainingDate;
    private Integer trainingTime;
    public UserTrainingHistoryDto dto() {
        return UserTrainingHistoryDto.builder()
                .id(this.id)
                .user(this.user.safeDto())
                .training(this.training.dto())
                .trainingDate(this.trainingDate)
                .trainingTime(this.trainingTime)
                .build();

    }

    public TrainingHistory response(){
        byte[] image = null;

        try {
            image = Files.readAllBytes(Paths.get("src/main/resources/images/" + this.getTraining().getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return TrainingHistory.builder()
                .id(this.getId())
                .name(this.getTraining().getName())
                .image(image)
                .difficulty(this.getTraining().getDifficulty())
                .points(this.getTraining().getPoints())
                .date(this.getTrainingDate())
                .time(this.getTrainingTime())
                .build();

    }
}
