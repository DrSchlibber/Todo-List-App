package com.example.todolist.config;

import com.example.todolist.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Legt beim Start der Anwendung einen Standard-Benutzer an,
 * falls dieser noch nicht existiert.
 */
@Component
public class InitialUserLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        String username = "admin";
        String password = "adminpass";
        String role = "ADMIN";

        // Prüfen, ob der Benutzer schon existiert
        if (userService.findByUsername(username).isEmpty()) {
            userService.createUser(username, password, role);
            System.out.println("Admin-Benutzer wurde angelegt.");
        } else {
            System.out.println("Admin-Benutzer existiert bereits.");
        }
    }
}