package com.project.service;

import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

//    @Override
//    public Zadanie setZadanie(Zadanie zadanie) {
//        Zadanie zadanieToSave = null;
//        if(zadanie.getZadanieId()!=null) {
//            zadanieToSave = zadanie;
//        }else {
//            zadanieToSave = new Zadanie(zadanie.getNazwa(), zadanie.getOpis(), zadanie.getKolejnosc());
//        }
//        return  zadanieRepository.save(zadanieToSave);
//    }

    @Override
    public Zadanie setZadanie(Zadanie zadanie){
        return zadanieRepository.save(zadanie);
    }

//    @Override
//    @Transactional
//    public void deleteZadanie(Integer zadanieId) {
//        //TODO Zmienic deleteZadanie nie uwzględnia projektId
//        zadanieRepository.deleteById(zadanieId);
//    }

    @Override
    public void deleteZadanie(Integer zadanieId) {
        Integer projektId = zadanieRepository.findById(zadanieId).get().getProjekt().getProjektId();
        zadanieRepository.delete(zadanieRepository.findById(zadanieId).get());

        List<Zadanie> zadaniaProjektu = projektService.getProjekt(projektId).get().getZadania();
        AtomicInteger kol = new AtomicInteger();
        kol.set(1);
        zadaniaProjektu.stream().sorted(Comparator.comparingInt(Zadanie::getKolejnosc)).forEach(s->{
            s.setKolejnosc(kol.get());
            kol.getAndIncrement();
            zadanieRepository.save(s);
        });

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
}