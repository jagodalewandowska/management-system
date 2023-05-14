package com.project.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    // dzięki adnotacji @Bean Spring uruchomi metodę i zarejestruje w kontenerze obiekt przez nią
    // zwrócony, natomiast adnotacja @Autowired użyta w innej klasie spowoduje jego wstrzyknięcie
    @Bean
    RestTemplate customRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication("admin", "admin").build();
    }
}
// login i hasło można przechowywać w pliku np. application.properties