package com.muscle.trainings.mapper;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.entity.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExerciseFromDto(ExerciseDto dto, @MappingTarget Exercise entity);
}
