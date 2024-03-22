package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long index =0;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++index);
       faculties.put(index,faculty);
        return faculty;
    }

    public Faculty findFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        return null;
    }
    public List<Faculty> filterByColor(String color) {
        return faculties.values()
                .stream()
                .filter(s -> s.getColor().equals(color))
                .toList();
    }

}
