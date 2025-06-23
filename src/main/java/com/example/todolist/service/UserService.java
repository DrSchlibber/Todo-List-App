package com.example.todolist.service;

import com.example.todolist.model.AppUser;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse für die Geschäftslogik rund um Benutzer.
 * Bietet Methoden zur Benutzerverwaltung, z.B. Anlegen, Suchen, Rollen prüfen.
 * Arbeitet mit dem UserRepository zusammen.
 * Beispiel:
 * UserService userService = ...; // Injected via Spring
 * boolean success = userService.createUser("john_doe", "password123", "USER");
 * @see UserRepository
 * @see AppUser
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * * Diese Klasse ist für die Verwaltung von Benutzern in der Anwendung zuständig.
 * Sie ermöglicht das Anlegen neuer Benutzer, die Suche nach Benutzern anhand ihres Benutzernamens
 * und die Verwaltung von Benutzerrollen. Die Passwörter werden sicher verschlüsselt gespeichert.
 * Die Klasse nutzt Spring's Dependency Injection, um das UserRepository und den PasswordEncoder
 * zu verwenden. Sie sollte nicht direkt von Controllern aufgerufen werden, sondern über
 * Dependency Injection in den Controllern verwendet werden.
 * Diese Klasse ist für die Geschäftslogik zuständig und sollte nicht direkt von Controllern
 * aufgerufen werden. Stattdessen sollte sie über Dependency Injection in den Controllern
 * verwendet werden.
 * Sie stellt sicher, dass nur gültige Benutzer erstellt werden können und bietet eine einfache
 * Schnittstelle zur Benutzerverwaltung.
*/
@Service
public class UserService {
    private String username;
    private String password;
    private String role;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean createUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return true;
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<AppUser> findAll() {  
        return userRepository.findAll();
    }

    public void save(AppUser user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}