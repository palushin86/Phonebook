package ru.palushin86.exceptions;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;

public class AppExceptionHandler extends ExceptionHandlerWrapper {
    private ExceptionHandler wrapped;

    public AppExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();

        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();
            Throwable throwable = context.getException();
            FacesContext fc = FacesContext.getCurrentInstance();

            try {
                Flash flash = fc.getExternalContext().getFlash();
                flash.put("errorDetails", throwable.getMessage());
                fc.renderResponse();
            } finally {
                iterator.remove();
            }
        }

        getWrapped().handle();
    }
}
