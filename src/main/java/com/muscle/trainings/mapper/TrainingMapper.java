package com.muscle.trainings.mapper;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.entity.Training;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTrainingFromDto(TrainingDto dto, @MappingTarget Training entity);
}
