package net.ddns.jazzsrv.kollektiv.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "user", layout = MainLayout.class)
@RolesAllowed("USER")
public class UserView extends VerticalLayout {
    public UserView() {
        add(new H1("User-Bereich"));
    }
}