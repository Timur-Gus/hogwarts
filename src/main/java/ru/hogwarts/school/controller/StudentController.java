package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping()
    public Collection<Student> getAllStudents() {
        return studentServiceImpl.getAll();
    }

    @GetMapping("/count-students")
    public Integer getCountStudents() {
        return studentServiceImpl.getCountStudents();
    }

    @GetMapping("/students/print-parallel")
    public void getNamesStudentsParallel() {
            studentServiceImpl.getNamesStudentsParallel();
    }
    @GetMapping("/students/print-synchronized")
    public void getNamesStudentSynchronized() {
            studentServiceImpl.getNamesStudentsParallel();
    }

    @GetMapping("/average-age-students")
    public Double getAverageAgeStudents() {
        return studentServiceImpl.getAverageAgeStudents();
    }

    @GetMapping("/average-age-students-stream")
    public Double getAverageAgeStudentsStream() {
        return studentServiceImpl.getAverageAgeStudentsStream();
    }

    @GetMapping("/last-five-students")
    public List<Student> getLastFiveStudents() {
        return studentServiceImpl.getLastFiveStudents();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentServiceImpl.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}/faculty-by-student")
    public Faculty getFacultyByStudent(@PathVariable Long id) {
        return studentServiceImpl.getFacultyByStudent(id);
    }

    @GetMapping("/filtered-by-age")
    public List<Student> getStudentsByAge(@RequestParam int age) {

        return studentServiceImpl.filterByAge(age);
    }

    @GetMapping("/filtered-by-age-between")
    public List<Student> getStudentsByAgeBetween(@RequestParam int min,
                                                 @RequestParam int max) {
        return studentServiceImpl.filterByAgeBetween(min, max);
    }

    @GetMapping("/filter-by-name-started-letter-A")
    public List<String> filterByNameStartedLetterA() {
        return studentServiceImpl.filterByNameStartedLetterA();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.addStudent(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> editStudent(@PathVariable Long id, @RequestBody Student student) {
        Student student1 = studentServiceImpl.editStudent(id, student);
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

}
