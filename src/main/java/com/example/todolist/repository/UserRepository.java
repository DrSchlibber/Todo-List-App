package com.example.todolist.repository;

import com.example.todolist.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository-Interface für den Zugriff auf Benutzer in der Datenbank.
 * Bietet Methoden zum Finden von Benutzern anhand ihres Benutzernamens.
 * Erweitert MongoRepository, um CRUD-Operationen zu ermöglichen.
 * Beispiel:
 * UserRepository userRepository = ...; // Injected via Spring
 * Optional<AppUser> user = userRepository.findByUsername("john_doe");
 * @see AppUser
 * @see MongoRepository
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
}