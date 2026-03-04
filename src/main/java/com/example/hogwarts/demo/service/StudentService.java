package com.example.hogwarts.demo.service;

import com.example.hogwarts.demo.model.Faculty;
import com.example.hogwarts.demo.model.Student;
import com.example.hogwarts.demo.repository.FacultyRepository;
import com.example.hogwarts.demo.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    @Transactional
    public Student createStudent(Student student) {

        student.setId(null);

        if (student.getFaculty() != null) {

            String facultyName = student.getFaculty().getName();
            String facultyColor = student.getFaculty().getColor();

            if (facultyName == null || facultyName.isBlank()) {
                throw new IllegalArgumentException("Faculty name must be provided");
            }

            Faculty faculty = facultyRepository
                    .findByNameIgnoreCase(facultyName)
                    .orElseGet(() -> {
                        Faculty newFaculty = new Faculty();
                        newFaculty.setName(facultyName);
                        newFaculty.setColor(facultyColor);
                        return facultyRepository.save(newFaculty);
                    });

            student.setFaculty(faculty);
        }

        return studentRepository.save(student);
    }


    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }


    public Student editStudent(Student student) {

        if (student.getId() == null ||
                !studentRepository.existsById(student.getId())) {
            return null;
        }

        if (student.getFaculty() != null &&
                student.getFaculty().getId() != null) {

            Faculty faculty = facultyRepository.findById(
                    student.getFaculty().getId()
            ).orElseThrow(() ->
                    new IllegalArgumentException("Faculty not found"));

            student.setFaculty(faculty);
        }

        return studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
    }


    public long getStudentsCount() {
        return studentRepository.getStudentsCount();
    }

    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.findTop5ByOrderByIdDesc(PageRequest.of(0, 5));
    }

    public List<String> getNameStudentWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}