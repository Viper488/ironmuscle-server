package com.muscle.user.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class IronUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean locked;
    private Boolean enabled;
    private List<GrantedAuthority> authorities;

    public IronUserDetails(IronUserDto ironUserDto) {
        this.id = ironUserDto.getId();
        this.username = ironUserDto.getUsername();
        this.email = ironUserDto.getEmail();
        this.password = ironUserDto.getPassword();
        this.locked = ironUserDto.getLocked();
        this.enabled = ironUserDto.getEnabled();
        this.authorities = ironUserDto.getRoles().stream().map(roleDto -> new SimpleGrantedAuthority(roleDto.getName())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
}
