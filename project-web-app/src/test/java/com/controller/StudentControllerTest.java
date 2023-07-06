package com.controller;

import com.project.controller.StudentController;
import com.project.model.Student;
import com.project.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testStudentEditSave_WithValidStudent_ShouldRedirectToStudentList() {
        Student student = new Student();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(studentService.setStudent(any(Student.class))).thenReturn(student);

        String result = studentController.studentEditSave(student, bindingResult);

        verify(studentService, times(1)).setStudent(any(Student.class));
        assertEquals("redirect:/app/studentList", result);
    }

    @Test
    public void testStudentEditSave_WithInvalidStudent_ShouldReturnEditPage() {
        Student student = new Student();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = studentController.studentEditSave(student, bindingResult);

        verifyNoMoreInteractions(studentService);
        assertEquals("studentEdit", result);
    }

    @Test
    public void testStudentEditDelete_ShouldRedirectToStudentList() {
        Student student = new Student();
        student.setStudentId(1);

        String result = studentController.studentEditDelete(student);

        verify(studentService, times(1)).deleteStudent(student.getStudentId());
        assertEquals("redirect:/app/studentList", result);
    }
}
