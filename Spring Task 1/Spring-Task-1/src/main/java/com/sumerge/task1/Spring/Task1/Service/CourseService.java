package com.sumerge.task1.Spring.Task1.Service;


import com.sumerge.common.Interface.CourseRecommender;
import com.sumerge.common.Model.Course;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    CourseRecommender courseRecommender;

    public CourseService(@Qualifier("cheapestCourseRecommender") CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }


    public List<Course> getRecommendedCourses(){
        return courseRecommender.recommendCourses();
    }
}
