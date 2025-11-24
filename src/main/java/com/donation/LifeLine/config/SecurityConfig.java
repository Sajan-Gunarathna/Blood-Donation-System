package com.donation.LifeLine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/donor/register", "/thank-you", "/api/**").permitAll()
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/donor/**").hasRole("DONOR")
                        .requestMatchers("/registration-officer/**").hasRole("REGISTRATION_OFFICER")
                        .requestMatchers("/medical-adviser/**").hasRole("MEDICAL_ADVISER")
                        .requestMatchers("/hospital-coordinator/**").hasRole("HOSPITAL_COORDINATOR")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(roleBasedSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    String role = authority.getAuthority();
                    switch (role) {
                        case "ROLE_DONOR":
                            response.sendRedirect("/donor/dashboard");
                            return;
                        case "ROLE_REGISTRATION_OFFICER":
                            response.sendRedirect("/registration-officer/dashboard");
                            return;
                        case "ROLE_MEDICAL_ADVISER":
                            response.sendRedirect("/medical-adviser/dashboard");
                            return;
                        case "ROLE_ADMIN":
                            response.sendRedirect("/api/admin/dashboard");
                            return;
                        case "ROLE_HOSPITAL_COORDINATOR":
                            response.sendRedirect("/hospital-coordinator/dashboard");
                            return;

                    }
                }
                // Fallback redirect if no specific role is found
                response.sendRedirect("/dashboard");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}