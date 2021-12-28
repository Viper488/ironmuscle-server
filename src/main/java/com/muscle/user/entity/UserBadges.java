package com.muscle.user.entity;

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
public class UserBadges {

    @EmbeddedId
    UserBadgesKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "iron_user_id")
    IronUser user;

    @ManyToOne
    @MapsId("badgeId")
    @JoinColumn(name = "badge_id")
    Badge badge;
}
