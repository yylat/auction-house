package com.epam.auction.controller;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.factory.CommandFactory;
import com.epam.auction.page.Page;
import com.epam.auction.page.TransferMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FileUploadController", urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 20)
public class FileUploadController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent();

        requestContent.extractValues(request);

        CommandFactory commandFactory = new CommandFactory();
        AbstractCommand command = commandFactory.initCommand(requestContent);

        List<InputStream> files = new ArrayList<>();

        for(Part part : request.getParts()){
            if(part.getSubmittedFileName() != null){
                files.add(part.getInputStream());
            }
        }

        if(!files.isEmpty()){
            requestContent.setFiles(files);
        }

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