package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.constants.TestConstants.*;

@SpringBootTest(classes = SchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void crud() {
        //create
        Faculty body = createFaculty(STUDENT_1,FACULTY_1);
        checkFaculty(body, FACULTY_1.getName(), FACULTY_1.getColor());

        //read
        ResponseEntity<Faculty> response = restTemplate.getForEntity(PATH_FACULTY + "/" + body.getId(), Faculty.class);
        body = response.getBody();
        checkFaculty(body, FACULTY_1.getName(), FACULTY_1.getColor());

        //update
        body.setColor("Белый");
        response = restTemplate.exchange(
                PATH_FACULTY + "/" + body.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        response = restTemplate.getForEntity(PATH_FACULTY + "/" + body.getId(), Faculty.class);
        body = response.getBody();
        checkFaculty(body, FACULTY_1.getName(), "Белый");

        //delete
        restTemplate.delete(PATH_FACULTY + "/" + body.getId());
        response = restTemplate.getForEntity(PATH_FACULTY + "/" + body.getId(), Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void filters() throws JsonProcessingException {
        Faculty faculty1 = createFaculty(STUDENT_1,FACULTY_1);
        Faculty faculty2 = createFaculty(STUDENT_2,FACULTY_2);
        Faculty faculty3 = createFaculty(STUDENT_3,FACULTY_3);

        //getAll
        ResponseEntity<ArrayList> response = restTemplate.getForEntity(PATH_FACULTY, ArrayList.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);

        //filteredByColor
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(
                PATH_FACULTY + "/filteredByColor?color=Синий", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        String json = jsonResponse.getBody();
        ArrayList<Faculty> faculties = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(faculties.get(0).getId()).isEqualTo(faculty2.getId());

        //filteredByNameOrColor
        jsonResponse = restTemplate.getForEntity(
                PATH_FACULTY + "/filteredByColorOrName?color=Синий&name=Факультет 1",
                String.class);
        assertThat(jsonResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(jsonResponse.getBody()).isNotNull();
        json = jsonResponse.getBody();
        faculties = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(faculties.size()).isEqualTo(2);

        //studentsByFaculty
        jsonResponse = restTemplate.getForEntity(
                PATH_FACULTY + "/" + faculty1.getId() + "/studentsByFaculty",
                String.class);
        assertThat(jsonResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(jsonResponse.getBody()).isNotNull();
        json = jsonResponse.getBody();
        ArrayList<Student> studentsByFaculty = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(studentsByFaculty.size()).isEqualTo(1);

    }

    private Faculty createFaculty(Student student, Faculty faculty) {
        ResponseEntity<Faculty> response = restTemplate.postForEntity(PATH_FACULTY, faculty, Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        createStudent(student, faculty);
        return response.getBody();
    }

    private void checkFaculty(Faculty faculty, String name, String color) {
        assertThat(faculty).isNotNull();
        assertThat(faculty.getId()).isNotNull();
        assertThat(faculty.getName()).isEqualTo(name);
        assertThat(faculty.getColor()).isEqualTo(color);
    }
    private Student createStudent(Student student, Faculty faculty) {
        student.setFaculty(faculty);
        ResponseEntity<Student> response = restTemplate.postForEntity(PATH_STUDENT, student, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody();
    }

}
