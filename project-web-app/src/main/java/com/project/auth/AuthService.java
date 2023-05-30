package com.project.auth;

import com.project.model.Student;

import java.util.Optional;

public interface AuthService {
    Optional<String> login(Credentials credentials);
    void register(Student student);
}
