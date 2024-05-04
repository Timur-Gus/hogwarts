package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for upload add student");
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Was invoked method for upload find student");
        return studentRepository.findById(id).orElseThrow(() ->{
                logger.error("Student not found");
            return new NotFoundStudentException();
        });
    }

    @Override
    public Faculty getFacultyByStudent(Long id) {
        logger.info("Was invoked method for upload get faculty by student");
        return findStudent(id).getFaculty();
    }

    @Override
    public List<Student> getAll() {
        logger.info("Was invoked method for upload get all students");
        return studentRepository.findAll();
    }

    @Override
    public Integer getCountStudents() {
        logger.info("Was invoked method for upload get count students");
        return studentRepository.getCountStudents();
    }

    @Override
    public Double getAverageAgeStudents() {
        logger.info("Was invoked method for upload get average age students");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for upload get last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method for upload edit student");
        Student exsitingStudent = findStudent(id);
        exsitingStudent.setAge(student.getAge());
        exsitingStudent.setName(student.getName());
        return studentRepository.save(exsitingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method for upload delete student");
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> filterByAge(int age) {
        logger.info("Was invoked method for upload filter by age");
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> filterByAgeBetween(int min, int max) {
        logger.info("Was invoked method for upload filter by age between");
        return studentRepository.findByAgeBetween(min, max);
    }

}
