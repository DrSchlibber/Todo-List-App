package com.example.todolist.ui;

import com.example.todolist.model.TodoItem;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.Icon;

/**
 * Die MainView ist die Hauptansicht der Anwendung.
 * Sie zeigt die ToDo-Liste des aktuell angemeldeten Benutzers an,
 * ermöglicht das Anlegen, Bearbeiten und Löschen von Todos,
 * bietet eine Navigation, einen Theme-Umschalter und Logout.
 * 
 * Zugänglich für Benutzer mit den Rollen USER oder ADMIN.
 * 
 * Abhängigkeiten:
 * - TodoService: Zugriff auf die ToDo-Datenbank
 * - UserService: Zugriff auf Benutzerdaten und Rollen
 * 
 * UI-Technologie: Vaadin (AppLayout, Grid, Dialoge, Buttons etc.)
 * Sicherheitskontext: Spring Security für Authentifizierung und Autorisierung
 * Beispiel:
 * MainView mainView = new MainView(todoService, userService);
 * mainView.setUserId("user123");
 * mainView.updateGrid();
 * @author Sascha
 * @version 1.0
 * @since 1.0
 */
@Route("")
@RolesAllowed({"USER", "ADMIN"})
public class MainView extends AppLayout {

    private final TodoService todoService;
    private final Grid<TodoItem> grid = new Grid<>(TodoItem.class);
    private String userId;

    public MainView(TodoService todoService, UserService userService) {
        UI.getCurrent().getPage().executeJs("document.body.setAttribute('theme','dark');");
        this.todoService = todoService;
        // Hole den aktuellen Benutzer aus dem SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : null; // Username als userId
        this.userId = userId;

        // Header
        H1 logo = new H1("Todo List");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        // Theme-Umschalter-Button im Header
        Button themeToggle = new Button(new Icon(VaadinIcon.ADJUST));
        themeToggle.setTooltipText("Design wechseln");
        themeToggle.addClickListener(e -> {
            UI ui = UI.getCurrent();
            if (ui != null) {
                ui.getPage().executeJs(
                    "const b=document.body;" +
                    "if(b.hasAttribute('theme') && b.getAttribute('theme').includes('dark')){" +
                    "  b.setAttribute('theme','');" +
                    "}else{" +
                    "  b.setAttribute('theme','dark');" +
                    "}"
                );
            } else {
                Notification.show("UI nicht verfügbar!", 3000, Notification.Position.MIDDLE);
            }
        });

        // Benutzername und Rolle anzeigen
        
        String username = authentication != null ? authentication.getName() : "Unbekannt";
        String role = authentication != null && authentication.getAuthorities().stream().findFirst().isPresent()
                ? authentication.getAuthorities().stream().findFirst().get().getAuthority()
                : "Keine Rolle";

        Span userInfo = new Span(username + " (" + role + ")");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, userInfo,themeToggle);
        header.setWidth("100%");
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        header.setPadding(true);
        addToNavbar(header);

        // Drawer (Navigation)
        Icon todosIcon = VaadinIcon.LIST.create();
        todosIcon.getStyle().set("margin-right", "0.5em");
        Span todosText = new Span("Todos");
        todosText.getStyle().set("font-size", "1.1em").set("font-weight", "500");

        RouterLink mainLink = new RouterLink(MainView.class);
        mainLink.add(todosIcon, todosText);
        mainLink.getStyle().set("padding", "0.5em 1.2em").set("border-radius", "var(--lumo-border-radius-m)")
            .set("transition", "background 0.2s").set("text-decoration", "none");
        mainLink.addClassName("menu-link");

        // Optional: Hover-Effekt per CSS
        mainLink.getElement().getStyle().set("cursor", "pointer");

