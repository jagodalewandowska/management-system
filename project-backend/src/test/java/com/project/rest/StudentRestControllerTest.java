package com.project.rest;

import com.project.model.Projekt;
import com.project.model.Student;
import com.project.service.ProjektService;
import com.project.service.StudentService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

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
public class StudentRestControllerTest {
    private final String apiPath = "/api/studenci";
    @MockBean
    private StudentService mockStudentService;
    @Autowired
    private MockMvc mockMvc;
    private JacksonTester<Projekt> jacksonTester;

    @Test
    public void getStudenci() throws Exception {
        Student student= new Student("Jan", "Kowalski", "12345", "kowalskijan@gmail.com", true);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
    @Test
    public void getStudent() throws Exception { //??
        Student student= new Student("Jan1", "Kowalski2", "12346", "kowalskijan1@gmail.com", true);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
    @Test
    public void getStudentNonStationary() throws Exception { //??
        Student student= new Student("Jan2", "Kowal2", "12347", "kowalskijan2@gmail.com", false);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
    @Test
    public void getStudentEmptyEmail() throws Exception { //??
        Student student= new Student("Jan3", "Kowalski3", "12348", "", true);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
    @Test
    public void getStudentEmptyName() throws Exception { //??
        Student student= new Student("", "Kowalski4", "12349", "kowalskijan4@gmail.com", true);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
    @Test
    public void getStudentIndexDuplication() throws Exception { //??
        Student student= new Student("Jan5", "Kowalski5", "12345", "kowalskijan5@gmail.com", true);
        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(mockStudentService.getStudenci(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content[0].imie").value(student.getImie()))
                .andExpect(jsonPath("$.content[0].nazwisko").value(student.getNazwisko()))
                .andExpect(jsonPath("$.content[0].nrIndeksu").value(student.getNrIndeksu()))
                .andExpect(jsonPath("$.content[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$.content[0].stacjonarny").value(student.getStacjonarny()));

        verify(mockStudentService, times(1)).getStudenci(any(Pageable.class));
        verifyNoMoreInteractions(mockStudentService);
    }
}
