package com.serviciosRosario.ServiciosRosario.security.config;

import com.serviciosRosario.ServiciosRosario.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {

                    // Rutas de autenticación
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/auth/authenticate").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/auth/validate").permitAll();

                    // Rutas privadas (requieren autenticación)
                    auth.requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "SUPERADMIN");
                    auth.requestMatchers("/api/v1/**").authenticated();

                    // Cualquier otra petición requiere autenticación
                    auth.anyRequest().authenticated();

                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http:" +
                "" +
                "" +
                "" +
                "//localhost:5173")); // Origen permitido (front)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Headers permitidos
        configuration.setAllowCredentials(true); // Permitir credenciales (cookies, tokens)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar a todas las rutas
        return source;
    }


}
