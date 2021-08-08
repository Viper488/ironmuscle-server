package com.muscle.user.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IronUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Boolean locked;
    private Boolean enabled;
    private Collection<RoleDto> roles = new ArrayList<>();
}
