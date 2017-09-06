package com.epam.auction.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Provides service to process body of the tag: paste page title
 * and command for filter and pagination in specific places.
 */
public class ItemsPageTag extends BodyTagSupport {

    /**
     * Pattern to insert command for pagination.
     */
    private static final String PAGINATION_COMMAND_PATTERN = "data-command=\"\"";
    /**
     * Pattern to insert command for filter.
     */
    private static final String FILTER_COMMAND_PATTERN = "name=\"command\" value=\"\"";
    /**
     * Pattern to insert title.
     */
    private static final String TITLE_PATTERN = "//title-place//";

    /**
     * Page title.
     */
    private String title;
    /**
     * Command name.
     */
    private String command;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doAfterBody() throws JspTagException {
        BodyContent bodyContent = this.getBodyContent();
        StringBuilder bodyBuilder = new StringBuilder(bodyContent.getString());

        int titleStart = bodyBuilder.indexOf(TITLE_PATTERN);
        if (titleStart != -1) {
            bodyBuilder.replace(titleStart, titleStart + TITLE_PATTERN.length(), title);
        }

        int paginationCommandStart = bodyBuilder.indexOf(PAGINATION_COMMAND_PATTERN);
        if (paginationCommandStart != -1) {
            bodyBuilder.insert(paginationCommandStart + PAGINATION_COMMAND_PATTERN.length() - 1, command);
        }
        int filterCommandStart = bodyBuilder.indexOf(FILTER_COMMAND_PATTERN);
        if (filterCommandStart != -1) {
            bodyBuilder.insert(filterCommandStart + FILTER_COMMAND_PATTERN.length() - 1, command);
        }

        JspWriter jspWriter = bodyContent.getEnclosingWriter();
        try {
            jspWriter.write(bodyBuilder.toString());
        } catch (IOException e) {
            throw new JspTagException();
        }

        return SKIP_BODY;
    }

}