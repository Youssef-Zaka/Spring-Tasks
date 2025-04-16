package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Model.CourseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class })
public interface CourseMapper {

    @Mapping(source = "author.id", target = "authorId")
    CourseDto toDto(Course course);

    @Mapping(source = "authorId", target = "author.id")
    Course toEntity(CourseDto dto);
}