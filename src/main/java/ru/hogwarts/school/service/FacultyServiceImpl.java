package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(NotFoundFacultyException::new);
    }


    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty exsitinngFaculty = findFaculty(id);
        exsitinngFaculty.setName(faculty.getName());
        exsitinngFaculty.setColor(faculty.getColor());
        return facultyRepository.save(exsitinngFaculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> filterByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
    @Override
    public List<Student> getStudentsByFaculty(Long id){
        return findFaculty(id).getStudents();
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }


}
