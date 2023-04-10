package com.project.service;

import java.net.URI;
import java.util.Optional;
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
import com.project.model.Projekt;
@Service
public class ProjektServiceImpl implements ProjektService {
    private static final Logger logger = LoggerFactory.getLogger(ProjektServiceImpl.class);
    @Value("${rest.server.url}") // adres serwera jest wstrzykiwany przez Springa, a jego wartość
    private String serverUrl; // przechowywana w pliku src/main/resources/application.properties
    private final static String RESOURCE_PATH = "/api/projekty";
    // obiekt wstrzykiwany poprzez konstruktor, dzięki adnotacjom
    private RestTemplate restTemplate; // @Configuration i @Bean zawartym w klasie SecurityConfig
    // Spring utworzy wcześniej obiekt, a adnotacja @Autowired
// tej klasy wskaże element docelowy wstrzykiwania // (adnotacja może być pomijana jeżeli w klasie jest // tylko jeden konstruktor)
    @Autowired
    public ProjektServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(projektId))
                .build()
                .toUri();
        logger.info("REQUEST -> GET {}", url);
        return Optional.ofNullable(restTemplate.getForObject(url, Projekt.class));
    }
    @Override
    public Projekt setProjekt(Projekt projekt) {
        if (projekt.getProjektId() != null) { // modyfikacja istniejącego projektu
            String url = getUriStringComponent(projekt.getProjektId());
            logger.info("REQUEST -> PUT {}", url);
            restTemplate.put(url, projekt);
            return projekt;
        } else {//utworzenie nowego projektu
// po dodaniu projektu zwracany jest w nagłówku Location - link do utworzonego zasobu
            HttpEntity<Projekt> request = new HttpEntity<>(projekt);
            String url = getUriStringComponent();
            logger.info("REQUEST -> POST {}", url);
            URI location = restTemplate.postForLocation(url, request);
            logger.info("REQUEST (location) -> GET {}", location);
            return restTemplate.getForObject(location, Projekt.class);
// jeżeli usługa miałaby zwracać utworzony obiekt a nie link to trzeba by użyć
// return restTemplate.postForObject(url, projekt, Projekt.class);
        }
    }
    @Override
    public void deleteProjekt(Integer projektId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(projektId))
                .build()
                .toUri();
        logger.info("REQUEST -> DELETE {}", url);
        restTemplate.delete(url);
    }
    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
        logger.info("REQUEST -> GET {}", url);
        return getPage(url, restTemplate);
    }
    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(), pageable)
                .queryParam("nazwa", nazwa)
                .build().toUri();
        logger.info("REQUEST -> GET {}", url);
        return getPage(url, restTemplate);
    }
    // metody pomocnicze
    private Page<Projekt> getPage(URI uri, RestTemplate restTemplate) {
        return ServiceUtil.getPage(uri, restTemplate,
                new ParameterizedTypeReference<RestResponsePage<Projekt>>() {});
    }
    private String getResourcePath() {
        return RESOURCE_PATH;
    }
    private String getResourcePath(Integer id) {
        return RESOURCE_PATH + "/" + id;
    }
    private String getUriStringComponent() {
        return serverUrl + getResourcePath();
    }
    private String getUriStringComponent(Integer id) {
        return serverUrl + getResourcePath(id);
    }
}