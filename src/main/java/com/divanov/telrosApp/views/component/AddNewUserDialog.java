package com.divanov.telrosApp.views.component;

import com.divanov.telrosApp.entities.Role;
import com.divanov.telrosApp.entities.UserApp;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class AddNewUserDialog extends Dialog {
    TextField userName = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField patronymic = new TextField("Patronymic");
    EmailField email = new EmailField("Email");
    TextField dateOfBirth = new TextField("Date of Birth");
    TextField phone = new TextField("Phone");
    ComboBox<Role> role = new ComboBox<>("Role");

    Binder<UserApp> binder = new BeanValidationBinder<>(UserApp.class);

    public AddNewUserDialog(List<Role> roles) {
        setClassName("addNewUserDialog");
        setHeaderTitle("Add New User");
        binder.bindInstanceFields(this);
        role.setItems(roles);
        role.setItemLabelGenerator(Role::getName);
        getFooter().add(createSaveButton());
        getFooter().add(createCancelButton(this));
        setResizable(true);
        setDraggable(true);
        add(createLayout());
    }

    private FormLayout createLayout() {
        return new FormLayout(userName, password, firstName, lastName, patronymic, email, dateOfBirth, phone, role);
    }

    private Button createSaveButton() {
        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());
        return save;
    }

    private Button createCancelButton(Dialog dialog) {
        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addClickShortcut(Key.ESCAPE);
        return cancel;
    }

    public void setUserApp(UserApp userApp) {
        binder.setBean(userApp);
    }

    private void validateAndSave() {
        UserApp userApp = new UserApp();
        userApp.setUsername(userName.getValue());
        userApp.setPassword(password.getValue());
        userApp.setPatronymic(patronymic.getValue());
        userApp.setDateOfBirth(dateOfBirth.getValue());
        userApp.setEmail(email.getValue());
        userApp.setPhone(phone.getValue());
        userApp.setRoles(Collections.emptyList());

        try {
            binder.writeBean(userApp);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        fireEvent(new SaveEvent(this, userApp));
    }

    //Events
    @Getter
    public static abstract class AddNewUserDialogEvent extends ComponentEvent<AddNewUserDialog> {
        private final UserApp userApp;

        protected AddNewUserDialogEvent(AddNewUserDialog source, UserApp userApp) {
            super(source, false);
            this.userApp = userApp;
        }
    }

    public static class SaveEvent extends AddNewUserDialogEvent {
        SaveEvent(AddNewUserDialog source, UserApp userApp) {
            super(source, userApp);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
}

