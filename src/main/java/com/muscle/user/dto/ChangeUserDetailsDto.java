package com.muscle.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangeUserDetailsDto {
    private final String name;
    private final String lastName;
    private final String username;
    private final String email;
}
