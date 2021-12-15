package com.muscle.user.entity;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.IronUserImageDto;
import com.muscle.user.response.UserResponse;
import com.muscle.user.response.UserSafeDto;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private String icon;

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
                .icon(this.icon)
                .locked(this.locked)
                .enabled(this.enabled)
                .roles(this.roles.stream().map(Role::dto).collect(Collectors.toList()))
                .build();
    }

    public IronUserImageDto dtoImage() {
        byte[] image = null;

        try {
            image = Files.readAllBytes(Paths.get("src/main/resources/images/" + this.icon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return IronUserImageDto.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .icon(image)
                .locked(this.locked)
                .enabled(this.enabled)
                .roles(this.roles.stream().map(Role::dto).collect(Collectors.toList()))
                .build();
    }

    public UserSafeDto safeDto() {
        return UserSafeDto.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .build();
    }
}
