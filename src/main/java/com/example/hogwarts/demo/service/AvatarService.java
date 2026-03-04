package com.example.hogwarts.demo.service;

import com.example.hogwarts.demo.model.Avatar;
import com.example.hogwarts.demo.model.Student;
import com.example.hogwarts.demo.repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    private final String avatarsDir = "avatars";

    public AvatarService(StudentService studentService,
                         AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        logger.info("AvatarService initialized");
    }

    public Avatar findByStudentId(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {

        Student student = studentService.findStudent(studentId);
        if (student == null) {
            throw new IllegalStateException("Student not found: " + studentId);
        }

        String extension = getExtension(file.getOriginalFilename());
        Path filePath = Path.of(avatarsDir, studentId + "." + extension);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath)) {
            is.transferTo(os);
        }

        Avatar avatar = avatarRepository
                .findByStudentId(studentId)
                .orElse(new Avatar());

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generatePreview(filePath));

        avatarRepository.save(avatar);
    }

    public byte[] getOriginalFile(Avatar avatar) throws IOException {
        return Files.readAllBytes(Path.of(avatar.getFilePath()));
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "jpg";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }

    private byte[] generatePreview(Path filePath) throws IOException {

        try (InputStream is = Files.newInputStream(filePath)) {

            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                return new byte[0];
            }

            int width = 100;
            int height = (int) (width * (double) image.getHeight() / image.getWidth());

            BufferedImage preview =
                    new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            graphics.dispose();

            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                ImageIO.write(preview, "jpg", os);
                return os.toByteArray();
            }
        }
    }
}