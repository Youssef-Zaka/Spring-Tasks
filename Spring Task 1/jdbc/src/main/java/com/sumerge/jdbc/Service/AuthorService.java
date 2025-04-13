package com.sumerge.jdbc.Service;

import com.sumerge.jdbc.Mappers.AuthorRowMapper;
import com.sumerge.jdbc.Model.Author;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthorService {

    private final NamedParameterJdbcTemplate jdbc;

    public AuthorService(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO author (name, email, birthdate) VALUES (:name, :email, :birthdate)";
        Map<String, Object> params = Map.of(
                "name", author.getName(),
                "email", author.getEmail(),
                "birthdate", author.getBirthdate()
        );
        jdbc.update(sql, params);
    }

    public Author getAuthorByName(String name) {
        String sql = "SELECT * FROM author WHERE name = :name";
        return jdbc.queryForObject(sql, Map.of("name", name), new AuthorRowMapper());
    }

    public void updateAuthor(Author author) {
        String sql = "UPDATE author SET name = :name, email = :email, birthdate = :birthdate WHERE id = :id";
        Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName(),
                "email", author.getEmail(),
                "birthdate", author.getBirthdate()
        );
        jdbc.update(sql, params);
    }

    public void deleteAuthor(Long id) {
        jdbc.update("DELETE FROM author WHERE id = :id", Map.of("id", id));
    }
}