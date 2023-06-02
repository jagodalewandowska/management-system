package com.project.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomTutorDetails implements UserDetails {
    private final Tutor tutorUser;

    public CustomTutorDetails(Tutor tutorUser) {
        this.tutorUser = tutorUser;
    }

    public final Tutor getUser() {
        return tutorUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tutorUser.getRole().name()));
    }

    @Override
    public String getPassword() {
        return tutorUser.getPassword();
    }

    @Override
    public String getUsername() {
        return tutorUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
