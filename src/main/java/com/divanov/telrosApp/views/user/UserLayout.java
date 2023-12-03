package com.divanov.telrosApp.views.user;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class UserLayout extends AppLayout {

    public UserLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        DrawerToggle toggle = new DrawerToggle();

        H1 logo = new H1("Vaadin CRM | User Info");
        logo.addClassNames("text-l", "m-m");

        Button logOut = new Button("Log out");

        HorizontalLayout header = new HorizontalLayout(
                toggle,
                logo,
                logOut
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink userViewLink = new RouterLink("User Info", UserView.class);
        userViewLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                userViewLink
        ));
    }
}
