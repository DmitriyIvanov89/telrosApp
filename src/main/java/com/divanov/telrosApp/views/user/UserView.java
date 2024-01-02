package com.divanov.telrosApp.views.user;

;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;


@Route(value = "user", layout = UserLayout.class)
@PageTitle("User info page | Telros CRM")
@PermitAll
public class UserView extends HorizontalLayout {
    TextField userName;
    PasswordField password;
    TextField firstName;
    TextField lastName;
    TextField patronymic;
    EmailField email;
    TextField dateOfBirth;
    TextField phone;

    Button save;
    Button delete;

    public UserView() {
        addClassName("user-view");

        userName = new TextField("Username");
        password = new PasswordField("Password");
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        patronymic = new TextField("Patronymic");
        email = new EmailField("Email");
        dateOfBirth = new TextField("Date of Birth");
        phone = new TextField("Phone");

        save = createSaveButton();
        delete = createDeleteButton();

        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete);

        add(createFieldsLayout(), buttonLayout);
        setAlignItems(Alignment.END);
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(createUserInfoFieldsLayout(), createCredentialsLayout());
    }

    private VerticalLayout createUserInfoFieldsLayout() {
        VerticalLayout userInfoFieldsLayout = new VerticalLayout(firstName, lastName, patronymic, email, dateOfBirth, phone);
        userInfoFieldsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return userInfoFieldsLayout;
    }

    private VerticalLayout createCredentialsLayout() {
        VerticalLayout credentialsLayout = new VerticalLayout(userName, password);
        credentialsLayout.setJustifyContentMode(JustifyContentMode.START);
        return credentialsLayout;
    }

    private Button createSaveButton() {
        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return save;
    }

    private Button createDeleteButton() {
        Button delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return delete;
    }
}
