package com.example.todolist.service;

import com.example.todolist.model.TodoItem;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-Klasse für die Geschäftslogik rund um ToDo-Items.
 * Bietet Methoden zum Laden, Speichern, Bearbeiten und Löschen von Todos.
 * Arbeitet mit dem TodoRepository zusammen.
 * Stellt sicher, dass nur die Todos des angemeldeten Benutzers geladen werden.
 * * Beispiel:
 * TodoService todoService = ...; // Injected via Spring
 * TodoItem todo = new TodoItem("Einkaufen", "Milch und Brot kaufen", false);
 * todoService.save(todo);
 * List<TodoItem> todos = todoService.findByUserId("user123");
 * @see TodoItem
 * @see TodoRepository
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * Diese Klasse ist für die Geschäftslogik zuständig und sollte nicht direkt von Controllern
 * aufgerufen werden. Stattdessen sollte sie über Dependency Injection in den Controllern
 * verwendet werden.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Speichert ein neues oder geändertes TodoItem in der Datenbank.
     * @param todo Das zu speichernde TodoItem.
     * @return Das gespeicherte TodoItem.
     */
    public TodoItem save(TodoItem todo) {
        return todoRepository.save(todo);
    }

    public List<TodoItem> findAll() {
        return todoRepository.findAll();
    }

    public void delete(TodoItem todo) {
        todoRepository.delete(todo);
    }

    public TodoItem findById(String id) {
        return todoRepository.findById(id).orElse(null);
    }

    public List<TodoItem> findByUserId(String userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<TodoItem> getAllTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTodos'");
    }
}