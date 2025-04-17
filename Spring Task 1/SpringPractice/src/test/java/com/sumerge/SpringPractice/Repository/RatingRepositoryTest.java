package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;




@DataJpaTest
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void testSaveAndFindRating() {
        Rating rating = new Rating();
        
        rating = ratingRepository.save(rating);
        Assertions.assertNotNull(rating.getId(), "Saved rating should have an ID.");

        Optional<Rating> foundRating = ratingRepository.findById(rating.getId());
        Assertions.assertTrue(foundRating.isPresent(), "Rating should be found by its ID.");
        Assertions.assertEquals(rating.getId(), foundRating.get().getId(), "IDs should match.");
    }

    @Test
    public void testDeleteRating() {
        Rating rating = new Rating();
        
        rating = ratingRepository.save(rating);
        Long ratingId = rating.getId();
        Assertions.assertNotNull(ratingId, "Rating ID should not be null after saving.");

        ratingRepository.delete(rating);
        Optional<Rating> deletedRating = ratingRepository.findById(ratingId);
        Assertions.assertFalse(deletedRating.isPresent(), "Rating should not be found after deletion.");
    }
}