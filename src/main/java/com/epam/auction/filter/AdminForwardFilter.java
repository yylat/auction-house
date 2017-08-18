package com.epam.auction.filter;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.entity.User;
import com.epam.auction.entity.UserRole;
import com.epam.auction.page.PageName;
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
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE},
        urlPatterns = {"/jsp/admin/*"})
public class AdminForwardFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.log(Level.INFO, this.getClass().getName() + " initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        User user = (User) httpRequest.getSession().getAttribute(RequestConstant.USER);
        if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
            logger.log(Level.INFO, "Attempt to access admin page from user with non admin role. Forwarding to index page.");
            httpRequest.getRequestDispatcher(PageName.INDEX.getAddress()).forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, this.getClass().getName() + " destroyed.");
    }

}