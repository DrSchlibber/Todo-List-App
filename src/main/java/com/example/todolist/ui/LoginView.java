package com.example.todolist.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * LoginView stellt die Login-Oberfläche für die Anwendung bereit.
 * Ermöglicht Benutzern das Anmelden mit Benutzername und Passwort.
 * Setzt das Theme standardmäßig auf "dark".
 * Bei erfolgreichem Login wird zur MainView weitergeleitet.
 * Verwendet Vaadin's LoginOverlay-Komponente für die Benutzeroberfläche.
 * Beispiel:
 * LoginView loginView = new LoginView();
 * loginView.setAction("login");
 * loginView.setTitle("Todo List App");
 * loginView.setDescription("Please log in to continue.");
 * loginView.setOpened(true);
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * Diese Klasse ist für die Anzeige der Login-Seite zuständig und sollte nicht direkt von Controllern
 * aufgerufen werden. Stattdessen wird sie von Vaadin's Router verwendet, um die Login-Seite anzuzeigen,
 * wenn der Benutzer nicht angemeldet ist. Sie ist für die Anzeige des Login-Overlays verantwortlich
 * und ermöglicht es Benutzern, sich mit ihren Anmeldedaten einzuloggen.
 * Nach erfolgreichem Login wird der Benutzer zur MainView weitergeleitet, die die Hauptanwendung darstellt.
 * Die LoginView ist mit der Route "login" verbunden und kann von jedem Benutzer aufgerufen werden,
 * auch wenn er nicht angemeldet ist (AnonymousAllowed).
 * Sie verwendet Vaadin's LoginOverlay-Komponente, um eine ansprechende und benutzerfreundliche
 * Login-Oberfläche bereitzustellen. Das Theme wird standardmäßig auf "dark" gesetzt,
 * um ein modernes Aussehen zu gewährleisten.
 */
@Route("login")
@PageTitle("Login | Todo List")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        UI.getCurrent().getPage().executeJs("document.body.setAttribute('theme','dark');");
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setAction("login");
        loginOverlay.setTitle("Todo List App");
        loginOverlay.setDescription("Please log in to continue.");
        loginOverlay.setOpened(true);
        add(loginOverlay);
    }

}