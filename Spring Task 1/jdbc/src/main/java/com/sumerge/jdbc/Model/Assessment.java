package com.sumerge.jdbc.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {
    private Long id;
    private String content;
    private Long courseId;
}