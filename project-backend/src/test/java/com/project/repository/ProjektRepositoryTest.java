package com.project.repository;

import com.project.model.Projekt;
import com.project.service.ProjektService;
import com.project.service.ProjektServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjektRepositoryTest {

    @Mock
    private ProjektRepository projektRepository;

    @InjectMocks
    private ProjektServiceImpl projektService;

    @Test
    public void testFindByNazwaContainingIgnoreCase() {
        String nazwa = "pierwszy";
        Pageable pageable = Pageable.unpaged();
        List<Projekt> expectedResult = new ArrayList<>();
        expectedResult.add(new Projekt("Projekt pierwszy", "Opis projektu 1", LocalDate.now()));
        expectedResult.add(new Projekt("Projekt drugi", "Opis projektu 2", LocalDate.now()));

        Page<Projekt> expectedPage = new PageImpl<>(expectedResult.subList(0, 1));

        when(projektRepository.findByNazwaContainingIgnoreCase(nazwa, pageable)).thenReturn(expectedPage);

        Page<Projekt> result = projektService.searchByNazwa(nazwa, pageable);

        assertEquals(expectedResult.subList(0, 1), result.getContent());

        System.out.println("Oczekiwany projekt:");
        expectedResult.subList(0, 1).forEach(p -> System.out.println("Nazwa: " + p.getNazwa() + ", Opis: " + p.getOpis() + ", Data: " + p.getDataOddania()));

        System.out.println("Wyszukany projekt:");
        result.getContent().forEach(p -> System.out.println("Nazwa: " + p.getNazwa() + ", Opis: " + p.getOpis() + ", Data: " + p.getDataOddania()));

        System.out.println("Test 'searchByNazwa' zakończony pomyślnie!");
    }
}
