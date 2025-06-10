package net.ddns.jazzsrv.kollektiv.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Startseite")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView() {
        add(new Text("Willkommen auf der Startseite!"));
    }
}


