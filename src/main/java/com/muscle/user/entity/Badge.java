package com.muscle.user.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    String icon;
}
