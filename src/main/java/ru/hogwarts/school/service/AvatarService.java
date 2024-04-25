package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;
    Avatar findStudentAvatar(Long studentId);
    byte[] generateImagePreview(Path filePath);
    String getExtension(String fileName);

    List<Avatar> findAllStudentsAvatar(Integer pageNumber,Integer pageSize);
}
