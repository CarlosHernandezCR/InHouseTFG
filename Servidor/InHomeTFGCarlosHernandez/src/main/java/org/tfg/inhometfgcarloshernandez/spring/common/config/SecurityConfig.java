package org.tfg.inhometfgcarloshernandez.spring.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(POST, ConstantesServer.LOGINPATH).permitAll()
                            .requestMatchers(GET, ConstantesServer.CASAPATH+ConstantesServer.PANTALLA_CASA).permitAll()
                            .requestMatchers(POST, ConstantesServer.CASAPATH+ConstantesServer.CAMBIAR_ESTADO).permitAll()
                            .requestMatchers(GET, ConstantesServer.CALENDARIOPATH+ConstantesServer.GET_EVENTOS_MES).permitAll()
                            .requestMatchers(GET, ConstantesServer.CALENDARIOPATH+ConstantesServer.GET_EVENTOS_DIA).permitAll()
                            .requestMatchers(POST, ConstantesServer.CALENDARIOPATH+ConstantesServer.CREAR_EVENTO).permitAll()
                            .requestMatchers(GET, ConstantesServer.INMUEBLESPATH+ConstantesServer.PANTALLA_INMUEBLES).permitAll()
                            .requestMatchers(GET, ConstantesServer.INMUEBLESPATH+ConstantesServer.GET_USUARIOS).permitAll()
                            .requestMatchers(POST, ConstantesServer.INMUEBLESPATH+ConstantesServer.AGREGAR_HABITACION).permitAll()
                            .requestMatchers(POST, ConstantesServer.INMUEBLESPATH+ConstantesServer.AGREGAR_MUEBLE).permitAll()
                            .requestMatchers(POST, ConstantesServer.INMUEBLESPATH+ConstantesServer.AGREGAR_CAJON).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}