        Button logout = new Button("Logout", event -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI ui = UI.getCurrent();
            if (ui != null) {
                ui.access(() -> ui.getPage().setLocation("/login"));
            }
        });

        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Button adminButton = null;
        
        // Admin-Button unter dem Logout-Button
        if (isAdmin) {
            adminButton = new Button("Adminbereich");
            adminButton.addClickListener(e -> {
                Dialog adminDialog = new Dialog();
                adminDialog.setHeaderTitle("Adminbereich");
                Tab tab1=new Tab("Benutzer anlegen");
                Tab tab2=new Tab("Benutzer bearbeiten");

                Div content1 = new Div();
                content1.add(new UserCreateView(userService));
                Div content2 = new Div();
                content2.add(new UserEditView(userService, null));

                Div pages = new Div(content1, content2);
                content2.setVisible(false);
                Tabs tabs = new Tabs(tab1, tab2);

                tabs.addSelectedChangeListener(event -> {
                    content1.setVisible(tabs.getSelectedTab() == tab1);
                    content2.setVisible(tabs.getSelectedTab() == tab2);
                });
                //VerticalLayout layout = new VerticalLayout(tabs, pages);
                //adminDialog.add(new UserCreateView(userService));
                adminDialog.add(tabs, pages);
                adminDialog.setWidth("400px");
                adminDialog.setHeight("auto");
                adminDialog.open();
            });
        }

        // Menü (Drawer)
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setSizeFull();
        drawerLayout.setPadding(true); // Mehr Abstand innen
        drawerLayout.setSpacing(true); // Mehr Abstand zwischen Elementen
        drawerLayout.setAlignItems(Alignment.CENTER); // Zentriert die Links

        drawerLayout.add(mainLink);

        // Logout-Button immer unten
        VerticalLayout logoutWrapper = new VerticalLayout();
        logoutWrapper.add(logout);
        if (adminButton != null) {
            logoutWrapper.add(adminButton);
        }
        logoutWrapper.setPadding(true);
        logoutWrapper.setJustifyContentMode(JustifyContentMode.END);
        logoutWrapper.setAlignItems(Alignment.STRETCH);
        logoutWrapper.setSizeFull();

        drawerLayout.add(logoutWrapper);
        drawerLayout.setFlexGrow(1, logoutWrapper);

        addToDrawer(drawerLayout);

        // Dialog für neues Todo
        Dialog addTodoDialog = createAddTodoDialog();

        // Add Todo Button öffnet das Dialog
        Button addButton = new Button("Add Todo", e -> addTodoDialog.open());
        addButton.getStyle().set("margin-bottom", "1em");

        // Content schöner gestalten
        VerticalLayout content = new VerticalLayout(addButton, grid);
        content.setPadding(true); // Mehr Abstand innen
        content.setSpacing(true); // Mehr Abstand zwischen Button und Grid
        content.setAlignItems(Alignment.STRETCH);
        content.setWidthFull();
        content.getStyle().set("max-width", "700px").set("margin", "auto"); // Zentriert den Content

        setContent(content);

        configureGrid();
        updateGrid();
    }

    /**
     * Konfiguriert die Grid-Komponente für die Anzeige der Todos.
     * - Spalten: Titel, Beschreibung, Erledigt-Status
     * - Aktionen: Editieren und Löschen von Todos
     * - Erledigt-Status wird als farbiger Punkt angezeigt (grün für erledigt, rot für nicht erledigt)
     * - Editieren öffnet einen Dialog zum Bearbeiten des Todos
     * - Löschen öffnet einen Bestätigungsdialog
     *   und löscht das Todo bei Bestätigung
     */
    private void configureGrid() {
        grid.setColumns( "title", "description", "completed");

        grid.addComponentColumn(todo -> {
            Icon dot = VaadinIcon.CIRCLE.create();
            if (todo.isCompleted()) {
                dot.getStyle().set("color", "var(--lumo-success-color)");
            } else {
                dot.getStyle().set("color", "var(--lumo-error-color)");
            }
            dot.setSize("1em");
            return dot;
        }).setHeader("Erledigt").setAutoWidth(true);

        grid.addComponentColumn(todo -> {
            Button edit = new Button(new Icon(VaadinIcon.EDIT));
            edit.addClickListener(e -> openEditDialog(todo, userId));

            Button delete = new Button(new Icon(VaadinIcon.TRASH));
            // Bestätigungsdialog für das Löschen
            delete.addClickListener(e -> {

                Dialog confirmDialog = new Dialog();
                confirmDialog.add(new Span("Möchtest du dieses Todo wirklich löschen?"));

                Button yes = new Button("Ja", event -> {
                    todoService.delete(todo);
                    updateGrid();
                    Notification.show("Todo deleted");
                    confirmDialog.close();
                });
                Button no = new Button("Abbrechen", event -> confirmDialog.close());

                confirmDialog.add(new HorizontalLayout(yes, no));
                confirmDialog.open();
            });

            HorizontalLayout actions = new HorizontalLayout(edit, delete);
            return actions;
        }).setHeader("Actions");
    }

    // Aktualisiert die Grid-Daten
    // Diese Methode wird aufgerufen, wenn ein Todo erstellt oder bearbeitet wird
    private void updateGrid() {
        List<TodoItem> todos = todoService.findByUserId(userId);
        grid.setItems(todos);
    }

    /**
     * Öffnet einen Dialog zum Bearbeiten eines bestehenden Todos.
     * Der Dialog enthält Felder für Titel, Beschreibung und Status,
     * sowie Buttons zum Speichern und Abbrechen.
     * @param todo
     * @param userId
     */
    private void openEditDialog(TodoItem todo, String userId) {
        Dialog editTodoDialog = new Dialog();
        editTodoDialog.setHeaderTitle("Todo bearbeiten");

        TextField titleField = new TextField("Titel");
        titleField.setWidthFull();
        titleField.setValue(todo.getTitle() != null ? todo.getTitle() : "");

        // TextArea für die Beschreibung
        TextArea descriptionField = new TextArea("Beschreibung");
        descriptionField.setWidthFull();
        descriptionField.setValue(todo.getDescription() != null ? todo.getDescription() : "");

        // Checkbox für den Status
        Checkbox completedCheckbox = new Checkbox("Abgeschlossen");
        completedCheckbox.setValue(Boolean.TRUE.equals(todo.isCompleted()));

        Button saveButton = new Button("Speichern", event -> {
            // Hole den Titel und die Beschreibung aus den Textfeldern
            String title = titleField.getValue();

            String description = descriptionField.getValue();
            // Setze den Status des Todos
            todo.setCompleted(completedCheckbox.getValue());
            todoService.save(todo);
            if (!title.isEmpty()) {
                todo.setTitle(title);
                todo.setDescription(description);
                todo.setUserId(userId);
                todoService.save(todo);
                Notification.show("Todo aktualisiert!");
                editTodoDialog.close();
                updateGrid();
            } else {
                Notification.show("Titel darf nicht leer sein!", 3000, Notification.Position.MIDDLE);
            }
        });
        Button cancelButton = new Button("Abbrechen", e -> editTodoDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        VerticalLayout formLayout = new VerticalLayout(
            titleField,
            descriptionField,
            completedCheckbox,
            buttonLayout
        );
        formLayout.setPadding(true);
        formLayout.setSpacing(true);
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setWidth("350px");

        editTodoDialog.add(formLayout);
        editTodoDialog.open();
    }

    /**
     * Erstellt einen Dialog zum Anlegen eines neuen Todos.
     * Der Dialog enthält Felder für Titel und Beschreibung,
     * sowie Buttons zum Speichern und Abbrechen.
     * @return
     */
    private Dialog createAddTodoDialog() {
        Dialog addTodoDialog = new Dialog();
        addTodoDialog.setHeaderTitle("Neues Todo anlegen");

        TextField titleField = new TextField("Titel");
        titleField.setWidthFull();
        TextArea descriptionField = new TextArea("Beschreibung");
        descriptionField.setWidthFull();

        Button saveButton = new Button("Speichern", event -> {
            String title = titleField.getValue();
            String description = descriptionField.getValue();
            if (!title.isEmpty()) {
                TodoItem newTodo = new TodoItem();
                newTodo.setTitle(title);
                newTodo.setDescription(description);
                newTodo.setUserId(userId);
                todoService.save(newTodo);
                Notification.show("Todo angelegt!");
                addTodoDialog.close();
                updateGrid();
                titleField.clear();
                descriptionField.clear();
            } else {
                Notification.show("Titel darf nicht leer sein!", 3000, Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Abbrechen", e -> addTodoDialog.close());
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        VerticalLayout formLayout = new VerticalLayout(titleField, descriptionField, buttonLayout);
        formLayout.setPadding(true);
        formLayout.setSpacing(true);
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setWidth("350px");

        addTodoDialog.add(formLayout);
        return addTodoDialog;
    }
}