package com.example.hogwarts.demo.service;

import com.example.hogwarts.demo.model.Faculty;
import com.example.hogwarts.demo.repository.FacultyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(null);   // защита от merge
        return facultyRepository.save(faculty);
    }

    @Transactional(readOnly = true)
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculty.getId() == null || !facultyRepository.existsById(faculty.getId())) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    @Transactional(readOnly = true)
    public List<Faculty> search(String name, String color) {
        return facultyRepository
                .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(
                        name == null ? "" : name,
                        color == null ? "" : color
                );
    }

    @Transactional(readOnly = true)
    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}