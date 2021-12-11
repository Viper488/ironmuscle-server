package com.muscle.user.response;


import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserSafeDto {
    private Long id;
    private String username;
    private String email;
}
