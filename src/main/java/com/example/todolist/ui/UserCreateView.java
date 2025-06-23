package com.example.todolist.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import com.example.todolist.service.UserService;

/**
 * Ansicht für Administratoren zum Anlegen neuer Benutzer mit Rolle USER oder ADMIN.
 * Diese Ansicht enthält ein Formular mit Feldern für den Benutzernamen, das Passwort und die Rolle.
 * Nach dem Absenden des Formulars wird der Benutzer über den Erfolg oder Misserfolg der
 * Erstellung informiert.
 * Die Rolle des Benutzers wird standardmäßig auf USER gesetzt, kann aber auf ADMIN geändert werden.
 * Nur Benutzer mit der Rolle ADMIN können auf diese Ansicht zugreifen.
 * Die Benutzer werden über den UserService verwaltet, der die Logik zur Erstellung von Benutzern enthält.
 * Die Ansicht ist so gestaltet, dass sie einfach zu bedienen ist und eine klare Rückmeldung gibt,
 * ob der Benutzer erfolgreich angelegt wurde oder ob der Benutzername bereits existiert.
 * Die Benutzeroberfläche ist responsiv und zentriert die Eingabefelder und den Button.
 * Die Eingabefelder sind so gestaltet, dass sie eine einfache und intuitive Benutzererfahrung bieten.
 * Die Rolle des Benutzers kann über eine Dropdown-Liste ausgewählt werden,
 * die standardmäßig auf USER gesetzt ist, aber auch auf ADMIN geändert werden kann.
 * Diese Funktionalität ist nützlich für Administratoren, die neue Benutzerkonten erstellen
 * und ihnen entsprechende Rollen zuweisen möchten.
 */
@Route("admin/create-user")
@PageTitle("Benutzer anlegen")
@RolesAllowed("ADMIN")
public class UserCreateView extends VerticalLayout {

    public UserCreateView(UserService userService) {
        TextField username = new TextField("Benutzername");
        PasswordField password = new PasswordField("Passwort");
        ComboBox<String> role = new ComboBox<>("Rolle");
        role.setItems("USER", "ADMIN");
        role.setValue("USER");

        Button create = new Button("Benutzer anlegen", event -> {
            boolean success = userService.createUser(username.getValue(), password.getValue(), role.getValue());
            if (success) {
                Notification.show("Benutzer erfolgreich angelegt!");
                username.clear();
                password.clear();
                role.setValue("USER");
            } else {
                Notification.show("Benutzername existiert bereits!", 3000, Notification.Position.MIDDLE);
            }
        });
        add(username, password, role, create);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
