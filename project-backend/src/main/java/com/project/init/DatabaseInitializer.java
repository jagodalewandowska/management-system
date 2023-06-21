package com.project.init;

import com.project.model.Student;
import com.project.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.project.model.Role.ADMIN;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DatabaseInitializer(StudentRepository studentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!studentRepository.existsByEmail("administrator@admin.admin")) {
            Student student = new Student();
            student.setImie("Admin");
            student.setNazwisko("Administrator");
            student.setEmail("administrator@admin.admin");
            student.setPassword(passwordEncoder.encode("admin123"));
            student.setRole(ADMIN);
            student.setNrIndeksu("987654");
            student.setStacjonarny(false);
            studentRepository.save(student);
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
