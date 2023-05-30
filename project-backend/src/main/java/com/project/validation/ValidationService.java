package com.project.validation;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

@Service
@RequiredArgsConstructor
public class ValidationService<T> {
    private final Validator validator;

    public void validate(T object) {
        var violations = validator.validate(object);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
