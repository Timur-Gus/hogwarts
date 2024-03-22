package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("{age}")
    public List<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.filterByAge(age);
    }

    @PostMapping

    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student student1 = studentService.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

}
