package com.sumerge.SpringPractice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.SpringPractice.Model.AuthorDto;
import com.sumerge.SpringPractice.Service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AuthorDto authorDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();

        authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName("John Doe");
        authorDto.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllAuthors() throws Exception {
        List<AuthorDto> authors = Collections.singletonList(authorDto);
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(authors.size()));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(authorDto);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.name").value(authorDto.getName()))
                .andExpect(jsonPath("$.email").value(authorDto.getEmail()));

        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void testCreateAuthor() throws Exception {
        when(authorService.createAuthor(any(AuthorDto.class))).thenReturn(authorDto);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.name").value(authorDto.getName()))
                .andExpect(jsonPath("$.email").value(authorDto.getEmail()));

        verify(authorService, times(1)).createAuthor(any(AuthorDto.class));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        authorDto.setName("Updated Name");
        when(authorService.updateAuthor(eq(1L), any(AuthorDto.class))).thenReturn(authorDto);

        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.name").value(authorDto.getName()))
                .andExpect(jsonPath("$.email").value(authorDto.getEmail()));

        verify(authorService, times(1)).updateAuthor(eq(1L), any(AuthorDto.class));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteAuthor(1L);
    }

    @Test
    void testGetAuthorByEmail() throws Exception {
        when(authorService.getAuthorByEmail("john.doe@example.com")).thenReturn(authorDto);

        mockMvc.perform(get("/api/authors/email/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.name").value(authorDto.getName()))
                .andExpect(jsonPath("$.email").value(authorDto.getEmail()));

        verify(authorService, times(1)).getAuthorByEmail("john.doe@example.com");
    }
}