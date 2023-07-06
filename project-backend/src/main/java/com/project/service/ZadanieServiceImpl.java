package com.project.service;

import com.project.model.Student;
import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZadanieServiceImpl implements ZadanieService {

    private ZadanieRepository zadanieRepository;
    private ProjektService projektService;

    @Autowired
    public ZadanieServiceImpl(ZadanieRepository zadanieRepository, ProjektService projektService) {
        this.zadanieRepository = zadanieRepository;
        this.projektService = projektService;
    }

    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public Page<Zadanie> getZadanieProjektu(Integer projektId, Pageable pageable){
        return zadanieRepository.findZadaniaProjektu(projektId, pageable);
    }

    @Override
    public Zadanie setZadanie(Zadanie zadanie){
        return zadanieRepository.save(zadanie);
    }

    @Override
    @Transactional
    public void deleteZadanie(Integer zadanieId) {
        //TODO Zmienic deleteZadanie nie uwzględnia projektId
        zadanieRepository.deleteById(zadanieId);
    }

    @Override
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieRepository.findAll(pageable);
    }

    @Override
    public Page <Zadanie> getZadaniaPageSort(String sort, String direction) {
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
        return zadanieRepository.findAll(pageable);
    }

    @Override
    public Page<Zadanie> searchByNazwa(String nazwa, Pageable pageable) {
        return zadanieRepository.findByNazwaStartsWithIgnoreCase(nazwa, pageable);
    }
}