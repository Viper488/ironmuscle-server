package com.muscle.user.entity;

import com.muscle.user.response.BadgeResponse;
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

    public BadgeResponse response() {
        return BadgeResponse.builder()
                .id(this.id)
                .name(this.name)
                .goal(this.goal)
                .build();
    }
}
