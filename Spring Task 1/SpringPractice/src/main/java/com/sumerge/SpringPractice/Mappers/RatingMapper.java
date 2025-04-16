package com.sumerge.SpringPractice.Mappers;


import com.sumerge.SpringPractice.Entity.Rating;
import com.sumerge.SpringPractice.Model.RatingDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "course.id", target = "courseId")
    RatingDto toDto(Rating rating);

    @Mapping(source = "courseId", target = "course.id")
    Rating toEntity(RatingDto dto);
}
