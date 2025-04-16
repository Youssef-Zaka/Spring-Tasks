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
public class RatingDto {
    @Schema(description = "Rating ID")
    private Long id;

    @Schema(description = "Rating value")
    private int number;

    @Schema(description = "Course ID")
    private Long courseId;
}
