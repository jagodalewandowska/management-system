package com.project.auth;

import com.project.model.Student;
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
    // private final ValidationService<Student> validator;


    // zwracany token na jwt.io
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Student student) {
        authService.register(student);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody Student student) {
//        validator.validate(student);
//        return ResponseEntity.ok(authService.register(student));
//    }



}
