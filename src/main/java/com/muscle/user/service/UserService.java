package com.muscle.user.service;

import com.muscle.user.entity.IronUser;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;

import java.util.List;

public interface UserService{
    IronUserDto saveUser(IronUserDto ironUserDto);
    RoleDto saveRole(RoleDto roleDto);
    void addRoleToUser(String username, String roleName);
    IronUserDto getUser(String username);
    List<IronUserDto> getUsers();
    String signUpUser(IronUser ironUser);
}
