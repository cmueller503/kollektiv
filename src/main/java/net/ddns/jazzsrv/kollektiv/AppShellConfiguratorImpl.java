package net.ddns.jazzsrv.kollektiv;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

@Component
@PWA(name = "Meine Anwendung", shortName = "App")
public class AppShellConfiguratorImpl implements AppShellConfigurator {
}
