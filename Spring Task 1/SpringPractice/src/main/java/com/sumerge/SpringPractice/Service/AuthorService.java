package com.sumerge.SpringPractice.Service;


import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.AuthorMapper;
import com.sumerge.SpringPractice.Model.AuthorDto;
import com.sumerge.SpringPractice.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    //constant for error message
    private static final String AUTHOR_NOT_FOUND = "Author not found";

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorDto createAuthor(AuthorDto dto) {
        Author author = authorMapper.toEntity(dto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND));
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setBirthdate(dto.getBirthdate());
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorDto getAuthorByEmail(String email) {
        Author author  = authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND));
        return authorMapper.toDto(author);
    }
}
