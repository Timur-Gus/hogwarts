package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Long id,Student student);

    void deleteStudent(Long id);

    List<Student> filterByAge(int age);

    List<Student> filterByAgeBetween(int min, int max);

    Faculty getFacultyByStudent(Long id);

    List<Student> getAll();

    Integer getCountStudents();

    Double getAverageAgeStudents();

    List<Student> getLastFiveStudents();
}
