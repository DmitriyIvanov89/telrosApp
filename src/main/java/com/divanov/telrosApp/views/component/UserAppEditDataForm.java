package com.divanov.telrosApp.views.component;

import com.divanov.telrosApp.entities.Role;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class UserAppEditDataForm extends FormLayout {
    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField patronymic = new TextField("Patronymic");
    TextField dateOfBirth = new TextField("Date Of Birth");
    EmailField email = new EmailField("Email");
    TextField phone = new TextField("Phone");
    ComboBox<Role> role = new ComboBox<>("Role");

    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");
    Button deleteButton = new Button("Delete");

    public UserAppEditDataForm(List<Role> roles) {
        addClassName("UserAppEditDataForm");
        role.setItems(roles);
        role.setItemLabelGenerator(Role::getName);

        add(username, password, firstName, lastName, patronymic, dateOfBirth, email, phone, role, createButtonsLayout());

    }

    private HorizontalLayout createButtonsLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(saveButton, cancelButton, deleteButton);
    }
}
