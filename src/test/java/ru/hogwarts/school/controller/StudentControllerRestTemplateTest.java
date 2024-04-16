package ru.hogwarts.school.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static ru.hogwarts.school.constants.TestConstants.*;

@SpringBootTest(classes = SchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    Faculty faculty;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        Faculty faculty = FACULTY_1;
        ResponseEntity<Faculty> response = restTemplate.postForEntity(PATH_FACULTY, faculty, Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        this.faculty = response.getBody();
    }
    @Test
    void crud() {

        //create
        Student body = createStudent(STUDENT_1);
        checkStudent(body,STUDENT_1.getName(),STUDENT_1.getAge());

        //read
        ResponseEntity<Student> response = restTemplate.getForEntity(PATH_STUDENT + "/" + body.getId(), Student.class);
        body = response.getBody();
        checkStudent(body,STUDENT_1.getName(),STUDENT_1.getAge());

        //update
        body.setAge(35);
        response = restTemplate.exchange(
                PATH_STUDENT + "/" + body.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        response = restTemplate.getForEntity(PATH_STUDENT + "/" + body.getId(), Student.class);
        body = response.getBody();
        checkStudent(body,STUDENT_1.getName(),35);

        //delete
        restTemplate.delete(PATH_STUDENT + "/" + body.getId());
        response = restTemplate.getForEntity(PATH_STUDENT + "/" + body.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void filters() throws JsonProcessingException {
        Student student1 = createStudent(STUDENT_1);
        Student student2 = createStudent(STUDENT_2);
        Student student3 = createStudent(STUDENT_3);

        //getAll
        ResponseEntity<ArrayList> response = restTemplate.getForEntity(PATH_STUDENT, ArrayList.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);

        //filteredByAge
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(
                PATH_STUDENT + "/filteredByAge?age=15", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        String json = jsonResponse.getBody();
        ArrayList<Student> students = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(students.get(0).getId()).isEqualTo(student2.getId());

        //filteredByAgeBetween
        jsonResponse = restTemplate.getForEntity(
                PATH_STUDENT + "/filteredByAgeBetween?min=13&max=16",
                String.class);
        assertThat(jsonResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(jsonResponse.getBody()).isNotNull();
        json = jsonResponse.getBody();
        students = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(students.size()).isEqualTo(2);

        //facultyByStudent
        ResponseEntity<Faculty> responseFaculty = restTemplate.getForEntity(
                PATH_STUDENT + "/" + student1.getId() + "/facultyByStudent",
                Faculty.class);
        assertThat(responseFaculty.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(responseFaculty.getBody()).isNotNull();
        assertThat(faculty.getId()).isEqualTo(responseFaculty.getBody().getId());


    }

    private Student createStudent(Student student) {
        student.setFaculty(faculty);
        ResponseEntity<Student> response = restTemplate.postForEntity(PATH_STUDENT, student, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody();
    }

    private void checkStudent(Student student, String name, int age) {
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getAge()).isEqualTo(age);
    }
}
