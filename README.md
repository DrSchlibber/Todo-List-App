# Todo List App

## Übersicht

Die **Todo List App** ist eine moderne Webanwendung zur Verwaltung persönlicher Aufgaben (Todos).  
Sie basiert auf Java, Spring Boot, Vaadin und Spring Security.  
Jeder Benutzer sieht und verwaltet ausschließlich seine eigenen Todos.  
Administratoren können zusätzlich Benutzer anlegen und bearbeiten.

---

## Features

- **Benutzerauthentifizierung:**  
  Login mit Benutzername und Passwort (Spring Security).
- **Rollenbasiertes System:**  
  Rollen `USER` und `ADMIN` (Admins haben zusätzliche Rechte).
- **Persönliche Todos:**  
  Jeder Benutzer sieht nur seine eigenen Todos.
- **CRUD für Todos:**  
  Todos anlegen, bearbeiten, löschen, als erledigt markieren.
- **Dark Theme:**  
  Standardmäßig dunkles Design, Theme-Umschalter vorhanden.
- **Adminbereich:**  
  Benutzerverwaltung (anlegen, bearbeiten) für Admins.
- **Responsive UI:**  
  Moderne Oberfläche mit Vaadin (AppLayout, Grid, Dialoge, Tabs).

---

## Technologie-Stack

- **Java 17+**
- **Spring Boot**
- **Vaadin Flow**
- **Spring Security**
- **MongoDB** (oder anderes Spring Data Repository)
- **Maven** oder **Gradle**

---

## Projektstruktur

```
src/
 └── main/
     ├── java/
     │    └── com.example.todolist/
     │         ├── model/         // Datenmodelle (User, TodoItem)
     │         ├── repository/    // Datenbank-Repositories
     │         ├── service/       // Geschäftslogik
     │         └── ui/            // Vaadin-UI-Klassen (MainView, LoginView, AdminViews)
     └── resources/
          └── application.properties // Konfiguration
```

---

## Installation & Start

1. **Voraussetzungen:**  
   - Java 17 oder neuer  
   - Maven oder Gradle  
   - MongoDB (läuft lokal oder remote)

2. **Projekt klonen:**  
   ```bash
   git clone <REPO-URL>
   cd todo-list-app
   ```

3. **Konfiguration anpassen:**  
   Passe ggf. die Datenbankverbindung in `src/main/resources/application.properties` an.

4. **Build & Start:**  
   ```bash
   ./mvnw spring-boot:run
   ```
   oder
   ```bash
   ./gradlew bootRun
   ```

5. **Im Browser öffnen:**  
   [http://localhost:8080](http://localhost:8080)

---

## Standard-Benutzer

Lege initiale Benutzer in der Datenbank an oder erweitere das Projekt um einen Initializer.  
Beispiel für einen Admin-Benutzer (MongoDB):

```json
{
  "username": "admin",
  "password": "<bcrypt-hash>",
  "roles": ["ADMIN", "USER"]
}
```

---

## Wichtige Klassen

- **MainView:** Hauptansicht, zeigt Todos, Navigation, Theme-Switch, Logout.
- **LoginView:** Login-Formular.
- **TodoItem:** Datenmodell für ein Todo.
- **User:** Datenmodell für einen Benutzer.
- **TodoService:** Geschäftslogik für Todos.
- **UserService:** Geschäftslogik für Benutzer.
- **TodoRepository:** Datenbankzugriff für Todos.
- **UserRepository:** Datenbankzugriff für Benutzer.

---

## Sicherheit

- Zugriff auf Todos und UI nur für angemeldete Benutzer.
- Admin-Funktionen nur für Benutzer mit Rolle `ADMIN`.
- Passwörter werden verschlüsselt gespeichert.

---

## Anpassungen

- **Theme:**  
  Standardmäßig ist das Dark Theme aktiv.  
  Über den Theme-Switcher kann das Design gewechselt werden.

- **Benutzerverwaltung:**  
  Admins können neue Benutzer anlegen und bestehende bearbeiten.

---

## Weiterentwicklung

- Aufgaben nach Priorität sortieren
- Fälligkeitsdatum für Todos
- E-Mail-Benachrichtigungen
- Mehrsprachigkeit

---

## Lizenz

Dieses Projekt steht unter der MIT-Lizenz.

---

## Kontakt

Fragen, Feedback oder Beiträge?  
Erstelle ein Issue oder kontaktiere den Autor.
