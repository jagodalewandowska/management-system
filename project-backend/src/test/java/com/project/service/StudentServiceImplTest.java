package com.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.project.model.Student;
import com.project.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    void testGetStudent_WithValidId() {
        // Testuje, czy poprawnie zwracany jest student na podstawie poprawnego ID
        Integer studentId = 1;
        Student expectedStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));
        Optional<Student> result = studentService.getStudent(studentId);
        assertTrue(result.isPresent());
        assertEquals(expectedStudent, result.get());
        verify(studentRepository).findById(studentId);
    }

    @Test
    void testGetStudent_WithInvalidId() {
        // Testuje, czy dla niepoprawnego ID zwracany jest pusty Optional
        Integer studentId = 1;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        Optional<Student> result = studentService.getStudent(studentId);
        assertFalse(result.isPresent());
        verify(studentRepository).findById(studentId);
    }

    @Test
    void testSetStudent() {
        // Testuje, czy istniejący student jest zapisywany i zwracany poprawnie
        Student existingStudent = new Student();
        existingStudent.setStudentId(1);
        Student updatedStudent = new Student();
        updatedStudent.setStudentId(1);
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);
        Student result = studentService.setStudent(updatedStudent);
        assertEquals(updatedStudent, result);
        verify(studentRepository).save(updatedStudent);
    }

    @Test
    void testDeleteStudent() {
        // Testuje, czy student o poprawnym ID jest usuwany
        Integer studentId = 1;
        studentService.deleteStudent(studentId);
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void testGetStudenci() {
        // Testuje, czy poprawnie zwracana jest strona studentów
        Page<Student> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(studentRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<Student> result = studentService.getStudenci(pageable);
        assertEquals(expectedPage, result);
        verify(studentRepository).findAll(pageable);
    }

    @Test
    void testSearchByNazwisko() {
        // Testuje, czy poprawnie zwracana jest strona studentów na podstawie nazwiska
        String nazwisko = "Kowalski";
        Page<Student> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(studentRepository.findByNazwiskoStartsWithIgnoreCase(nazwisko, pageable)).thenReturn(expectedPage);
        Page<Student> result = studentService.searchByNazwisko(nazwisko, pageable);
        assertEquals(expectedPage, result);
        verify(studentRepository).findByNazwiskoStartsWithIgnoreCase(nazwisko, pageable);
    }

}

