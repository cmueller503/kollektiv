package net.ddns.jazzsrv.kollektiv.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
        setDrawerOpened(false);
    }

    private void createHeader() {
    	
    	DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("title", "Menü öffnen");
        
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        H1 logo = new H1("Kollektiv");
        Span userInfo = new Span("Angemeldet als: " + username);
        Button logout = new Button("Logout", e -> logout());

        HorizontalLayout header = new HorizontalLayout(toggle, logo, userInfo, logout);
        header.setWidthFull();
        header.setSpacing(true);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout menu = new VerticalLayout();
        menu.add(new RouterLink("Startseite", MainView.class));
        addToDrawer(menu);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            	RouterLink adminLink = new RouterLink("Benutzerverwaltung", BenutzerVerwaltungView.class);
            	menu.add(adminLink);
            	adminLink.getElement().getStyle().set("margin", "var(--lumo-space-s)");
            	RouterLink catLink = new RouterLink("Kategorien", CategoryView.class);
            	menu.add(catLink);
            	catLink.getElement().getStyle().set("margin", "var(--lumo-space-s)");
        }
    	RouterLink taskLink = new RouterLink("Aufgaben", TaskView.class);
    	menu.add(taskLink);
    	//taskLink.getElement().getStyle().set("margin", "var(--lumo-space-s)");
    	RouterLink projectLink = new RouterLink("Projekte", ProjectView.class);
    	menu.add(projectLink);
    	//projectLink.getElement().getStyle().set("margin", "var(--lumo-space-s)");

    	
    	RouterLink projectTreeLink = new RouterLink("Projekte als Baum", ProjectTreeView.class);
    	menu.add(projectTreeLink);
    	
    	//projectTreeLink.getElement().getStyle().set("margin", "var(--lumo-space-s)");

    }

    private void logout() {
        UI.getCurrent().getPage().setLocation("/login");
    }
}