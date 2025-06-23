package com.example.todolist.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.todolist.model.TodoItem;

/**
 * Repository-Interface für den Zugriff auf ToDo-Items in der Datenbank.
 * Bietet Methoden zum Suchen, Speichern und Löschen von Todos.
 * Speziell:
 * - findByUserId(String userId): Liefert alle Todos eines bestimmten Benutzers.
 * Nutzt Spring Data MongoDB.
 * Beispiel:
 * TodoRepository todoRepository = ...; // Injected via Spring
 * List<TodoItem> todos = todoRepository.findByUserId("user123");
 * @see TodoItem
 * @see MongoRepository
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public interface TodoRepository extends MongoRepository<TodoItem, String> {
    List<TodoItem> findByUserId(String userId);
}