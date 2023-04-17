package com.project.controller;

import java.net.URI;
import java.util.List;

import com.project.model.Projekt;
import jakarta.validation.Valid;

import com.project.model.Zadanie;
import com.project.service.ZadanieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ZadanieRestController {
    private ZadanieService zadanieService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }

    @GetMapping("/zadania/{zadanieId}")
    ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId) {
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @GetMapping("/zadania/{projektId}/{zadanieId}")
    ResponseEntity<Zadanie> getZadanieProjekt(@PathVariable Integer zadanieId) {
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @PostMapping(path = "/zadania/{projektId}")
    ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie, @PathVariable("projektId") Integer projektId) {
        Zadanie createdZadanie = zadanieService.setZadanie(zadanie, projektId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{zadanieId}")
                .buildAndExpand(createdZadanie.getZadanieId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/zadania/{zadanieId}/{projektId}")
    public ResponseEntity<Void> updateZadanie(@Valid @RequestBody Zadanie zadanie, @PathVariable("zadanieId") Integer zadanieId, @PathVariable("projektId") Integer projektId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.setZadanie(zadanie, projektId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/zadania")
    Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieService.getZadania(pageable);
    }

    @GetMapping("/zadania/sort={sort}/order={order}")
    public List <Zadanie> getSortedZadania(@PathVariable String sort, @PathVariable String order) {
        Page <Zadanie> data = zadanieService.getZadaniaPageSort(sort, order);
        return data.getContent();
    }
}