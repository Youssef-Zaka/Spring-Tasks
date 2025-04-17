package com.sumerge.SpringPractice.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.SpringPractice.Model.CourseDto;
import com.sumerge.SpringPractice.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;





class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllCourses() throws Exception {
        CourseDto course1 = new CourseDto();
        course1.setId(1L);
        course1.setName("Course 1");

        CourseDto course2 = new CourseDto();
        course2.setId(2L);
        course2.setName("Course 2");

        Page<CourseDto> page = new PageImpl<>(Arrays.asList(course1, course2));
        when(courseService.getAllCourses(PageRequest.of(0, 10, Sort.by("id").ascending()))).thenReturn(page);

        mockMvc.perform(get("/api/courses")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk());


        verify(courseService, times(1)).getAllCourses(any());
    }

    @Test
    void testGetCourseById() throws Exception {
        CourseDto course = new CourseDto();
        course.setId(1L);
        course.setName("Course 1");

        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course 1"));

        verify(courseService, times(1)).getCourseById(1L);
    }

    @Test
    void testCreateCourse() throws Exception {
        CourseDto course = new CourseDto();
        course.setName("New Course");

        CourseDto createdCourse = new CourseDto();
        createdCourse.setId(1L);
        createdCourse.setName("New Course");

        when(courseService.createCourse(any(CourseDto.class))).thenReturn(createdCourse);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Course"));

        ArgumentCaptor<CourseDto> captor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService, times(1)).createCourse(captor.capture());
        assertEquals("New Course", captor.getValue().getName());
    }

    @Test
    void testUpdateCourse() throws Exception {
        CourseDto course = new CourseDto();
        course.setName("Updated Course");

        CourseDto updatedCourse = new CourseDto();
        updatedCourse.setId(1L);
        updatedCourse.setName("Updated Course");

        when(courseService.updateCourse(eq(1L), any(CourseDto.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Course"));

        ArgumentCaptor<CourseDto> captor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService, times(1)).updateCourse(eq(1L), captor.capture());
        assertEquals("Updated Course", captor.getValue().getName());
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());

        verify(courseService, times(1)).deleteCourse(1L);
    }
    @Test
    void testParseSortSingleFieldAscending() throws Exception {
        String[] sort = {"name,asc"};
        Sort.Order[] orders = courseController.parseSort(sort);

        assertEquals(1, orders.length);
        assertEquals("name", orders[0].getProperty());
        assertEquals(Sort.Direction.ASC, orders[0].getDirection());
    }

    @Test
    void testParseSortSingleFieldDescending() throws Exception {
        String[] sort = {"credit,desc"};
        Sort.Order[] orders = courseController.parseSort(sort);

        assertEquals(1, orders.length);
        assertEquals("credit", orders[0].getProperty());
        assertEquals(Sort.Direction.DESC, orders[0].getDirection());
    }

    @Test
    void testParseSortMultipleFields() throws Exception {
        String[] sort = {"name,asc", "credit,desc"};
        Sort.Order[] orders = courseController.parseSort(sort);

        assertEquals(2, orders.length);
        assertEquals("name", orders[0].getProperty());
        assertEquals(Sort.Direction.ASC, orders[0].getDirection());
        assertEquals("credit", orders[1].getProperty());
        assertEquals(Sort.Direction.DESC, orders[1].getDirection());
    }

    @Test
    void testParseSortDefaultDirection() throws Exception {
        String[] sort = {"name"};
        Sort.Order[] orders = courseController.parseSort(sort);

        assertEquals(1, orders.length);
        assertEquals("name", orders[0].getProperty());
        assertEquals(Sort.Direction.ASC, orders[0].getDirection());
    }

    @Test
    void testParseSortInvalidDirection() throws Exception {
        String[] sort = {"name,invalid"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseController.parseSort(sort));
        assertEquals("Invalid value 'invalid' for orders given; Has to be either 'desc' or 'asc' (case insensitive)", exception.getMessage());
    }

}