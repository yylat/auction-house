package com.epam.auction.controller;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.CommandFactory;
import com.epam.auction.command.PageAddress;
import com.epam.auction.command.PageGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides service for working with AJAX requests.
 */
@WebServlet(name = "AjaxController", urlPatterns = {"/ajax"})
public class AjaxController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Writes JSON into response.
     *
     * @param request  request
     * @param response response
     * @throws ServletException if servlet exception occurred
     * @throws IOException      if IO exception occurred
     * @see MainController#processRequest(HttpServletRequest, HttpServletResponse)
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent();

        requestContent.extractValuesFromAjax(request);

        CommandFactory commandFactory = new CommandFactory();
        AbstractCommand command = commandFactory.initCommand(requestContent);

        PageGuide pageGuide = command.execute(requestContent);

        requestContent.insertAttributes(request);

        String ajaxResponse = requestContent.getAjaxResponse();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ajaxResponse);

        if (pageGuide != null && PageAddress.ERROR.equals(pageGuide.getPageAddress())) {
            response.sendRedirect(PageAddress.ERROR);
        }

    }

}