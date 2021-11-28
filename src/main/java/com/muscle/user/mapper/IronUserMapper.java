package com.muscle.user.mapper;

import com.muscle.user.dto.ChangeUserDetails;
import com.muscle.user.entity.IronUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IronUserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(ChangeUserDetails dto, @MappingTarget IronUser entity);
}
