package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Assessment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;




@DataJpaTest
 class AssessmentRepositoryTest {

    @Autowired
    private AssessmentRepository repository;

    @Test
     void testSaveAssessment() {
        // Create a new Assessment instance.
        Assessment assessment = new Assessment();

        // Save the assessment and verify it's saved correctly.
        Assessment saved = repository.save(assessment);
        assertNotNull(saved, "Saved assessment should not be null");
        assertNotNull(saved.getId(), "Saved assessment should have an auto-generated ID");
    }

    @Test
     void testFindAllAssessments() {
        // Save two new Assessments.
        Assessment assessment1 = new Assessment();
        Assessment assessment2 = new Assessment();
        repository.save(assessment1);
        repository.save(assessment2);

        // Verify that the repository returns both saved assessments.
        long count = repository.count();
        assertEquals(2, count, "Repository should contain 2 assessments");
    }
}