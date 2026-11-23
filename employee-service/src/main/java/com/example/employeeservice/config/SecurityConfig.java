package com.example.employeeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/actuator/health").permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Read operations
                        .requestMatchers(HttpMethod.GET, "/api/employees/**")
                        .hasAuthority("SCOPE_employee.read")

                        // Write operations
                        .requestMatchers(HttpMethod.POST, "/api/employees/**")
                        .hasAuthority("SCOPE_employee.write")

                        .requestMatchers(HttpMethod.PUT,"/api/employees/**"
                        ).hasAuthority("SCOPE_employee.write")

                        .requestMatchers(HttpMethod.PATCH,"/api/employees/**"
                        ).hasAuthority("SCOPE_employee.write")

                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**"
                        ).hasAuthority("SCOPE_employee.write")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}