package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.receiver.RequestConstant;

class PaginationHelper {

    private int page;
    private int limit;

    PaginationHelper(int limit) {
        this.limit = limit;
    }

    int getLimit() {
        return limit;
    }

    void definePage(RequestContent requestContent) {
        String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);
        if (page != null) {
            this.page = Integer.valueOf(page[0]);
        } else {
            this.page = 1;
        }
        requestContent.setRequestAttribute(RequestConstant.PAGE, this.page);
    }

    void definePages(RequestContent requestContent, int totalUnits) {
        requestContent.setSessionAttribute(RequestConstant.PAGES, (totalUnits / this.limit) + 1);
    }

    int findOffset() {
        return (page - 1) * limit;
    }

    boolean pagesNumberNotDefined(RequestContent requestContent) {
        return requestContent.getRequestParameter(RequestConstant.INITIAL) == null ||
                requestContent.getSessionAttribute(RequestConstant.PAGES) == null;
    }

}