package com.project.service;

import java.util.Optional;

import com.project.model.Projekt;
import com.project.model.Student;
import com.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudent(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public Student setStudent(Student student) {
        Student studentToSave= null;
        if(student.getStudentId()!=null) {
            studentToSave = student;
        }else {
            studentToSave = new Student (student.getImie(), student.getNazwisko(), student.getNrIndeksu(), student.getEmail(), student.getStacjonarny());
        }
        return studentRepository.save(studentToSave);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public Page<Student> getStudenci(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
        return studentRepository.findByNazwiskoStartsWithIgnoreCase(nazwisko, pageable);
    }

    @Override
    public Page <Student> getStudenciPageSort(String sort, String direction) {
        Pageable pageable = null;
        direction = direction.toUpperCase();
        if (sort != null) {
            if (direction.equals("ASC")) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, sort);
            }
            if (direction.equals("DESC")) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, sort);
            }
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE);
        }
        return studentRepository.findAll(pageable);
    }
}