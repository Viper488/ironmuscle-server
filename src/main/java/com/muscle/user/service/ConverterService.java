package com.muscle.user.service;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.repository.IronUser;
import com.muscle.user.repository.Role;

public interface ConverterService {
    IronUser convertDtoToUser(IronUserDto ironUserDto);
    Role convertDtoToRole(RoleDto roleDto);
}
