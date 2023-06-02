package com.project.repository;

import com.project.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findByEmail(String email);
}