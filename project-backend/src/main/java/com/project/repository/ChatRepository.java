package com.project.repository;

import com.project.model.Chat;
import com.project.model.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Page<Chat> findByProjektProjektId(Integer projektId, Pageable pageable);
}
