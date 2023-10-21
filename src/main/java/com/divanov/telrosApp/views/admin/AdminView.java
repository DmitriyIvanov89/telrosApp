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
import lombok.extern.slf4j.Slf4j;

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
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "patronymic", "dateOfBirth", "email", "phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editUserData(event.getValue()));

    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("First or Last name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addNewUserButton = new Button("New user", e -> dialog.open());
        addNewUserButton.addClickListener(e -> addNewUser());

        var toolbar = new HorizontalLayout(filterText, addNewUserButton);

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
        form = new UserAppEditDataForm(service.findAllRoles());
        form.setWidth("25em");
        form.addSaveListener(this::saveUserForm);
        form.addDeleteListener(this::deleteUserForm);
        form.addCancelListener(e -> closeEditor());
    }

    private void saveUserForm(UserAppEditDataForm.SaveEvent event) {
        service.saveUser(event.getUserApp());
        updateList();
        closeEditor();
    }

    private void deleteUserForm(UserAppEditDataForm.DeleteEvent event) {
        service.deleteUser(event.getUserApp());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editUserData(UserApp userApp) {
        if (userApp == null) {
            closeEditor();
        } else {
            form.setUser(userApp);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    /**
     * Create and config dialog component
     * only fot add new user
     */

    private void configureDialog() {
        dialog = new AddNewUserDialog(service.findAllRoles());
        dialog.addListener(AddNewUserDialog.SaveEvent.class, this::saveNewUserDialog);
    }

    private void saveNewUserDialog(AddNewUserDialog.SaveEvent event) {
        service.saveUser(event.getUserApp());
        updateList();
        dialog.close();
    }

    private void addNewUser() {
        complicateNewUser(new UserApp());
    }

    private void complicateNewUser(UserApp userApp) {
        if (userApp == null) {
            dialog.setUser(null);
            dialog.close();
        } else {
            dialog.setUser(userApp);
            dialog.setVisible(true);
        }
    }

    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }
}
