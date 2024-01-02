package com.divanov.telrosApp.views;

import com.divanov.telrosApp.views.admin.AdminView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
//    private final SecurityService securityService;

    public MainLayout(/**SecurityService securityService*/) {
//        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Telros CRM");
        logo.addClassNames("text-l", "m-m");

//        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
//                logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
//        header.expand(logout);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        VaadinIcon viewIcon = VaadinIcon.USER;
        String viewName = "User's data";
        Icon icon = viewIcon.create();
        RouterLink listLink = new RouterLink(AdminView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        listLink.add(icon, new Span(viewName));

        addToDrawer(new VerticalLayout(
                listLink
        ));
    }
}
