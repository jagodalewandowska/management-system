package com.project.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import com.project.service.ProjektService;
import jakarta.validation.Valid;

import com.project.model.Zadanie;
import com.project.service.ZadanieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ZadanieRestController {
    private ZadanieService zadanieService;
    private ProjektService projektService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService, ProjektService projektService) {
        this.zadanieService = zadanieService;
        this.projektService = projektService;
    }

    @GetMapping("/zadania/{zadanieId}")
    ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId) {
        System.out.println("GET wybrane zadanie");
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @GetMapping("/zadania/projektId={projektId}")
    Page<Zadanie> getZadanieProjektu(@PathVariable Integer projektId, Pageable pageable){
        System.out.println("GET z danym projektem");
        return zadanieService.getZadanieProjektu(projektId, pageable);
    }

    @PostMapping("/zadania/{projektId}")
    ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie, @PathVariable Integer projektId) {
        System.out.println("ProjektId: "+projektId);
//        Integer nextKolejnosc= projektService.getProjekt(projektId).get().getZadania().size();
        zadanie.setDataczas_dodania(LocalDateTime.now());
//        zadanie.setKolejnosc(nextKolejnosc+1);
        //System.out.println(projektId);
        zadanie.setProjekt(projektService.getProjekt(projektId).get());
        Zadanie createdZadanie = zadanieService.setZadanie(zadanie);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{zadanieId}")
                .buildAndExpand(createdZadanie.getZadanieId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/zadania/{zadanieId}", params = "projektId")
    public ResponseEntity<Void> updateZadanie(@Valid @RequestBody Zadanie zadanie,
                                              @PathVariable("zadanieId") Integer zadanieId, @RequestParam("projektId") Integer projektId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanie.setProjekt(projektService.getProjekt(projektId).get());
            zadanieService.setZadanie(zadanie);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/zadania/{zadanieId}", params = "projektId")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId, @RequestParam Integer projektId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/zadania")
    Page<Zadanie> getZadania(Pageable pageable) {
        System.out.println("GET Wszystkie zdania");
        return zadanieService.getZadania(pageable);
    }

    @GetMapping("/zadania/sort={sort}/order={order}")
    public List <Zadanie> getSortedZadania(@PathVariable String sort, @PathVariable String order) {
        Page <Zadanie> data = zadanieService.getZadaniaPageSort(sort, order);
        return data.getContent();
    }
}