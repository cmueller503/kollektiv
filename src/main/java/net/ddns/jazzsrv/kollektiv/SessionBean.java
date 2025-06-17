package net.ddns.jazzsrv.kollektiv;

import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.VaadinSession;

import net.ddns.jazzsrv.kollektiv.exception.CustomErrorHandler;

@Component
public class SessionBean {
	
    @EventListener
    public void logSessionInits(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(
                sessionInitEvent -> onSessionInit(sessionInitEvent));
    }

	private void onSessionInit(SessionInitEvent sessionInitEvent) {
		LoggerFactory.getLogger(getClass())
        .info("A new Session has been initialized!");
		VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
	}
    
    
}
