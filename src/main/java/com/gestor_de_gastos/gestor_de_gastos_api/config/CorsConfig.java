package com.gestor_de_gastos.gestor_de_gastos_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // libera todos os endpoints
                        .allowedOrigins("http://localhost:5173") // frontend local
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // métodos permitidos
                        .allowedHeaders("*") // headers permitidos
                        .allowCredentials(true); // se precisar mandar cookies/autenticação
            }
        };
    }
}
