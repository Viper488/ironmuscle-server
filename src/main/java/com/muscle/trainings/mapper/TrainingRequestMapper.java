package com.muscle.trainings.mapper;

import com.muscle.trainings.dto.TrainingRequestDto;
import com.muscle.trainings.entity.TrainingRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TrainingRequestMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTrainingRequestFromDto(TrainingRequestDto dto, @MappingTarget TrainingRequest entity);
}
