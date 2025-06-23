package com.example.todolist.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.todolist.security.MongoUserDetailsService;
import com.example.todolist.ui.LoginView;

/**
 * Konfiguriert die Sicherheit der Anwendung (Login, Rollen, Zugriffsrechte).
 */
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    private MongoUserDetailsService mongoUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
        );
    }

    protected UserDetailsService userDetailsService() {
        return mongoUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}