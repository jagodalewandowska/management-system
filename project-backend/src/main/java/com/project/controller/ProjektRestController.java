package com.project.controller;
import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.project.model.Projekt;
import com.project.service.ProjektService;

@RestController
@RequestMapping("/api")
public class ProjektRestController {
    private ProjektService projektService;
    @Autowired
    public ProjektRestController(ProjektService projektService) {
        this.projektService = projektService;
    }
    @GetMapping("/projekty/{projektId}")
    ResponseEntity<Projekt> getProjekt(@PathVariable Integer projektId) {
        return ResponseEntity.of(projektService.getProjekt(projektId));
    }

    @PostMapping(path = "/projekty")
    ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {
        Projekt createdProjekt = projektService.setProjekt(projekt);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{projektId}").buildAndExpand(createdProjekt.getProjektId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/projekty/{projektId}")
    public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Projekt projekt,
                                              @PathVariable Integer projektId) {
        return projektService.getProjekt(projektId)
                .map(p -> {
                    projektService.setProjekt(projekt);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/projekty/{projektId}")
    public ResponseEntity<Void> deleteProjekt(@PathVariable Integer projektId) {
        return projektService.getProjekt(projektId).map(p -> {
            projektService.deleteProjekt(projektId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Przykład żądania wywołującego metodę: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
    @GetMapping(value = "/projekty")
    Page<Projekt> getProjekty(Pageable pageable) {
        return projektService.getProjekty(pageable);
    }

    // Przykład żądania wywołującego metodę: GET http://localhost:8080/api/projekty?nazwa=webowa
    // Metoda zostanie wywołana tylko, gdy w żądaniu będzie przesyłana wartość parametru nazwa.
    @GetMapping(value = "/projekty", params="nazwa")
    Page<Projekt> getProjektyByNazwa(@RequestParam String nazwa, Pageable pageable) {
        return projektService.searchByNazwa(nazwa, pageable);
    }
}

