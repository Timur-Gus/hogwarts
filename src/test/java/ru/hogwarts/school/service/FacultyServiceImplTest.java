package ru.hogwarts.school.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constants.TestConstants.*;



@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Inject
    FacultyServiceImpl facultyService;
    @Mock
    FacultyRepository facultyRepository;
    @Test
    void addFaculty() {
    }

    @Test
    void findFaculty() {
    }

    @Test
    void editFaculty() {
    }

    @Test
    void deleteFaculty() {
    }

    @Test
    void filterByColor() {
    }
}