package com.sumerge.SpringPractice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.SpringPractice.Model.RatingDto;
import com.sumerge.SpringPractice.Service.RatingService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;





class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllRatings_ReturnsEmptyList() throws Exception {
        // Arrange
        when(ratingService.getAllRatings()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(ratingService, times(1)).getAllRatings();
    }

    @Test
    void testGetAllRatings_ReturnsNonEmptyList() throws Exception {
        // Arrange
        RatingDto rating1 = new RatingDto();
        rating1.setCourseId(1L);
        rating1.setNumber(5);

        RatingDto rating2 = new RatingDto();
        rating2.setCourseId(2L);
        rating2.setNumber(4);

        List<RatingDto> ratings = Arrays.asList(rating1, rating2);
        when(ratingService.getAllRatings()).thenReturn(ratings);

        // Act & Assert
        mockMvc.perform(get("/api/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(ratingService, times(1)).getAllRatings();
    }

    @Test
    void testGetRatingById_Success() throws Exception {
        // Arrange
        RatingDto rating = new RatingDto();
        rating.setCourseId(1L);
        rating.setNumber(5);

        when(ratingService.getRatingById(1L)).thenReturn(rating);

        // Act & Assert
        mockMvc.perform(get("/api/ratings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1L))
                .andExpect(jsonPath("$.number").value(5));

        verify(ratingService, times(1)).getRatingById(1L);
    }

    @Test
    void testCreateRating_Success() throws Exception {
        // Arrange
        RatingDto rating = new RatingDto();
        rating.setCourseId(1L);
        rating.setNumber(5);

        when(ratingService.createRating(any(RatingDto.class))).thenReturn(rating);

        // Act & Assert
        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1L))
                .andExpect(jsonPath("$.number").value(5));

        verify(ratingService, times(1)).createRating(any(RatingDto.class));
    }

    @Test
    void testUpdateRating_Success() throws Exception {
        // Arrange
        RatingDto rating = new RatingDto();
        rating.setCourseId(1L);
        rating.setNumber(10);

        when(ratingService.updateRating(eq(1L), any(RatingDto.class))).thenReturn(rating);

        // Act & Assert
        mockMvc.perform(put("/api/ratings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1L))
                .andExpect(jsonPath("$.number").value(10));

        verify(ratingService, times(1)).updateRating(eq(1L), any(RatingDto.class));
    }

    @Test
    void testDeleteRating_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/ratings/1"))
                .andExpect(status().isNoContent());

        verify(ratingService, times(1)).deleteRating(1L);
    }
}