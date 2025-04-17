package com.sumerge.SpringPractice.Mappers;


import com.sumerge.SpringPractice.Entity.Assessment;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    @Mapping(source = "course.id", target = "courseId")
    AssessmentDto toDto(Assessment assessment);

    @Mapping(source = "courseId", target = "course.id")
    Assessment toEntity(AssessmentDto dto);
}