package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
    }
    @Override
    public Faculty getFacultyByStudent(Long id){
        return findStudent(id).getFaculty();
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student editStudent(Long id,Student student) {
        Student exsitingStudent = findStudent(id);
        exsitingStudent.setAge(student.getAge());
        exsitingStudent.setName(student.getName());
        return studentRepository.save(exsitingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> filterByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> filterByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

}
