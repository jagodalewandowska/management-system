package com.project.auth;

import com.project.model.Student;
import com.project.model.Tutor;
import com.project.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final ValidationService<Tutor> validatorTutor;
    private final ValidationService<Student> validator; // można dać do projektu, zadania - jest to klasa generyczna


    // zwracany token na jwt.io
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Student student) {
        validator.validate(student);
        authService.register(student);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/registerTutor")
    public ResponseEntity<Void> registerTutor(@RequestBody Tutor tutor) {
        validatorTutor.validate(tutor);
        authService.registerTutor(tutor);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody Student student) {
//        validator.validate(student);
//        return ResponseEntity.ok(authService.register(student));
//    }



}
