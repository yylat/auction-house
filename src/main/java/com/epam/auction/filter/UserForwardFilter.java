package com.epam.auction.filter;

import com.epam.auction.controller.PageAddress;
import com.epam.auction.entity.User;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        urlPatterns = {"/jsp/user/*"})
public class UserForwardFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.log(Level.DEBUG, this.getClass().getName() + " initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HttpSession session = httpRequest.getSession(false);
        User user = (User) session.getAttribute(RequestConstant.USER);
        if (user == null) {
            LOGGER.log(Level.INFO, "Attempt to access user page by guest. Forwarding to index page.");
            httpRequest.getRequestDispatcher(PageAddress.ACTIVE_ITEMS).forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOGGER.log(Level.DEBUG, this.getClass().getName() + " destroyed.");
    }

}