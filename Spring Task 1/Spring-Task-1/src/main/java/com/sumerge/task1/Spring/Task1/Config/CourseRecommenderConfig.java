package com.sumerge.task1.Spring.Task1.Config;

import com.sumerge.task1.Spring.Task1.Impl.CheapestCourseRecommender;
import com.sumerge.task1.Spring.Task1.Impl.HighRatedCourseRecommender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CourseRecommenderConfig {

    @Bean
    @Primary
    @Qualifier("cheapestCourseRecommender")
    public CheapestCourseRecommender cheapestCourseRecommender() {
        return new CheapestCourseRecommender();
    }

    @Bean
    @Qualifier("highRatedCourseRecommender")
    public HighRatedCourseRecommender highRatedCourseRecommender() {
        return new HighRatedCourseRecommender();
    }

}
