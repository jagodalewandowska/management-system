package com.project.service;

import com.project.model.Projekt;
import com.project.model.User;
import com.project.model.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  { // extends UserDetailsService
    //User save(UserRegistrationDto registrationDto);
    Page<User> getAll(Pageable pageable);
}
