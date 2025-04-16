package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}