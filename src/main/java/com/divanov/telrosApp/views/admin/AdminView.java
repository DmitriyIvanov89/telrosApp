package com.divanov.telrosApp.views.admin;

import com.divanov.telrosApp.entities.UserApp;
import com.divanov.telrosApp.services.UserService;
import com.divanov.telrosApp.views.component.UserAppDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * method addClassName makes it easier to styling components using CSS
 */

@Route(value = "")
@PageTitle("Admin dashboard")
public class AdminView extends VerticalLayout {
    Grid<UserApp> grid = new Grid<>(UserApp.class);
    TextField filterText = new TextField();
    UserAppDialog dialog;
    UserService service;

    public AdminView(UserService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureDialog();

        add(getToolBar(), grid);
        updateList();
        clearDialog();
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "patronymic", "dateOfBirth", "email", "phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editUserData(event.getValue()));
    }

    private void configureDialog() {
        dialog = new UserAppDialog(service.findAllRoles());
        dialog.setDraggable(true);
        dialog.setResizable(true);
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("First or Last name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("New user", e -> addUser());

        var toolbar = new HorizontalLayout(filterText, addContactButton);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void editUserData(UserApp userApp) {
        if (userApp == null) {
            clearDialog();
        }
        dialog.setUserApp(userApp);
        dialog.open();
        addClassName("editing");
    }

    private void clearDialog() {
        dialog.setUserApp(null);
        dialog.close();
        removeClassName("editing");
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUserData(new UserApp());
    }


    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }
}
