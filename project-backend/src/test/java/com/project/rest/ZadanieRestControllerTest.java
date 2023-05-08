package com.project.rest;

import com.project.model.Projekt;
import com.project.model.Student;
import com.project.model.Zadanie;
import com.project.service.ProjektService;
import com.project.service.StudentService;
import com.project.service.ZadanieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class ZadanieRestControllerTest {
    private final String apiPath = "/api/zadanie";
    @MockBean
    private ZadanieService mockZadanieService;
    @Autowired
    private MockMvc mockMvc;
    private JacksonTester<Projekt> jacksonTester;

    @Test
    public void getZadania() throws Exception { //??
        Zadanie zadanie= new Zadanie("Nazwa1", 1, "Opis1");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);
    }
    @Test
    public void getZadanie() throws Exception { //??
        Zadanie zadanie= new Zadanie("Nazwa2", 2, "Opis2");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);
    }
    @Test
    public void getZadanieIndexDuplication() throws Exception { //??
        Zadanie zadanie= new Zadanie("Nazwa3", null, "Opis3");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);

//        Exception exception = result.getResolvedException(); ??
//        assertNotNull(exception);
//        assertTrue(exception instanceof MethodArgumentNotValidException);
//        System.out.println(exception.getMessage());
    }
    @Test
    public void getZadanieEmptyName() throws Exception { //??
        Zadanie zadanie = new Zadanie("", 3, "Opis3");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);
    }
    @Test
    public void getZadanieEmptyDescription() throws Exception { //??
        Zadanie zadanie = new Zadanie("Nazwa4", 4, "");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);
    }
    @Test
    public void getZadanieEmptyDescription() throws Exception { //??
        Zadanie zadanie = new Zadanie("Nazwa5", 1, "");
        Page<Zadanie> page = new PageImpl<>(Collections.singletonList(zadanie));
        when(mockZadanieService.getZadanie(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].nawa").value(zadanie.getNazwa()))
                .andExpect(jsonPath("$.content[0].kolejnosc").value(zadanie.getKolejnosc()))
                .andExpect(jsonPath("$.content[0].opis").value(zadanie.getOpis()));

        verify(mockZadanieService, times(1)).getZadanie(any(Pageable.class));
        verifyNoMoreInteractions(mockZadanieService);
    }
}
