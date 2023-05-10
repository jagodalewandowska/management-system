package com.project.service;

import java.util.Optional;

import com.project.model.Projekt;
import com.project.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Optional<Student> getStudent(Integer studentId);
    Student setStudent(Student student);
    void deleteStudent(Integer studentId);
    Page<Student> getStudenci(Pageable pageable);
    Page<Student> searchByNazwisko(String nazwisko, Pageable pageable);
    Page<Student> getStudenciPageSort(String sort, String direction);
// token
    Optional<Student> searchByEmail(String email);
}