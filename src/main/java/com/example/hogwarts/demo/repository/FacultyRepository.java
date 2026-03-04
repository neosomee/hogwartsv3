package com.example.hogwarts.demo.repository;

import com.example.hogwarts.demo.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorIgnoreCase(String color);

    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);

    Optional<Faculty> findByNameIgnoreCase(String name);
}