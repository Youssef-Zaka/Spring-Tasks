package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Model.AuthorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);
}


