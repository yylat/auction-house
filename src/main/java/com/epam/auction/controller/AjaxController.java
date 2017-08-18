package com.epam.auction.controller;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.content.RequestContent;
import com.epam.auction.factory.CommandFactory;
import com.epam.auction.page.Page;
import com.epam.auction.page.PageName;

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

        Page page = command.execute(requestContent);

        requestContent.insertAttributes(request);

        String ajaxResponse = requestContent.getAjaxResponse();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ajaxResponse);

        if (page != null && PageName.ERROR.equals(page.getPageName())) {
            response.sendRedirect(page.getAddress());
        }

    }

}