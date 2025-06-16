package net.ddns.jazzsrv.kollektiv.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "manager" , layout = MainLayout.class)
@RolesAllowed("MANAGER")
public class ManagerView extends VerticalLayout {
    public ManagerView() {
        add(new H1("Manager-Bereich"));
    }
}