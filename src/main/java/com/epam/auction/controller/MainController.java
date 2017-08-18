package com.epam.auction.controller;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.factory.CommandFactory;
import com.epam.auction.page.Page;
import com.epam.auction.page.TransferMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/controller"})
public class MainController extends HttpServlet {

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

        requestContent.setSessionAttribute(RequestConstant.CURRENT_PAGE, page.getPageName());

        requestContent.insertAttributes(request);

        if (TransferMethod.FORWARD.equals(page.getTransferMethod())) {
            request.getRequestDispatcher(page.getAddress()).forward(request, response);
        } else {
            response.sendRedirect(page.getAddress());
        }
    }

}