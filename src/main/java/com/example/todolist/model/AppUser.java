package com.example.todolist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Repräsentiert einen Benutzer der Anwendung.
 * 
 * Felder:
 * - id: Eindeutige Kennung des Benutzers
 * - username: Benutzername
 * - password: Passwort (verschlüsselt)
 * - roles: Liste der Rollen des Benutzers (z.B. USER, ADMIN)
 * 
 * Wird von der Datenbank als Dokument gespeichert.
 * Verwendung:
 * - Authentifizierung und Autorisierung von Benutzern
 * - Verwaltung von Benutzerinformationen
 * Beispiel:
 * AppUser user = new AppUser();
 * user.setUsername("john_doe");
 * user.setPassword("hashed_password");
 * user.setRole("USER");
 * // UserRepository userRepository = ...; // Injected via Spring
 * // userRepository.save(user);
 * @see org.springframework.data.mongodb.core.mapping.Document
 * @see org.springframework.data.annotation.Id
 * @see com.example.todolist.repository.UserRepository
 * @author Your Name
 * @version 1.0
 * @since 1.0   
 */
@Document(collection = "users")
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password; // Store hashed passwords in production
    private String role;

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

}