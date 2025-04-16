package com.sumerge.SpringPractice.Mappers;

import org.mapstruct.Mapper;
import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Model.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);
}


