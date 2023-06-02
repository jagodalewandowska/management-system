package com.project.service;

import com.project.model.Student;
import com.project.model.Tutor;
import com.project.repository.StudentRepository;
import com.project.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TutorServiceImpl implements TutorService {

    private TutorRepository tutorRepository;

    @Autowired
    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Optional<Tutor> getTutor(Integer tutorId) {
        return tutorRepository.findById(tutorId);
    }

    @Override
    public Tutor setTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    @Override
    public Page<Tutor> getTutors(Pageable pageable) {
        return tutorRepository.findAll(pageable);
    }

    @Override
    public Optional<Tutor> searchByEmail(String email) {
        return tutorRepository.findByEmail(email);
    }

}