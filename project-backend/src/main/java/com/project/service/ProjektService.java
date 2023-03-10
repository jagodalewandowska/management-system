package com.project.service;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Projekt;

public interface ProjektService {
    Optional<Projekt> getProjekt(Integer projektId);
    Projekt setProjekt(Projekt projekt);
    void deleteProjekt(Integer projektId);
    Page<Projekt> getProjekty(Pageable pageable);
    Page<Projekt> searchByNazwa(String nazwa, Pageable pageable);
}