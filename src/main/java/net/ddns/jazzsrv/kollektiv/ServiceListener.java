package net.ddns.jazzsrv.kollektiv;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

import net.ddns.jazzsrv.kollektiv.exception.CustomErrorHandler;

//@Component
public class ServiceListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = 4649136907863874076L;

	@Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(
                initEvent -> sessionInit(initEvent));

        event.getSource().addUIInitListener(
                initEvent -> uiInit(initEvent));
        
        
    }
    
    private void uiInit(UIInitEvent initEvent) {
    	LoggerFactory.getLogger(getClass())
        .info("A new UI has been initialized!");
	}

	private void sessionInit(SessionInitEvent event) {
    	LoggerFactory.getLogger(getClass())
        .info("A new Session has been initialized!");
    	VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
    }
}
