package com.epam.auction.tag;

import com.epam.auction.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class InitialsTag extends TagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder result = new StringBuilder();
        result.append(user.getLastName())
                .append(leaveInitialLetter(user.getFirstName()))
                .append(leaveInitialLetter(user.getMiddleName()));
        try {
            this.pageContext.getOut().write(result.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    private String leaveInitialLetter(String word) {
        String result;
        if (word != null && !word.isEmpty()) {
            result = " " + word.substring(0, 1) + ".";
        } else {
            result = "";
        }
        return result;
    }

}