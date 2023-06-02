package com.project.auth;

import com.project.config.JwtService;
import com.project.model.*;
import com.project.service.StudentService;
import com.project.service.TutorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentService studentService;
    private final TutorService tutorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.USER);
        studentService.setStudent(student);
    }

    public void registerTutor(Tutor tutor){
        tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));
        tutor.setRole(Role.ADMIN);
        tutorService.setTutor(tutor);
    }

    public AuthResponse authenticate(@NonNull String email, @NonNull String password) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (studentService.searchByEmail(email)==null) {
            var tutor = tutorService
                    .searchByEmail(email)
                    .map(s -> new CustomTutorDetails(s))
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("User '%s' not found!", email)));
            var token = jwtService.generateToken(tutor);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } else {
            var user = studentService
                    .searchByEmail(email)
                    .map(s -> new CustomUserDetails(s))
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("User '%s' not found!", email)));
            var token = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        }
    }

    public AuthResponse authenticate(AuthRequest request) {
        return authenticate(request.getEmail(), request.getPassword());
    }
}
