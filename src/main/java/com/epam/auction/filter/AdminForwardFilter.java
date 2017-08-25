package com.epam.auction.filter;

import com.epam.auction.controller.PageAddress;
import com.epam.auction.entity.User;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE},
        urlPatterns = {"/jsp/admin/*"})
public class AdminForwardFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.log(Level.INFO, this.getClass().getName() + " initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        String uri = httpRequest.getRequestURI();
//        LOGGER.log(Level.INFO, "Requested resourse: " + uri);
        HttpSession session = httpRequest.getSession(false);
        User user = (User) session.getAttribute(RequestConstant.USER);
        if (user == null || !user.getRole().equals(User.UserRole.ADMIN)) {
            LOGGER.log(Level.INFO, "Attempt to access admin page from user with non admin role. Forwarding to index page.");
            httpRequest.getRequestDispatcher(PageAddress.ACTIVE_ITEMS).forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOGGER.log(Level.INFO, this.getClass().getName() + " destroyed.");
    }

}