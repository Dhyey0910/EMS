package com.ems.employee.config;

import com.ems.employee.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider(userDetailsService);

        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .httpBasic(httpBasic -> httpBasic.disable())

                .formLogin(formLogin -> formLogin.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Login
                        .requestMatchers("/auth/login").permitAll()

                        // =========================
                        // EMPLOYEE
                        // =========================

                        .requestMatchers(HttpMethod.GET, "/employees")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/employees/*")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/employees")
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(HttpMethod.PUT, "/employees/*")
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/employees/*/department/*"
                        )
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/employees/*/manager/*"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(HttpMethod.DELETE, "/employees/*")
                        .hasRole("ADMIN")


                        // =========================
                        // DEPARTMENT
                        // =========================

                        .requestMatchers(HttpMethod.GET, "/departments")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/departments/*")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/departments")
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(HttpMethod.PUT, "/departments/*")
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(HttpMethod.DELETE, "/departments/*")
                        .hasRole("ADMIN")


                        // =========================
                        // ATTENDANCE
                        // =========================

                        .requestMatchers(HttpMethod.GET, "/attendances")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/attendances/*")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/attendances")
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(HttpMethod.PUT, "/attendances/*")
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/attendances/*/employee/*"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(HttpMethod.DELETE, "/attendances/*")
                        .hasAnyRole("ADMIN", "HR")


                        // =========================
                        // LEAVE REQUEST
                        // =========================

                        .requestMatchers(HttpMethod.GET, "/leave-requests")
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/leave-requests/*"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/leave-requests"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER", "EMPLOYEE")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/leave-requests/*"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/leave-requests/*/employee/*"
                        )
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/leave-requests/*"
                        )
                        .hasAnyRole("ADMIN", "HR")


                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}