package com.muscle.user.service.impl;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.service.ConverterService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ConverterServiceImpl implements ConverterService {
    @Override
    public IronUser convertDtoToUser(IronUserDto ironUserDto) {
        return IronUser.builder()
                .username(ironUserDto.getUsername())
                .email(ironUserDto.getEmail())
                .password(ironUserDto.getPassword())
                .locked(ironUserDto.getLocked())
                .enabled(ironUserDto.getEnabled())
                .roles(ironUserDto.getRoles()
                        .stream()
                        .map(roleDto -> Role.builder()
                                .id(roleDto.getId())
                                .name(roleDto.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Role convertDtoToRole(RoleDto roleDto) {
        return Role.builder()
                .name(roleDto.getName())
                .build();
    }
}
