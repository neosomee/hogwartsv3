package com.example.hogwarts.demo.controller;

import com.example.hogwarts.demo.model.Avatar;
import com.example.hogwarts.demo.service.AvatarService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    //  Upload

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadAvatar(
            @PathVariable Long studentId,
            @RequestParam MultipartFile file) throws Exception {

        if (file.isEmpty() || file.getSize() > 300 * 1024) {
            return ResponseEntity.badRequest().build();
        }

        avatarService.uploadAvatar(studentId, file);
        return ResponseEntity.ok().build();
    }

    //  Original file

    @GetMapping("/{studentId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long studentId) throws Exception {

        Avatar avatar = avatarService.findByStudentId(studentId);
        if (avatar == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] file = avatarService.getOriginalFile(avatar);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(file.length)
                .body(file);
    }

    //  Preview

    @GetMapping("/{studentId}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable Long studentId) {

        Avatar avatar = avatarService.findByStudentId(studentId);
        if (avatar == null || avatar.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(avatar.getData().length)
                .body(avatar.getData());
    }
}