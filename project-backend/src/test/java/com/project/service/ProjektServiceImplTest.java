package com.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.project.model.Projekt;
import com.project.repository.ProjektRepository;

import com.project.repository.ZadanieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProjektServiceImplTest {

    @Mock
    private ProjektRepository projektRepository;

    @Mock
    private ZadanieRepository zadanieRepository;

    private ProjektService projektService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projektService = new ProjektServiceImpl(projektRepository, zadanieRepository);
    }

    // getProjekt z prawidłowym ID
    @Test
    void testGetProjekt_WithValidId_ShouldReturnOptionalWithProjekt() {
        Integer projektId = 1;
        Projekt expectedProjekt = new Projekt();
        when(projektRepository.findById(projektId)).thenReturn(Optional.of(expectedProjekt));
        Optional<Projekt> result = projektService.getProjekt(projektId);
        assertTrue(result.isPresent());
        assertEquals(expectedProjekt, result.get());
        verify(projektRepository).findById(projektId);
    }

    // getProjekt z nieprawidłowym ID
    @Test
    void testGetProjekt_WithInvalidId_ShouldReturnEmptyOptional() {
        Integer projektId = 1;
        when(projektRepository.findById(projektId)).thenReturn(Optional.empty());
        Optional<Projekt> result = projektService.getProjekt(projektId);
        assertFalse(result.isPresent());
        verify(projektRepository).findById(projektId);
    }

    // setProjekt z istniejącym obiektem Projekt
    @Test
    void testSetProjekt_WithExistingProjekt_ShouldSaveAndReturnProjekt() {
        Projekt existingProjekt = new Projekt();
        existingProjekt.setProjektId(1);
        Projekt updatedProjekt = new Projekt();
        updatedProjekt.setProjektId(1);
        when(projektRepository.save(updatedProjekt)).thenReturn(updatedProjekt);
        Projekt result = projektService.setProjekt(updatedProjekt);
        assertEquals(updatedProjekt, result);
        verify(projektRepository).save(updatedProjekt);
    }

    // deleteProjekt z prawidłowym identyfikatorem projektu
    @Test
    void testDeleteProjekt_WithValidId_ShouldDeleteProjekt() {
        Integer projektId = 1;
        projektService.deleteProjekt(projektId);
        verify(projektRepository).deleteById(projektId);
    }
}
