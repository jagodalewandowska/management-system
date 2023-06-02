package com.project.service;

import com.project.model.Student;
import com.project.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface TutorService {
    Optional<Tutor> getTutor(Integer tutorId);
    Tutor setTutor(Tutor tutor);
    Page<Tutor> getTutors(Pageable pageable);
    Optional<Tutor> searchByEmail(String email);
}