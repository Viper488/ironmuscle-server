package com.muscle.user.entity;

import com.muscle.user.response.BadgeResponse;
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
public class Badge {

    @Id
    @SequenceGenerator(
            name = "badge_sequence",
            sequenceName = "badge_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "badge_sequence"
    )
    Long id;
    String name;
    Integer goal;
    String image;

    public BadgeResponse response() {
        return BadgeResponse.builder()
                .id(this.id)
                .name(this.name)
                .goal(this.goal)
                .build();
    }

    public BadgeResponse responseImage() {
        byte[] image = null;

        try {
            image = Files.readAllBytes(Paths.get("src/main/resources/images/" + this.image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BadgeResponse.builder()
                .id(this.id)
                .name(this.name)
                .goal(this.goal)
                .image(image)
                .build();
    }
}
