package ru.hogwarts.school.repository;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {


    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Integer getCountStudents();

    @Query(value = "SELECT * from  student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

    @Query(value = "SELECT AVG(age) from student", nativeQuery = true)
    Double getAverageAge();
}
