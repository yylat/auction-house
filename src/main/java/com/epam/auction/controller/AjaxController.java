package com.epam.auction.controller;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.CommandFactory;
import com.epam.auction.command.RequestContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent();

        requestContent.extractValues(request);

        CommandFactory commandFactory = new CommandFactory();
        AbstractCommand command = commandFactory.initCommand(requestContent);

        PageGuide pageGuide = command.execute(requestContent);

        requestContent.insertAttributes(request);

        String ajaxResponse = requestContent.getAjaxResponse();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ajaxResponse);

        if (pageGuide != null && PageAddress.ERROR.equals(pageGuide.getPageAddress())) {
            response.sendRedirect(pageGuide.getPageAddress());
        }

    }

}