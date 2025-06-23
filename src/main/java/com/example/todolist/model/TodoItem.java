package com.example.todolist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Repräsentiert ein einzelnes ToDo-Element.
 * 
 * Felder:
 * - id: Eindeutige Kennung des Todos
 * - title: Titel des Todos
 * - description: Beschreibung des Todos
 * - completed: Status, ob das Todo erledigt ist
 * - userId: Die ID des Benutzers, dem das Todo gehört
 * 
 * Wird von der Datenbank als Dokument gespeichert.
 * Beispiel:
 * TodoItem todo = new TodoItem("Einkaufen", "Milch und Brot kaufen", false);
 * @see org.springframework.data.mongodb.core.mapping.Document
 * @see org.springframework.data.annotation.Id
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * Dieses Dokument wird in der MongoDB-Datenbank in der Sammlung "todos" gespeichert.
 * @Document(collection = "todos")
 * @Id wird verwendet, um das Feld "id" als eindeutige Kennung für jedes ToDo-Element zu kennzeichnen.
 * @see com.example.todolist.repository.TodoRepository
 */
@Document(collection = "todos")
public class TodoItem {
    
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private boolean completed;

    public TodoItem() {
    }

    public TodoItem(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}