package com.muscle.user.repository;

import com.muscle.user.dto.IronUserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class IronUser implements UserDetails {

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
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role:roles
             ) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public IronUserDto dto() {
        return IronUserDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .locked(this.locked)
                .enabled(this.enabled)
                .roles(this.roles.stream().map(Role::dto).collect(Collectors.toList()))
                .build();
    }
}
