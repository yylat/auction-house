package com.epam.auction.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Represents a method for servlet to go to some page.
 */
public enum TransferMethod {

    /**
     * Says to use forward() method.
     *
     * @see javax.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse)
     */
    FORWARD,
    /**
     * Says to use sendRedirect() method.
     *
     * @see javax.servlet.http.HttpServletResponse#sendRedirect(String)
     */
    REDIRECT

}