package com.muscle.user.entity;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.response.IronUserResponse;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class IronUser {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public IronUserDto dto() {
        return IronUserDto.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .locked(this.locked)
                .enabled(this.enabled)
                .roles(this.roles.stream().map(Role::dto).collect(Collectors.toList()))
                .build();
    }

    public IronUserDto dtoResponse() {
        return IronUserDto.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .locked(this.locked)
                .enabled(this.enabled)
                .roles(this.roles.stream().map(Role::dto).collect(Collectors.toList()))
                .build();
    }

    public IronUserResponse response() {
        return IronUserResponse.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .build();
    }
}
