package com.project.service;
import java.util.List;
import java.util.Optional;

import com.project.repository.ZadanieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.project.model.Projekt;
import com.project.repository.ProjektRepository;
import com.project.model.Zadanie;
@Service
public class ProjektServiceImpl implements ProjektService {
    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;
    @Autowired //adnotację można pomijać, jeżeli nie ma kilku wersji konstruktora
    public ProjektServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepository) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepository;
    }
    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }
    @Override
    public Projekt setProjekt(Projekt projekt) {
        Projekt projektToSave = null;
        if(projekt.getProjektId()!=null) {
            projektToSave = projekt;
        }else {
            projektToSave = new Projekt(projekt.getNazwa(),projekt.getOpis(),projekt.getDataOddania());
        }
        return  projektRepository.save(projektToSave);
    }
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {
        for (Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)) {
            zadanieRepository.delete(zadanie);
        }
        projektRepository.deleteById(projektId);
    }
    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektRepository.findAll(pageable);
    }
    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        return projektRepository.findByNazwaContainingIgnoreCase(nazwa, pageable);
    }

    @Override
    public Page <Projekt> getProjektyPageSort(String sort, String direction) {
        Pageable pageable = null;
        direction = direction.toUpperCase();
        if (sort != null) {
            if (direction.equals("ASC")) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, sort);
            }
            if (direction.equals("DESC")) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, sort);
            }
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE);
        }
        return projektRepository.findAll(pageable);
    }
}

