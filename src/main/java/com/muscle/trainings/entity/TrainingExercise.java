package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingExerciseDto;
import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class TrainingExercise {

    @Id
    @SequenceGenerator(
            name = "training_exercise_sequence",
            sequenceName = "training_exercise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "training_exercise_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "training_id"
    )
    private Training training;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "exercise_id"
    )
    private Exercise exercise;
    private Integer time;
    private Integer repetitions;

    public TrainingExerciseDto dto() {
        return TrainingExerciseDto.builder()
                .id(this.id)
                .training(this.training.dto())
                .exercise(this.exercise.dto())
                .time(this.time)
                .repetitions(this.repetitions)
                .build();
    }
}
