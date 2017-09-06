package com.epam.auction.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Provides service to format date in jsp.
 */
public class DateTag extends TagSupport {

    /**
     * Date format.
     */
    private static final String DATE_PATTERN = "yyyy  dd MMM,  EEE  HH:mm:ss";

    /**
     * Date value.
     */
    private Date date;
    /**
     * Locale
     */
    private Locale locale;


    public void setDate(Date date) {
        this.date = date;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN, locale);

        try {
            this.pageContext.getOut().write(formatter.format(date));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

}