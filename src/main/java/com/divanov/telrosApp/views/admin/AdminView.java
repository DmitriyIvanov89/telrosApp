package com.divanov.telrosApp.views.admin;

import com.divanov.telrosApp.entities.UserApp;
import com.divanov.telrosApp.services.UserService;
import com.divanov.telrosApp.views.MainLayout;
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

/**
 * method addClassName makes it easier to styling components using CSS
 */


@Route(value = "", layout = MainLayout.class)
@PageTitle("Admin dashboard | Telros CRM")
public class AdminView extends VerticalLayout {
    Grid<UserApp> grid = new Grid<>(UserApp.class);
    TextField filterText = new TextField();
    UserAppEditDataForm form;
    AddNewUserDialog dialog;
    UserService service;

    public AdminView(UserService service) {
        this.service = service;
        addClassName("admin-list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        configureDialog();

        add(getToolBar(), getContent());
        updateList();
        closeEditForm();

        grid.asSingleSelect().addValueChangeListener(event ->
                editUserDataForEditForm(event.getValue()));

    }

    private void configureGrid() {
        grid.addClassNames("admin-list-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "patronymic", "dateOfBirth", "email", "phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("First or Last Name");
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
        form.addSaveListener(this::saveUserDataInEditForm);
        form.addDeleteListener(this::deleteUserDataInEditForm);
        form.addCancelListener(e -> closeEditForm());
    }

    private void saveUserDataInEditForm(UserAppEditDataForm.SaveEvent event) {
        service.saveUser(event.getUserApp());
        updateList();
        closeEditForm();
    }

    private void deleteUserDataInEditForm(UserAppEditDataForm.DeleteEvent event) {
        service.deleteUser(event.getUserApp());
        updateList();
        closeEditForm();
    }

    private void closeEditForm() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing-form");
    }

    private void editUserDataForEditForm(UserApp userApp) {
        if (userApp == null) {
            closeEditForm();
        } else {
            form.setUser(userApp);
            form.setVisible(true);
            addClassName("editing-form");
        }
    }

    /**
     * Create and config dialog component
     * only for add new user
     */

    private void configureDialog() {
        dialog = new AddNewUserDialog(service.findAllRoles());
        dialog.addSaveListener(this::saveNewUserInDialog);
    }

    private void saveNewUserInDialog(AddNewUserDialog.SaveEvent event) {
        service.saveUser(event.getUserApp());
        updateList();
        dialog.close();
    }

    private void addNewUser() {
        complicateNewUser(new UserApp());
    }

    private void complicateNewUser(UserApp userApp) {
        if (userApp == null) {
            closeDialog();
        } else {
            dialog.setUser(userApp);
            dialog.setVisible(true);
            addClassName("editing-dialog");
        }
    }

    private void closeDialog() {
        dialog.setUser(null);
        dialog.setVisible(false);
        removeClassName("editing-dialog");
        dialog.close();
    }

    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }
}
