package com.sumerge.SpringPractice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import com.sumerge.SpringPractice.Service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





class AssessmentControllerTest {

    @Mock
    private AssessmentService assessmentService;

    @InjectMocks
    private AssessmentController assessmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assessmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllAssessments_Success() throws Exception {
        // Arrange
        AssessmentDto dto1 = new AssessmentDto();
        AssessmentDto dto2 = new AssessmentDto();
        List<AssessmentDto> assessments = Arrays.asList(dto1, dto2);
        when(assessmentService.getAllAssessments()).thenReturn(assessments);

        // Act & Assert
        mockMvc.perform(get("/api/assessments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        verify(assessmentService, times(1)).getAllAssessments();
    }

    @Test
    void testGetAllAssessments_EmptyList() throws Exception {
        // Arrange
        when(assessmentService.getAllAssessments()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/assessments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(assessmentService, times(1)).getAllAssessments();
    }

    @Test
    void testGetAssessmentById_Success() throws Exception {
        // Arrange
        AssessmentDto dto = new AssessmentDto();
        dto.setId(1L);
        when(assessmentService.getAssessmentById(1L)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/api/assessments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(assessmentService, times(1)).getAssessmentById(1L);
    }

    @Test
    void testCreateAssessment_Success() throws Exception {
        // Arrange
        AssessmentDto dto = new AssessmentDto();
        dto.setId(1L);
        when(assessmentService.createAssessment(any(AssessmentDto.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(assessmentService, times(1)).createAssessment(any(AssessmentDto.class));
    }

    @Test
    void testUpdateAssessment_Success() throws Exception {
        // Arrange
        AssessmentDto dto = new AssessmentDto();
        dto.setId(1L);
        when(assessmentService.updateAssessment(eq(1L), any(AssessmentDto.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(put("/api/assessments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(assessmentService, times(1)).updateAssessment(eq(1L), any(AssessmentDto.class));
    }

    @Test
    void testDeleteAssessment_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/assessments/1"))
                .andExpect(status().isNoContent());
        verify(assessmentService, times(1)).deleteAssessment(1L);
    }
}