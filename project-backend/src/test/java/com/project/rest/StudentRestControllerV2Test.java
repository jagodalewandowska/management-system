package com.project.rest;

import com.project.controller.StudentRestController;
import com.project.model.Student;
import com.project.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudentRestControllerV2Test {

    @Mock
    private StudentService studentService;

    private StudentRestController studentRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentRestController = new StudentRestController(studentService);
    }

    @Test
    public void testUpdateStudent() {
        Integer studentId = 1;
        Student student = new Student();
        student.setStudentId(studentId);

        when(studentService.getStudent(studentId)).thenReturn(Optional.of(student));
        when(studentService.setStudent(any(Student.class))).thenReturn(student);

        ResponseEntity<Void> response = studentRestController.updateStudent(student, studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteStudent() {
        Integer studentId = 1;
        Student student = new Student();
        student.setStudentId(studentId);

        when(studentService.getStudent(studentId)).thenReturn(Optional.of(student));

        ResponseEntity<Void> response = studentRestController.deleteStudent(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
