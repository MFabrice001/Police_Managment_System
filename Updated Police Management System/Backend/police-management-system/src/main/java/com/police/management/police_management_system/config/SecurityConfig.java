package com.police.management.police_management_system.config;

import com.police.management.police_management_system.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Bean for password encoding using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for the authentication manager, allowing custom configuration if needed.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defines the security filter chain for handling request authorization, login, and logout.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for the current configuration
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/signup", "/login", "/resources/**", "/static/**").permitAll() // Allow public access to specified paths
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Restrict access to ADMIN role
                        .requestMatchers("/staff/**").hasRole("STAFF")  // Restrict access to STAFF role
                        .requestMatchers("/manager/**").hasRole("MANAGER")  // Restrict access to MANAGER role
                        .anyRequest().authenticated()  // Require authentication for all other paths
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page
                        .defaultSuccessUrl("/dashboard", true)  // Redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Custom logout URL
                        .logoutSuccessUrl("/login?logout")  // Redirect after successful logout
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Exposes the UserService as UserDetailsService for Spring Security.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
}
