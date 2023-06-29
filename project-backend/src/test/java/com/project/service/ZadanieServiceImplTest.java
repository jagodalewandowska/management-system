package com.project.service;

import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZadanieServiceImplTest {

    @Mock
    private ZadanieRepository zadanieRepository;

    @Mock
    private ProjektService projektService;

    private ZadanieServiceImpl zadanieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        zadanieService = new ZadanieServiceImpl(zadanieRepository, projektService);
    }

    @Test
    void testGetZadanie_WithValidId_ShouldReturnOptionalWithZadanie() {
        Integer zadanieId = 1;
        Zadanie expectedZadanie = new Zadanie();
        when(zadanieRepository.findById(zadanieId)).thenReturn(Optional.of(expectedZadanie));
        Optional<Zadanie> result = zadanieService.getZadanie(zadanieId);
        assertTrue(result.isPresent());
        assertEquals(expectedZadanie, result.get());
        verify(zadanieRepository).findById(zadanieId);
    }

    @Test
    void testGetZadanie_WithInvalidId_ShouldReturnEmptyOptional() {
        Integer zadanieId = 1;
        when(zadanieRepository.findById(zadanieId)).thenReturn(Optional.empty());
        Optional<Zadanie> result = zadanieService.getZadanie(zadanieId);
        assertFalse(result.isPresent());
        verify(zadanieRepository).findById(zadanieId);
    }

    @Test
    void testSetZadanie_ShouldSaveAndReturnZadanie() {
        Zadanie zadanie = new Zadanie();
        when(zadanieRepository.save(zadanie)).thenReturn(zadanie);
        Zadanie result = zadanieService.setZadanie(zadanie);
        assertEquals(zadanie, result);
        verify(zadanieRepository).save(zadanie);
    }

    @Test
    void testGetZadanieProjektu_ShouldReturnPageOfZadania() {
        Integer projektId = 1;
        Pageable pageable = PageRequest.of(0, 10);
        Page<Zadanie> expectedPage = mock(Page.class);
        when(zadanieRepository.findZadaniaProjektu(projektId, pageable)).thenReturn(expectedPage);

        Page<Zadanie> result = zadanieService.getZadanieProjektu(projektId, pageable);

        assertEquals(expectedPage, result);
        verify(zadanieRepository).findZadaniaProjektu(projektId, pageable);
    }

    @Test
    void testGetZadania_ShouldReturnPageOfZadania() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Zadanie> expectedPage = mock(Page.class);
        when(zadanieRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Zadanie> result = zadanieService.getZadania(pageable);

        assertEquals(expectedPage, result);
        verify(zadanieRepository).findAll(pageable);
    }

    @Test
    void testGetZadaniaPageSort_ShouldReturnPageOfZadaniaSorted() {
        String sort = "nazwa";
        String direction = "ASC";
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, sort);
        Page<Zadanie> expectedPage = mock(Page.class);
        when(zadanieRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Zadanie> result = zadanieService.getZadaniaPageSort(sort, direction);


    }
}
