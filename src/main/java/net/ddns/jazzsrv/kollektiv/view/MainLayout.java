package net.ddns.jazzsrv.kollektiv.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vaadin.flow.server.VaadinSession;

import net.ddns.jazzsrv.kollektiv.entity.Role;
import net.ddns.jazzsrv.kollektiv.exception.CustomErrorHandler;

public class MainLayout extends AppLayout {
	
	private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
	
	

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
        Button checkAuthButton = new Button("Check Auth", e -> printAuth());

        HorizontalLayout header = new HorizontalLayout(toggle, logo, userInfo, logout, checkAuthButton);
        header.setWidthFull();
        header.setSpacing(true);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        addToNavbar(header);
    }

    private void printAuth() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	log.info("printAuth");
    	auth.getAuthorities().stream().forEach( a -> log.info(a.getAuthority()));
	}

	private void createDrawer() {
        VerticalLayout menu = new VerticalLayout();
        menu.add(new RouterLink("Startseite", MainView.class));
        addToDrawer(menu);
        
        
        if( isAuthorized(Role.ADMIN)) {
        	RouterLink adminLink2 = new RouterLink("Benutzerverwaltung UserGroupView", UserGroupView.class);
        	menu.add(adminLink2);        	
        	RouterLink adminLink = new RouterLink("Benutzerverwaltung UserManagementView", UserManagementView.class);
        	menu.add(adminLink);
        	
        	RouterLink adminViewLink = new RouterLink("AdminView", AdminView.class);
        	menu.add(adminViewLink);
        }
        

        if( isAuthorized(Role.MANAGER)) {
        	RouterLink managerViewLink = new RouterLink("ManagerView", ManagerView.class);
        	menu.add(managerViewLink);
        }
        
        if( isAuthorized(Role.USER)) {
        	RouterLink userViewLink = new RouterLink("UserView", UserView.class);
        	menu.add(userViewLink);
        }

    }

    private void logout() {
        UI.getCurrent().getPage().setLocation("/login");
    }
    
    private boolean isAuthorized( Role role ) {  
    	// Check, if role is found in security context
    	return role == null ? false : SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
    	.anyMatch(a -> a.toString().contentEquals("ROLE_" + role.name()));
    }
}