package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingDto;
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
public class Training {

    @Id
    @SequenceGenerator(
            name = "training_sequence",
            sequenceName = "training_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "training_sequence"
    )
    private Long id;
    private String name;
    private String image;
    private String difficulty;

    public TrainingDto dto() {
        return TrainingDto.builder()
                .id(this.id)
                .name(this.name)
                .image(this.image)
                .difficulty(this.difficulty)
                .build();
    }
}
