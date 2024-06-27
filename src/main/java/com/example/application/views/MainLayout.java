package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.DashBoardView;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        var logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        var logoutButton = new Button("Logout",
                e -> securityService.logout()
        );

        var header = new HorizontalLayout(
                new DrawerToggle(),
                logo,
                logoutButton
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-0");

        addToNavbar(header);
    }

    private void createDrawer() {
        var listView = new RouterLink("List", ListView.class);
        var dashboard = new RouterLink("Dashboard", DashBoardView.class);
        dashboard.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listView,
                dashboard
        ));
    }
}
