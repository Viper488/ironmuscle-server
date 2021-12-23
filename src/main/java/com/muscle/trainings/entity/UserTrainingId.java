package com.muscle.trainings.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingId implements Serializable {
    private Long ironUser;
    private Long training;
}
