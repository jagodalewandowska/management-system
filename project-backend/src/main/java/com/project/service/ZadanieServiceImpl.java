package com.project.service;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.repository.ProjektRepository;
import com.project.repository.ZadanieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ZadanieServiceImpl implements ZadanieService {

    private ZadanieRepository zadanieRepository;
    private ProjektRepository projektRepository;

    @Autowired
    public ZadanieServiceImpl(ZadanieRepository zadanieRepository, ProjektRepository projektRepository) {
        this.zadanieRepository = zadanieRepository;
        this.projektRepository = projektRepository;
    }

    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public Zadanie setZadanie(Zadanie zadanie, Integer projektId) {
        Zadanie zadanieToSave = null;
        if(zadanie.getZadanieId()!=null) {
            zadanieToSave = zadanie;
        }else {
            zadanieToSave = new Zadanie(zadanie.getNazwa(), zadanie.getOpis(), zadanie.getKolejnosc());
        }
        return  zadanieRepository.save(zadanieToSave);
    }

    @Override
    @Transactional
    public void deleteZadanie(Integer zadanieId) {
        zadanieRepository.deleteById(zadanieId);

    }

    @Override
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieRepository.findAll(pageable);
    }
}