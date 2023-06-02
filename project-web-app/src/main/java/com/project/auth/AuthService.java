package com.project.auth;

import com.project.model.Student;
import com.project.model.Tutor;

import java.util.Optional;

public interface AuthService {
    Optional<String> login(Credentials credentials);
    void register(Student student);
    void registerTutor(Tutor tutor);
}
