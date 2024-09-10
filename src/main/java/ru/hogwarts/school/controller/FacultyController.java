package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyServiceImpl.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}/studentsByFaculty")
    public List<Student> getStudentsByFaculty(@PathVariable Long id) {
        return facultyServiceImpl.getStudentsByFaculty(id);
    }

    @GetMapping("/filteredByColor")
    public List<Faculty> getFacultyByColor(@RequestParam String color) {
        return facultyServiceImpl.filterByColor(color);
    }

    @GetMapping("/filteredByColorOrName")
    public List<Faculty> getFacultiesByColorOrName(@RequestParam String color,
                                                   @RequestParam String name) {
        return facultyServiceImpl.filterByColorOrName(color, name);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(Long id, @RequestBody Faculty faculty) {
        Faculty faculty1 = facultyServiceImpl.editFaculty(id, faculty);
        if (faculty1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyServiceImpl.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

}

