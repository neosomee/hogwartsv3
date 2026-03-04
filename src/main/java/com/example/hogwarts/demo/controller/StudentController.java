package com.example.hogwarts.demo.controller;

import com.example.hogwarts.demo.model.Student;
import com.example.hogwarts.demo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final Object flag = new Object();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //  CRUD

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student updated = studentService.editStudent(student);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    // Queries ages

    @GetMapping("/age/{age}")
    public Collection<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/age/{min}/{max}")
    public Collection<Student> getStudentsByAgeBetween(
            @PathVariable int min,
            @PathVariable int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/count")
    public long getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/a-names")
    public ResponseEntity<List<String>> getNamesStartingWithA() {
        return ResponseEntity.ok(studentService.getNameStudentWithA());
    }

    //  Parallel

    @GetMapping("/print-parallel")
    public void printStudentsParallel() throws InterruptedException {
        List<Student> students = studentService.getAllStudents()
                .stream()
                .toList();

        if (students.size() < 6) {
            System.out.println("Недостаточно студентов");
            return;
        }

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private void printNameSynchronized(String name) {
        synchronized (flag) {
            System.out.println(name);
        }
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() throws InterruptedException {
        List<Student> students = studentService.getAllStudents()
                .stream()
                .toList();

        if (students.size() < 6) {
            System.out.println("Недостаточно студентов");
            return;
        }

        printNameSynchronized(students.get(0).getName());
        printNameSynchronized(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            printNameSynchronized(students.get(2).getName());
            printNameSynchronized(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            printNameSynchronized(students.get(4).getName());
            printNameSynchronized(students.get(5).getName());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}