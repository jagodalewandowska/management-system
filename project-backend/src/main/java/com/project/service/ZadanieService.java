package com.project.service;

import java.util.Optional;

import com.project.model.Zadanie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZadanieService {
    Optional<Zadanie> getZadanie(Integer zadanieId);
    Zadanie setZadanie(Zadanie zadanie);

    void deleteZadanie(Integer zadanieId);
    Page<Zadanie> getZadania(Pageable pageable);
    Page<Zadanie> getZadaniaPageSort(String sort, String direction);
    Page<Zadanie> getZadanieProjektu(Integer projektId, Pageable pageable);
}