package com.divanov.telrosApp.views.admin;

import com.divanov.telrosApp.entities.UserApp;
import com.divanov.telrosApp.services.UserService;
import com.divanov.telrosApp.views.component.AddNewUserDialog;
import com.divanov.telrosApp.views.component.UserAppEditDataForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

/**
 * method addClassName makes it easier to styling components using CSS
 */

@Route(value = "")
@PageTitle("Admin dashboard")
public class AdminView extends VerticalLayout {
    Grid<UserApp> grid = new Grid<>(UserApp.class);
    TextField filterText = new TextField();
    UserAppEditDataForm form;
    AddNewUserDialog dialog;
    UserService service;

    public AdminView(UserService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        configureDialog();

        add(getToolBar(), getContent());
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "patronymic", "dateOfBirth", "email", "phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
//        grid.asSingleSelect().addValueChangeListener(event -> editUserData(event.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("First or Last name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("New user", e -> dialog.open());

        var toolbar = new HorizontalLayout(filterText, addContactButton);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new UserAppEditDataForm(Collections.emptyList());
        form.setWidth("25em");
    }

    private void configureDialog() {
        dialog = new AddNewUserDialog(Collections.emptyList());
    }
}
