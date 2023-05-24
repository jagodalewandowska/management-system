package com.project.config;
import com.project.auth.TokenHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    private final TokenHolder tokenHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("Interceptor's request url:" + request.getRequestURL());
                final String token = tokenHolder.getToken();
                log.info("Interceptor's token ({})", token);
                if (token == null || token.isBlank()) {
                    response.sendRedirect(request.getContextPath() + "/app/login");
                    return false;
                }
                return true;
            }
        })
        .addPathPatterns("/app/**") // wyklucza te poniżej, aby nie było zapętlenia
        .excludePathPatterns("/app/login", "/app/register", "/app/logout"); // po wyłączeniu przeglądarki sesja jest niszczona
        // zawsze trzeba się logować
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("Request{");
        log.info(" URI: ", request.getURI());
        log.info(" Method: ", request.getMethod());
        log.info(" Headers: ", request.getHeaders());
        log.info(" Request body: ", new String(body, "UTF-8"));
        log.info("}");
    }

    @Bean
    RestTemplate customRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.additionalInterceptors((request, body, execution) -> {
            final String token = tokenHolder.getToken();
            if (token != null && !token.isBlank()) {
                HttpHeaders headers = request.getHeaders();
                if(!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    final String prefix = "Bearer ";
                    final String bearerToken = token.startsWith(prefix) ? token : prefix;
                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, bearerToken);
                }
            }
            logRequest(request, body);
            return execution.execute(request, body);
        }).build();
    }
}