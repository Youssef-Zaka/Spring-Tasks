package com.sumerge.task2.Spring.Task2.Config;


import com.sumerge.task2.Spring.Task2.Impl.CheapestCourseRecommender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration("externalRecommenderConfig")
public class CourseRecommenderConfig {

    @Bean
    @Primary
    @Qualifier("cheapestCourseRecommender")
    public CheapestCourseRecommender cheapestCourseRecommender() {
        return new CheapestCourseRecommender();
    }

}
