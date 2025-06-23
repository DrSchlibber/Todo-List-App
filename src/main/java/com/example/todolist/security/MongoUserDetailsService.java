package com.example.todolist.security;

import com.example.todolist.model.AppUser;
import com.example.todolist.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementierung von UserDetailsService für die Authentifizierung von Benutzern in
 * einer MongoDB-Datenbank.
 * Lädt Benutzerdetails basierend auf dem Benutzernamen und gibt ein UserDetails-Objekt
 * zurück.
 * 
 * @see UserDetailsService
 * @see User
 * @see AppUser
 * @see UserRepository
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * Beispiel:
 * MongoUserDetailsService userDetailsService = ...; // Injected via Spring
 * UserDetails userDetails = userDetailsService.loadUserByUsername("john_doe");
 * * @throws UsernameNotFoundException wenn der Benutzer nicht gefunden wird
 */
@Service
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MongoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Rollen mit ROLE_-Präfix versehen
        List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
        return new User(
                appUser.getUsername(),
                appUser.getPassword(), // Password should be encoded!
                authorities
        );
    }
}