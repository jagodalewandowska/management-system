package com.controller;

import com.project.controller.ZadanieController;
import com.project.model.Zadanie;
import com.project.service.ProjektService;
import com.project.service.ZadanieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZadanieControllerTest {

    @Mock
    private ZadanieService zadanieService;

    @Mock
    private ProjektService projektService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ZadanieController zadanieController;

    @Test
    public void testZadanieEditSave_WithValidZadanie_ShouldRedirectToZadanieList() {
        Zadanie zadanie = new Zadanie();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(zadanieService.setZadanie(any(Zadanie.class))).thenReturn(zadanie);

        String result = zadanieController.zadanieEditSave(zadanie, bindingResult);

        verify(zadanieService, times(1)).setZadanie(any(Zadanie.class));
        assertEquals("redirect:/app/zadanieList", result);
    }

    @Test
    public void testZadanieEditSave_WithInvalidZadanie_ShouldReturnEditPage() {
        Zadanie zadanie = new Zadanie();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = zadanieController.zadanieEditSave(zadanie, bindingResult);

        verifyNoMoreInteractions(zadanieService);
        assertEquals("zadanieEdit", result);
    }

    @Test
    public void testZadanieEditDelete_ShouldRedirectToZadanieList() {
        Zadanie zadanie = new Zadanie();
        zadanie.setZadanieId(1);

        String result = zadanieController.zadanieEditDelete(zadanie);

        verify(zadanieService, times(1)).deleteZadanie(zadanie.getZadanieId());
        assertEquals("redirect:/app/zadanieList", result);
    }
}
