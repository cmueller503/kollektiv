package net.ddns.jazzsrv.kollektiv.view;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import net.ddns.jazzsrv.kollektiv.entity.Benutzer;
import net.ddns.jazzsrv.kollektiv.repository.BenutzerRepository;

@Route(value = "admin/benutzer", layout = MainLayout.class)
@PageTitle("Benutzerverwaltung")
@RolesAllowed("ROLE_ADMIN")
public class BenutzerVerwaltungView extends VerticalLayout {

    private final BenutzerRepository repository;
    private final PasswordEncoder encoder;
    private final Grid<Benutzer> grid = new Grid<>(Benutzer.class);

    public BenutzerVerwaltungView(BenutzerRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;

        setSpacing(true);

        TextField benutzerName = new TextField("Benutzername");
        PasswordField passwort = new PasswordField("Passwort");
        ComboBox<String> rolle = new ComboBox<>("Rolle");
        rolle.setItems("ROLE_USER", "ROLE_ADMIN");
        rolle.setValue("ROLE_USER");

        Button speichern = new Button("Speichern", e -> {
            if (benutzerName.getValue().isBlank() || passwort.getValue().isBlank()) {
                Notification.show("Alle Felder ausfüllen");
                return;
            }
            Benutzer b = new Benutzer();
            b.setBenutzerName(benutzerName.getValue());
            b.setPasswort(encoder.encode(passwort.getValue()));
            b.setRolle(rolle.getValue());
            repository.save(b);
            Notification.show("Benutzer gespeichert");
            grid.setItems(repository.findAll());
        });

        HorizontalLayout formular = new HorizontalLayout(benutzerName, passwort, rolle, speichern);

        grid.setColumns("id", "benutzerName", "rolle");
        grid.setItems(repository.findAll());

        add(formular, grid);
    }
}
