package com.muscle.user.dto;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IronUserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String icon;
    private Boolean locked;
    private Boolean enabled;
    private List<RoleDto> roles;
}
