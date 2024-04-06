package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {
    public static final Student STUDENT_1 = new Student(1L, "Студент 1", 14);
    public static final Student STUDENT_2 = new Student(2L, "Студент 2", 15);
    public static final Student STUDENT_3 = new Student(3L, "Студент 3", 14);
    public static final Faculty FACULTY_1 = new Faculty(1L, "Факультет 1", "Красный");
    public static final Faculty FACULTY_2 = new Faculty(2L, "Факультет 2", "Синий");
    public static final Faculty FACULTY_3 = new Faculty(3L, "Факультет 3", "Красный");
    public static final List<Student> STUDENTS_BY_AGE = new ArrayList<>(List.of(
            STUDENT_1,STUDENT_3
    ));
    public static final List<Faculty> FACULTIES_BY_AGE = new ArrayList<>(List.of(
            FACULTY_1,FACULTY_3
    ));
}
