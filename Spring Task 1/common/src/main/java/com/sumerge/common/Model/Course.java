package com.sumerge.common.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private String courseName;
    private String courseDescription;
    private String courseInstructor;
}
