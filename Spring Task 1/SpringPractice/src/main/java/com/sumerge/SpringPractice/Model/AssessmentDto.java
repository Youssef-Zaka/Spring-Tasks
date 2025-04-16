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
public class AssessmentDto {
    @Schema(description = "Assessment ID")
    private Long id;

    @Schema(description = "Assessment content")
    private String content;

    @Schema(description = "Course ID")
    private Long courseId;
}
