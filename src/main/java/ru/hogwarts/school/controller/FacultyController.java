package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.filterByColor(color);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyService.editFaculty(faculty);
        if (faculty1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.deleteFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}

