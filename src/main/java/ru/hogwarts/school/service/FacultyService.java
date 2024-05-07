package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(Long id);

    Faculty editFaculty(Long id,Faculty faculty);

    void deleteFaculty(Long id);
    List<Faculty> filterByColor(String color);

    List<Faculty> filterByColorOrName(String color, String name);

    List<Student> getStudentsByFaculty(Long id);

    List<Faculty> getAll();

    String getMostLongNameFaculty();
}
