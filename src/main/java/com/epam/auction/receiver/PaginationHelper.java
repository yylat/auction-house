package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;

public class PaginationHelper {

    private int page;
    private int limit;

    public PaginationHelper(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void definePage(RequestContent requestContent) {
        String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);
        if (page != null) {
            this.page = Integer.valueOf(page[0]);
        } else {
            this.page = 1;
        }
        requestContent.setRequestAttribute(RequestConstant.PAGE, this.page);
    }

    public void definePages(RequestContent requestContent, int totalUnits) {
        requestContent.setSessionAttribute(RequestConstant.PAGES, (totalUnits / this.limit) + 1);
    }

    public int findOffset() {
        return (page - 1) * limit;
    }

    public boolean pagesNumberDefined(RequestContent requestContent) {
        return !(requestContent.getRequestParameter(RequestConstant.INITIAL) == null) &&
                requestContent.getSessionAttribute(RequestConstant.PAGES) != null;
    }

}