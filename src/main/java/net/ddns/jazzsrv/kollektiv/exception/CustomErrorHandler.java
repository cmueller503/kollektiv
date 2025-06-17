package net.ddns.jazzsrv.kollektiv.exception;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {

    private static final long serialVersionUID = 8801113023352464356L;
	private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

    @Override
    public void error(ErrorEvent errorEvent) {
        logger.error("Something wrong happened", errorEvent.getThrowable());
        
        String clazz = getRootCause(errorEvent.getThrowable()).getClass().getCanonicalName();
        
        switch (clazz ) {
        	case "org.hibernate.TransientObjectException":
        		printToUser("Kann nicht gelöscht werden: " +
                        "wird von anderen Elementen verwendet");
        		break;
        	default:
        		printToUser("An internal error has occurred." +
                        "Please contact support." 
        		+ " (" + errorEvent.getThrowable().getMessage() + ")"
        		+ ", class=" + clazz
        		);
        }
    }

	private void printToUser(String message) {
	       if(UI.getCurrent() != null) {
	            UI.getCurrent().access(() -> {
	                Notification.show(message);
	            });
	        }
	}
	
	private Throwable getRootCause(Throwable e) {
	    Throwable cause = null; 
	    Throwable result = e;

	    while(null != (cause = result.getCause())  && (result != cause) ) {
	        result = cause;
	    }
	    return result;
	}
}