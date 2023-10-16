package com.divanov.telrosApp.views.component;

import com.divanov.telrosApp.entities.Role;
import com.divanov.telrosApp.entities.UserApp;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

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

    Binder<UserApp> binder = new BeanValidationBinder<>(UserApp.class);

    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");
    Button deleteButton = new Button("Delete");

    public UserAppEditDataForm(List<Role> roles) {
        addClassName("UserAppEditDataForm");
        binder.bindInstanceFields(this);

        role.setItems(roles);
        role.setItemLabelGenerator(Role::getName);

        add(username, password, firstName, lastName, patronymic, dateOfBirth, email, phone, role, createButtonsLayout());

    }

    public void setUser(UserApp user) {
        binder.setBean(user);
    }

    private HorizontalLayout createButtonsLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        saveButton.addClickListener(event -> validateAndSave());
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));

        binder.addStatusChangeListener(e -> saveButton.setEnabled(binder.isValid()));

        return new HorizontalLayout(saveButton, cancelButton, deleteButton);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    @Getter
    public static abstract class UserAppEditDataFormEvent extends ComponentEvent<UserAppEditDataForm> {
        private UserApp userApp;

        protected UserAppEditDataFormEvent(UserAppEditDataForm source, UserApp userApp) {
            super(source, false);
            this.userApp = userApp;
        }
    }

    public static class SaveEvent extends UserAppEditDataFormEvent {
        SaveEvent(UserAppEditDataForm source, UserApp userApp) {
            super(source, userApp);
        }
    }

    public static class DeleteEvent extends UserAppEditDataFormEvent {
        DeleteEvent(UserAppEditDataForm source, UserApp userApp) {
            super(source, userApp);
        }
    }

    public static class CancelEvent extends UserAppEditDataFormEvent {
        CancelEvent(UserAppEditDataForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
        return addListener(CancelEvent.class, listener);
    }

}
