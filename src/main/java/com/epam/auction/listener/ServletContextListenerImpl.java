package com.epam.auction.listener;

import com.epam.auction.db.ConnectionPool;
import com.epam.auction.util.PhotoLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.init();
        PhotoLoader.createUploadFolder();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().destroy();
    }

}