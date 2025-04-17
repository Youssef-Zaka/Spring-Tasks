package com.sumerge.SpringPractice.Service;


import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.AuthorMapper;
import com.sumerge.SpringPractice.Model.AuthorDto;
import com.sumerge.SpringPractice.Repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

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
                .collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setBirthdate(dto.getBirthdate());
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorDto getAuthorByEmail(String email) {
        Author author  = authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.toDto(author);
    }
}
