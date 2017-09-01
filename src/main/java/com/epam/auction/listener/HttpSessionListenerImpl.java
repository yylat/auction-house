package com.epam.auction.listener;

import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.LocaleType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(RequestConstant.LOCALE, LocaleType.EN.getLocale());
        LOGGER.log(Level.DEBUG, this.getClass().getName() + " initialized.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.log(Level.DEBUG, this.getClass().getName() + " destroyed.");
    }

}