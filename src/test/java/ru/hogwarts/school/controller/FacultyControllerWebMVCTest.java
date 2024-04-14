package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static ru.hogwarts.school.constants.TestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWebMVCTest {
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean
    FacultyServiceImpl facultyService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();
    private Faculty faculty;

    @BeforeEach
    void init() {
        faculty = FACULTY_1;
        faculty.setStudents(STUDENTS);
        Optional<Faculty> optionalFaculty = Optional.of(faculty);
        Mockito.when(facultyRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(optionalFaculty);
        Mockito.when(facultyRepository.findAll())
                .thenReturn(List.of(faculty));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_FACULTY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(faculty)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(facultyRepository).save(ArgumentMatchers.any(Faculty.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, PATH_FACULTY + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(faculty)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(facultyRepository).save(ArgumentMatchers.any(Faculty.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, PATH_FACULTY + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(faculty)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(facultyRepository).delete(ArgumentMatchers.any(Faculty.class));
    }
    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_FACULTY + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(faculty)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Факультет 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("color").value("Красный"));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_FACULTY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Факультет 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value("Красный"));
    }

}
