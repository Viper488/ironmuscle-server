package com.muscle.trainings.entity;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.responses.TrainingResponse;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private String type;
    private String image;
    private String difficulty;
    private Integer points;

    public TrainingDto dto() {
        return TrainingDto.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .image(this.image)
                .difficulty(this.difficulty)
                .points(this.points)
                .build();
    }

    public TrainingResponse response() {
        byte[] image = null;

        try {
            image = Files.readAllBytes(Paths.get("src/main/resources/images/" + this.image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return TrainingResponse.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .image(image)
                .difficulty(this.difficulty)
                .points(this.points)
                .build();
    }
}
