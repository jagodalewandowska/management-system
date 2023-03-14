package com.project.service;

import com.project.model.Zadanie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ZadanieService {
    Optional<Zadanie> getZadanie(Integer zadanieId);
    Zadanie setZadanie(Zadanie zadanie, Integer projektId);
    void deleteZadanie(Integer zadanieId);
    Page<Zadanie> getZadania(Pageable pageable);
}