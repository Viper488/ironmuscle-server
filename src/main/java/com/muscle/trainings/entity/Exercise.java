package com.muscle.trainings.entity;

import com.muscle.trainings.dto.ExerciseDto;
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
public class Exercise {

    @Id
    @SequenceGenerator(
            name = "exercise_sequence",
            sequenceName = "exercise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exercise_sequence"
    )
    private Long id;
    private String name;
    private String gif;
    private String video;

    public ExerciseDto dto() {
        return ExerciseDto.builder()
                .id(this.id)
                .name(this.name)
                .gif(this.gif)
                .video(this.video)
                .build();
    }
}
