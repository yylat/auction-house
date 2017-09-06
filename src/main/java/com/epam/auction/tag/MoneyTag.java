package com.epam.auction.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Provides service to format money in jsp.
 */
public class MoneyTag extends TagSupport {

    /**
     * Money format.
     */
    private static final String DECIMAL_PATTERN = "$###,###,###";

    /**
     * Money value.
     */
    private BigDecimal value;

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        DecimalFormat formatter = new DecimalFormat(DECIMAL_PATTERN);

        try {
            this.pageContext.getOut().write(formatter.format(value));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

}