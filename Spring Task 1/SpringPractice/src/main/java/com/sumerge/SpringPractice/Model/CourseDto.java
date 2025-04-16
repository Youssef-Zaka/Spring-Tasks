package com.sumerge.SpringPractice.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    @Schema(description = "Course ID")
    private Long id;

    @Schema(description = "Course title")
    private String name;

    @Schema(description = "Course description")
    private String description;

    @Schema(description = "Number of credits")
    private int credit;

    @Schema(description = "Author ID")
    private Long authorId;
}

