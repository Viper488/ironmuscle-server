package com.muscle.user.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
}
