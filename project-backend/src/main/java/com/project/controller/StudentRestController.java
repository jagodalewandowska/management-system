package com.project.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import com.project.model.Student;
import com.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private StudentService studentService;
    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService= studentService;
    }

    @GetMapping("/studenci/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {
        return ResponseEntity.of(studentService.getStudent(studentId));
    }

    @PostMapping(path = "/studenci")
    ResponseEntity<Void> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.setStudent(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(createdStudent.getStudentId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student, @PathVariable Integer studentId) {
        return studentService.getStudent(studentId).map(p -> {
            studentService.setStudent(student);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/studenci/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId).map(p -> {
            studentService.deleteStudent(studentId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping(value = "/studenci")
    Page<Student> getStudent(Pageable pageable) {
        return studentService.getStudenci(pageable);
    }

    @GetMapping(value = "/studenci", params = "nazwisko")
    Page<Student> getStudenciByNazwisko(@RequestParam String nazwisko, Pageable pageable) {
        return studentService.searchByNazwisko(nazwisko, pageable);
    }

    @GetMapping("/studenci/sort={sort}/order={order}")
    public List <Student> getSortedStudenci(@PathVariable String sort, @PathVariable String order) {
        Page <Student> data = studentService.getStudenciPageSort(sort, order);
        return data.getContent();
    }
}