package net.ddns.jazzsrv.kollektiv.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    public AdminView() {
        add(new H1("Admin-Bereich"));
    }
}
