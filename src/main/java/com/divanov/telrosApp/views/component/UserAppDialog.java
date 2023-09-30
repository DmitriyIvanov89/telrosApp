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
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.List;

public class UserAppDialog extends Dialog {
    //TO DO вынести нициализацию полей в метод createLayout();
    TextField userName = new TextField("Username");
    PasswordField password = new PasswordField("Tmp user password");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField patronymic = new TextField("Patronymic");
    EmailField email = new EmailField("Email");
    TextField dateOfBirth = new TextField("Date of Birth");
    TextField phone = new TextField("Phone");
    ComboBox<Role> role = new ComboBox<>("Role");
    Binder<UserApp> binder = new BeanValidationBinder<>(UserApp.class);

    public UserAppDialog(List<Role> roles) {
        setClassName("addNewUser-dialog");
        setHeaderTitle("Add new user");
        binder.bindInstanceFields(this);
        role.setItems(roles);
        role.setItemLabelGenerator(Role::getName);
        getFooter().add(createCanselButton(this));
        getFooter().add(createSaveButton());
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

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private Button createCanselButton(Dialog dialog) {
        Button close = new Button("Cancel", e -> dialog.close());
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        return close;
    }

    public void setUserApp(UserApp userApp) {
        binder.setBean(userApp);
    }

    @Getter
    public static abstract class AddNewUserDialogEvent extends ComponentEvent<UserAppDialog> {
        private final UserApp userApp;

        protected AddNewUserDialogEvent(UserAppDialog source, UserApp userApp) {
            super(source, false);
            this.userApp = userApp;
        }

    }

    public static class SaveEvent extends AddNewUserDialogEvent {
        SaveEvent(UserAppDialog source, UserApp userApp) {
            super(source, userApp);
        }
    }

    public static class CloseEvent extends AddNewUserDialogEvent {
        CloseEvent(UserAppDialog source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);

    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
