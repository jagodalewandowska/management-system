package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.project.model.User;
import org.springframework.stereotype.Service;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
