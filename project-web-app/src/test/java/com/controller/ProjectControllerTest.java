package com.controller;

import com.project.controller.ProjectController;
import com.project.model.Projekt;
import com.project.service.ProjektService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjektService projektService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProjectController projectController;

    @Test
    public void testProjektEditSave_WithValidProjekt_ShouldRedirectToProjektList() {
        Projekt projekt = new Projekt();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(projektService.setProjekt(any(Projekt.class))).thenReturn(projekt);

        String result = projectController.projektEditSave(projekt, bindingResult);

        verify(projektService, times(1)).setProjekt(any(Projekt.class));
        assertEquals("redirect:/app/projektList", result);
    }

    @Test
    public void testProjektEditSave_WithInvalidProjekt_ShouldReturnEditPage() {
        Projekt projekt = new Projekt();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = projectController.projektEditSave(projekt, bindingResult);

        verifyNoMoreInteractions(projektService);
        assertEquals("projektEdit", result);
    }
}

