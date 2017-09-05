package com.epam.auction.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides replacement for HttpServletRequest object.
 */
public class RequestContent {

    /**
     * Request attributes map.
     */
    private Map<String, Object> requestAttributes = new HashMap<>();
    /**
     * Request parameters map.
     */
    private Map<String, String[]> requestParameters = new HashMap<>();

    /**
     * Session attributes map.
     */
    private Map<String, Object> sessionAttributes = new HashMap<>();
    /**
     * Indicates when to invalidate session.
     */
    private boolean invalidateSession = false;

    /**
     * Contains JSON response for AJAX request.
     */
    private String ajaxResponse = "";

    /**
     * Contains uploaded files.
     */
    private Map<String, InputStream> files;

    /**
     * Extracts request attributes, parameters and session attributes
     * from request and delete request and session attributes from
     * this request.
     *
     * @param request http servlet request
     */
    void extractValues(HttpServletRequest request) {
        extractRequestValues(request);

        Enumeration<String> sessionAttributesNames = request.getSession().getAttributeNames();
        while (sessionAttributesNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributesNames.nextElement();
            sessionAttributes.put(sessionAttributeName, request.getSession().getAttribute(sessionAttributeName));
            request.getSession().removeAttribute(sessionAttributeName);
        }
    }

    /**
     * Extracts request attributes, parameters and session attributes
     * from request and delete request attributes from this request.
     *
     * @param request http servlet request
     */
    void extractValuesFromAjax(HttpServletRequest request) {
        extractRequestValues(request);

        Enumeration<String> sessionAttributesNames = request.getSession().getAttributeNames();
        while (sessionAttributesNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributesNames.nextElement();
            sessionAttributes.put(sessionAttributeName, request.getSession().getAttribute(sessionAttributeName));
        }
    }

    /**
     * Inserts request and session attributes into request.
     *
     * @param request http servlet request
     */
    void insertAttributes(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);

        if (invalidateSession) {
            request.getSession().invalidate();
        } else {
            sessionAttributes.forEach(request.getSession()::setAttribute);
        }
    }

    /**
     * Returns request attribute with key from requestAttributes map.
     *
     * @param key key of the request attribute
     * @return request attribute with key
     */
    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    /**
     * Puts request attribute with key into requestAttributes map.
     *
     * @param key   key of the request attribute
     * @param value request attribute
     */
    public void setRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    /**
     * Returns array of request parameters with key from
     * requestParameters map.
     *
     * @param key key of the request parameters
     * @return array of request parameters with key
     */
    public String[] getRequestParameter(String key) {
        return requestParameters.get(key);
    }

    /**
     * Puts array of request parameters with key into
     * requestParameters map.
     *
     * @param key   key of the request parameters
     * @param value array of request parameters
     */
    public void setRequestParameter(String key, String[] value) {
        requestParameters.put(key, value);
    }

    /**
     * Returns session attribute with key from sessionAttributes map.
     *
     * @param key key of the session attribute
     * @return session attribute with key
     */
    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    /**
     * Puts session attribute with key into sessionAttributes map.
     *
     * @param key   key of the session attribute
     * @param value session attribute
     */
    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    /**
     * Removes session attribute with key from sessionAttributes map.
     *
     * @param key key of the session attribute
     */
    public void removeSessionAttribute(String key) {
        sessionAttributes.remove(key);
    }

    /**
     * Sets invalidateSession flag to <code>true</code>
     */
    public void destroySession() {
        invalidateSession = true;
    }

    /**
     * Returns response for AJAX request.
     *
     * @return response for AJAX request
     */
    String getAjaxResponse() {
        return ajaxResponse;
    }

    /**
     * Sets response for AJAX request.
     *
     * @param json response for AJAX request
     */
    public void setAjaxResponse(String json) {
        this.ajaxResponse = json;
    }

    /**
     * Returns uploaded files.
     *
     * @return uploaded files
     */
    public Map<String, InputStream> getFiles() {
        return files;
    }

    /**
     * Sets uploaded files.
     *
     * @param files uploaded files
     */
    void setFiles(Map<String, InputStream> files) {
        this.files = files;
    }

    /**
     * Extracts request attributes and parameters from request
     * and delete request attributes from this request.
     *
     * @param request http servlet request
     */
    private void extractRequestValues(HttpServletRequest request) {
        Enumeration<String> attributesNames = request.getAttributeNames();
        while (attributesNames.hasMoreElements()) {
            String attributeName = attributesNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
            request.removeAttribute(attributeName);
        }

        requestParameters = request.getParameterMap();
    }

}