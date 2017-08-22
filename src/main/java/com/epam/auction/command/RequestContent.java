package com.epam.auction.command;

import com.epam.auction.util.Converter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestContent {

    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, String[]> requestParameters = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();

    private String ajaxResponse;

    private List<InputStream> files;

    public void extractValues(HttpServletRequest request) {
        Enumeration<String> attributesNames = request.getAttributeNames();
        while (attributesNames.hasMoreElements()) {
            String attributeName = attributesNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
            request.removeAttribute(attributeName);
        }

        requestParameters = request.getParameterMap();

        Enumeration<String> sessionAttributesNames = request.getSession().getAttributeNames();
        while (sessionAttributesNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributesNames.nextElement();
            sessionAttributes.put(sessionAttributeName, request.getSession().getAttribute(sessionAttributeName));
            request.getSession().removeAttribute(sessionAttributeName);
        }

    }

    public void insertAttributes(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void setRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public String[] getRequestParameter(String key) {
        return requestParameters.get(key);
    }

    public void setRequestParameter(String key, String[] value) {
        requestParameters.put(key, value);
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public void destroySessionAttributes() {
        sessionAttributes.clear();
    }

    public String getAjaxResponse() {
        return ajaxResponse;
    }

    public void setAjaxResponse(String json){
        this.ajaxResponse = json;
    }

    public void setAjaxResponse(Object object) {
        this.ajaxResponse = Converter.objectToJson(object);
    }

    public void setAjaxFile(InputStream file) throws IOException {
        this.ajaxResponse = Converter.inputStreamToString(file);
    }

    public List<InputStream> getFiles() {
        return files;
    }

    public void setFiles(List<InputStream> files) {
        this.files = files;
    }
}