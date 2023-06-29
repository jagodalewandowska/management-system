package com.project.rest;

import com.project.auth.AuthController;
import com.project.auth.AuthService;
import com.project.auth.AuthRequest;
import com.project.auth.AuthResponse;
import com.project.model.Student;
import com.project.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthService authService;

    @Mock
    private ValidationService<Student> validator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        Student student = new Student();
        doNothing().when(validator).validate(student);

        ResponseEntity<Void> response = authController.register(student);

        verify(validator).validate(student);
        verify(authService).register(student);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        System.out.println("Test rejestracji zakończony pomyślnie!");
    }

    @Test
    void testLogin() {
        AuthRequest request = new AuthRequest();
        request.setEmail("administrator@admin.admin");
        request.setPassword("admin123");

        AuthResponse authResponse = new AuthResponse();

        when(authService.authenticate(request)).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = authController.login(request);

        verify(authService).authenticate(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());

        System.out.println("Test logowania zakończony pomyślnie!");
    }
}
