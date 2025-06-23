package com.example.todolist.ui;

import com.example.todolist.model.AppUser;
import com.example.todolist.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.List;

/**
 * Ansicht für Administratoren zum Bearbeiten bestehender Benutzer.
 * Diese Ansicht ermöglicht es, einen Benutzer auszuwählen und dessen Benutzernamen,
 * Passwort und Rollen zu bearbeiten. Die Änderungen werden über den UserService gespeichert.
 * Die Ansicht enthält ein Dropdown-Menü zur Auswahl des Benutzers, Textfelder für den
 * Benutzernamen und das Passwort sowie eine Multi-Select-ComboBox zur Auswahl der
 * Rollen (ADMIN, USER). Nach dem Speichern der Änderungen wird eine Benachrichtigung
 * angezeigt, die den Erfolg der Aktion bestätigt.
 * Die Benutzeroberfläche ist so gestaltet, dass sie einfach zu bedienen ist und eine klare
 * Rückmeldung gibt, ob die Änderungen erfolgreich gespeichert wurden.
 * Die Rolle des Benutzers kann über eine Multi-Select-ComboBox ausgewählt werden,
 * die standardmäßig die Rollen ADMIN und USER enthält. Der Benutzername ist schreibgeschützt
 * und kann nicht geändert werden, um die Integrität der Benutzerdaten zu gewährleisten.
 * Diese Funktionalität ist nützlich für Administratoren, die bestehende Benutzerkonten
 * verwalten und deren Rollen anpassen möchten.
 * Die Ansicht ist so gestaltet, dass sie eine klare und intuitive Benutzererfahrung bietet,
 * mit einem klaren Fokus auf die Bearbeitung von Benutzerdaten. Die Eingabefelder sind
 * so angeordnet, dass sie eine einfache Navigation ermöglichen, und die Schaltfläche zum Speichern
 * der Änderungen ist deutlich sichtbar und leicht zugänglich.
 * @param userService
 * @param onSave
 */
public class UserEditView extends VerticalLayout {

    private final ComboBox<AppUser> userComboBox = new ComboBox<>("Benutzer auswählen");
    private final TextField usernameField = new TextField("Benutzername");
    private final TextField passwordField = new TextField("Neues Passwort");
    private final MultiSelectComboBox<String> rolesBox = new MultiSelectComboBox<>("Rollen");
    private AppUser selectedUser = null;

    public UserEditView(UserService userService, Runnable onSave) {
        setPadding(true);
        setSpacing(true);

        List<AppUser> users = userService.findAll();
        userComboBox.setItems(users);
        userComboBox.setItemLabelGenerator(AppUser::getUsername);

        usernameField.setReadOnly(true);
        rolesBox.setItems(Arrays.asList("ADMIN", "USER"));

        userComboBox.addValueChangeListener(event -> {
            selectedUser = event.getValue();
            if (selectedUser != null) {
                usernameField.setValue(selectedUser.getUsername());
                rolesBox.setValue(selectedUser.getRole());
                passwordField.clear();
            } else {
                usernameField.clear();
                rolesBox.clear();
                passwordField.clear();
            }
        });

        Button saveButton = new Button("Speichern", e -> {
            if (selectedUser == null) {
                Notification.show("Bitte einen Benutzer auswählen.", 2000, Notification.Position.MIDDLE);
                return;
            }
            if (!passwordField.getValue().isEmpty()) {
                selectedUser.setPassword(passwordField.getValue());
            }
            selectedUser.setRole(String.join(",", rolesBox.getValue()));
            userService.save(selectedUser);
            Notification.show("Benutzer gespeichert", 2000, Notification.Position.MIDDLE);
            if (onSave != null) onSave.run();
        });

        add(userComboBox, usernameField, passwordField, rolesBox, saveButton);
    }
}