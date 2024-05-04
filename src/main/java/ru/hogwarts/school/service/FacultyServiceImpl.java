package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for upload add faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for upload find faculty");
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty not found");
            return new NotFoundFacultyException();
        });
    }


    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for upload edit faculty");
        Faculty exsitinngFaculty = findFaculty(id);
        exsitinngFaculty.setName(faculty.getName());
        exsitinngFaculty.setColor(faculty.getColor());
        return facultyRepository.save(exsitinngFaculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for upload delete faculty");
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        logger.info("Was invoked method for upload filter by color");
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> filterByColorOrName(String color, String name) {
        logger.info("Was invoked method for upload filter by color or name");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public List<Student> getStudentsByFaculty(Long id) {
        logger.info("Was invoked method for upload get students by faculty");
        return findFaculty(id).getStudents();
    }

    @Override
    public List<Faculty> getAll() {
        logger.info("Was invoked method for upload get all faculty");
        return facultyRepository.findAll();
    }


}
