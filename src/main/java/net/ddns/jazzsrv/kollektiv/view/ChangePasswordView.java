package net.ddns.jazzsrv.kollektiv.view;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("password-change")
@PageTitle("Passwort ändern")
public class ChangePasswordView extends VerticalLayout {

    private final PasswordField currentPasswordField = new PasswordField("Aktuelles Passwort");
    private final PasswordField newPasswordField = new PasswordField("Neues Passwort");
    private final PasswordField confirmPasswordField = new PasswordField("Passwort bestätigen");
    private final Button saveButton = new Button("Passwort ändern");

    public ChangePasswordView() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(currentPasswordField, newPasswordField, confirmPasswordField, saveButton);
        add(formLayout);

        saveButton.addClickListener(event -> {
            String currentPassword = currentPasswordField.getValue();
            String newPassword = newPasswordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (!newPassword.equals(confirmPassword)) {
                Notification.show("Die neuen Passwörter stimmen nicht überein.");
            } else {
                // Hier können Sie die Logik zur Passwortänderung hinzufügen
                // Zum Beispiel, das aktuelle Passwort mit dem im Backend abzugleichen
                // und das neue Passwort zu speichern
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedNewPassword = encoder.encode(newPassword);

                // Hier würden Sie die tatsächliche Logik zur Passwortänderung einfügen
                // Dies ist ein einfaches Beispiel und dient nur als Ausgangspunkt

                Notification.show("Ihr Passwort wurde erfolgreich geändert.");
            }
        });
    }
}
