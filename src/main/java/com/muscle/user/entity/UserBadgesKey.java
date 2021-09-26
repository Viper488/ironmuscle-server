package com.muscle.user.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class UserBadgesKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "badge_id")
    Long badgeId;
}
