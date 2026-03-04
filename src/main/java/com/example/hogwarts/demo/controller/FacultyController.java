package com.example.hogwarts.demo.controller;


import com.example.hogwarts.demo.model.Faculty;
import com.example.hogwarts.demo.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        return faculty == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(faculty);
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAllFaculty();
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updated = facultyService.editFaculty(faculty);
        return updated == null
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public Collection<Faculty> byColor(@PathVariable String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/search")
    public List<Faculty> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {
        return facultyService.search(name, color);
    }

    @GetMapping("/longest-name")
    public String longestName() {
        return facultyService.getLongestFacultyName();
    }
}
