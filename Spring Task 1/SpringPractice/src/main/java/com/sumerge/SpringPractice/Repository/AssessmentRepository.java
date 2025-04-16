package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}