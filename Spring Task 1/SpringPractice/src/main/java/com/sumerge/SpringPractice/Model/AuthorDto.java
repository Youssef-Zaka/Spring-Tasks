package com.sumerge.SpringPractice.Model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
    @Schema(description = "Author ID")
    private Long id;

    @Schema(description = "Author's full name")
    private String name;

    @Schema(description = "Author's email address")
    private String email;

    @Schema(description = "Author's birthdate")
    private LocalDate birthdate;
}
