package com.project.rest;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.model.Projekt;
import com.project.service.ProjektService;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class ProjektRestControllerTest {
    // Uwaga! Test wymaga poniższego konstruktora w klasie Projekt, dodaj jeżeli nie został jeszcze zdefiniowany.
    // public Projekt(Integer projektId, String nazwa, String opis, LocalDateTime dataCzasUtworzenia, LocalDate data_oddania){
        // ...
        //}
        // --- URUCHAMIANIE TESTÓW ---
        // ABY URUCHOMIĆ TESTY KLIKNIJ NA NAZWIE KLASY PRAWYM PRZYCISKIEM
        // MYSZY I WYBIERZ Z MENU 'Run As' -> 'Gradle Test' LUB PO USTAWIENIU
        // KURSORA NA NAZWIE KLASY WCIŚNIJ SKRÓT 'CTRL+ALT+X' A PÓŹNIEJ 'G'
        // MOŻNA RÓWNIEŻ ANALOGICZNIE URUCHAMIAĆ POJEDYNCZE METODY KLIKAJĄC
        // WCZEŚNIEJ NA ICH NAZWĘ
        private final String apiPath = "/api/projekty";
        @MockBean
        private ProjektService mockProjektService; //tzw. mock (czyli obiekt, którego używa się zamiast rzeczywistej
        //implementacji) serwisu wykorzystywany przy testowaniu kontrolera
        @Autowired
        private MockMvc mockMvc;
        private JacksonTester<Projekt> jacksonTester;
    @Test
    public void getProjekty() throws Exception {
        Projekt projekt = new Projekt(1, "Nazwa1", "Opis1", LocalDateTime.now(), LocalDate.of(2020, 6, 7));
        Page<Projekt> page = new PageImpl<>(Collections.singletonList(projekt));
        when(mockProjektService.getProjekty(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].projektId").value(projekt.getProjektId()))
                .andExpect(jsonPath("$.content[0].nazwa").value(projekt.getNazwa()));

        verify(mockProjektService, times(1)).getProjekty(any(Pageable.class));
        verifyNoMoreInteractions(mockProjektService);
    }
    @Test
    public void getProjekt() throws Exception {
        Projekt projekt = new Projekt(2, "Nazwa2", "Opis2", LocalDateTime.now(), LocalDate.of(2020, 6, 7));
        when(mockProjektService.getProjekt(projekt.getProjektId()))
                .thenReturn(Optional.of(projekt));
        mockMvc.perform(get(apiPath + "/{projektId}", projekt.getProjektId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projektId").value(projekt.getProjektId()))
                .andExpect(jsonPath("$.nazwa").value(projekt.getNazwa()));
        verify(mockProjektService, times(1)).getProjekt(projekt.getProjektId());
        verifyNoMoreInteractions(mockProjektService);
    }
    @Test
    public void createProjekt() throws Exception {
        Projekt projekt = new Projekt(null, "Nazwa3", "Opis3", null, LocalDate.of(2020, 6, 7));
        String jsonProjekt = jacksonTester.write(projekt).getJson();
        projekt.setProjektId(3);
        when(mockProjektService.setProjekt(any(Projekt.class))).thenReturn(projekt);

        mockMvc.perform(post(apiPath).content(jsonProjekt).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString(apiPath + "/" + projekt.getProjektId())));
    }
    @Test
    public void createProjektEmptyName() throws Exception {
        Projekt projekt = new Projekt(null, "", "Opis4", null, LocalDate.of(2020, 6, 7));
        MvcResult result = mockMvc.perform(post(apiPath)
                        .content(jacksonTester.write(projekt).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(mockProjektService, times(0)).setProjekt(any(Projekt.class));

        Exception exception = result.getResolvedException();
        assertNotNull(exception);
        assertTrue(exception instanceof MethodArgumentNotValidException);
        System.out.println(exception.getMessage());
    }
    @Test
    public void updateProjekt() throws Exception {
        Projekt projekt = new Projekt(5, "Nazwa5", "Opis5", LocalDateTime.now(), LocalDate.of(2020, 6, 7));
        String jsonProjekt = jacksonTester.write(projekt).getJson();

        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));
        when(mockProjektService.setProjekt(any(Projekt.class))).thenReturn(projekt);
        mockMvc.perform(put(apiPath + "/{projektId}", projekt.getProjektId())
                        .content(jsonProjekt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());

        verify(mockProjektService, times(1)).getProjekt(projekt.getProjektId());
        verify(mockProjektService, times(1)).setProjekt(any(Projekt.class));
        verifyNoMoreInteractions(mockProjektService);
    }
    /**
     * Test sprawdza czy żądanie o danych parametrach stronicowania i sortowania
     * spowoduje przekazanie do serwisu odpowiedniego obiektu Pageable, wcześniej
     * wstrzykniętego do parametru wejściowego metody kontrolera
     */
    @Test
    public void getProjektyAndVerifyPageableParams() throws Exception {
        Integer page = 5;
        Integer size = 15;
        String sortProperty = "nazwa";
        String sortDirection = "desc";
        mockMvc.perform(get(apiPath)
                        .param("page", page.toString())
                        .param("size", size.toString())
                        .param("sort", String.format("%s,%s", sortProperty, sortDirection)))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mockProjektService, times(1)).getProjekty(pageableCaptor.capture());

        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());
        assertEquals(sortProperty, pageable.getSort().getOrderFor(sortProperty).getProperty());
        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor(sortProperty).getDirection());
    }
    @BeforeEach
    public void before(TestInfo testInfo) {
        System.out.printf("-- METODA -> %s%n", testInfo.getTestMethod().get().getName());
        ObjectMapper mapper = new ObjectMapper(); // ustawienie formatu daty i czasu w komunikatach
        mapper.registerModule(new JavaTimeModule()); // JSON-a dla zmiennych typu LocalDate i LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JacksonTester.initFields(this, mapper);
    }
    @AfterEach
    public void after(TestInfo testInfo) {
        System.out.printf("<- KONIEC -- %s%n", testInfo.getTestMethod().get().getName());
    }
}