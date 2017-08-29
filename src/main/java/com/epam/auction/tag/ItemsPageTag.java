package com.epam.auction.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class ItemsPageTag extends BodyTagSupport {

    private static final String PAGINATION_COMMAND_PATTERN = "data-command=\"\"";
    private static final String FILTER_COMMAND_PATTERN = "name=\"command\" value=\"\"";
    private static final String TITLE_PATTERN = "//title-place//";

    private String title;
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
        bodyBuilder.replace(titleStart, titleStart + TITLE_PATTERN.length(), title);

        bodyBuilder.insert(bodyBuilder.indexOf(PAGINATION_COMMAND_PATTERN) + PAGINATION_COMMAND_PATTERN.length() - 1, command);
        bodyBuilder.insert(bodyBuilder.indexOf(FILTER_COMMAND_PATTERN) + FILTER_COMMAND_PATTERN.length() - 1, command);

        JspWriter jspWriter = bodyContent.getEnclosingWriter();
        try {
            jspWriter.write(bodyBuilder.toString());
        } catch (IOException e) {
            throw new JspTagException();
        }

        return SKIP_BODY;
    }

}