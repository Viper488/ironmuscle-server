package com.muscle.user.response;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponse {
    Long id;
    String name;
    Integer goal;
}
