package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}