package com.project.service;

import java.net.URI;
import java.util.Optional;

import com.project.model.Projekt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.project.model.Student;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(ProjektServiceImpl.class);

    @Value("http://localhost:8080") //adres serwera jest wstrzykiwany przez Springa, a jego wartość
    private String  serverUrl;

    private final static String RESOURCE_PATH = "/api/projekty";
    // obiekt wstrzykiwany poprzez konstruktor, dzięki adnotacjom
    private RestTemplate restTemplate; // @Configuration i @Bean zawartym w klasie SecurityConfig
    // Spring utworzy wcześniej obiekt, a adnotacja @Autowired
// tej klasy wskaże element docelowy wstrzykiwania // (adnotacja może być pomijana jeżeli w klasie jest // tylko jeden konstruktor)

    @Autowired
    public StudentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Student> getStudent(Integer studentId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(studentId))
                .build()
                .toUri();
        logger.info("REQUEST -> GET {}", url);
        return Optional.ofNullable(restTemplate.getForObject(url, Student.class));
    }

    @Override
    public Student setStudent(Student student) {
        if (student.getStudentId() != null) { // modyfikacja istniejącego projektu
            String url = getUriStringComponent(student.getStudentId());
            logger.info("REQUEST -> PUT {}", url);
            restTemplate.put(url, student);
            return student;
        } else {//utworzenie nowego projektu
// po dodaniu projektu zwracany jest w nagłówku Location - link do utworzonego zasobu
            HttpEntity<Student> request = new HttpEntity<>(student);
            String url = getUriStringComponent();
            logger.info("REQUEST -> POST {}", url);
            URI location = restTemplate.postForLocation(url, request);
            logger.info("REQUEST (location) -> GET {}", location);
            return restTemplate.getForObject(location, Student.class);
// jeżeli usługa miałaby zwracać utworzony obiekt a nie link to trzeba by użyć
// return restTemplate.postForObject(url, student, Student.class);
        }
    }

    private String getUriStringComponent() {
        return serverUrl + getResourcePath();
    }

    private String getResourcePath() {
        return RESOURCE_PATH;
    }

    private String getUriStringComponent(Integer id) {
        return serverUrl + getResourcePath(id);
    }

    @Override
    public void deleteStudent(Integer studentId) {

    }

    @Override
    public Page<Student> getStudenci(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
        return null;
    }

    private String getResourcePath(Integer id) {
        return RESOURCE_PATH + "/" + id;
    }
}
