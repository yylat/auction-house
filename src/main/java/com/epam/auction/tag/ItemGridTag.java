package com.epam.auction.tag;

import com.epam.auction.entity.Item;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ItemGridTag extends TagSupport {

    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int doStartTag() throws JspException {
        int size = items.size();

        StringBuilder grid = new StringBuilder();

        int leftCellIndex;
        int rightCellIndex;
        for (leftCellIndex = 0; leftCellIndex < size; leftCellIndex = leftCellIndex + 2) {
            rightCellIndex = leftCellIndex + 1;

            buildRowStart(grid);

            buildCell(grid, items.get(leftCellIndex));

            if (rightCellIndex < size) {
                buildCell(grid, items.get(rightCellIndex));
            }

            buildRowEnd(grid);
        }

        try {
            pageContext.getOut().write(grid.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }

        return SKIP_BODY;
    }

    private void buildRowStart(StringBuilder grid) {
        grid.append("<div class=\"w3-row-padding\">");
    }

    private void buildRowEnd(StringBuilder grid) {
        grid.append("</div>");
    }

    private void buildCell(StringBuilder grid, Item item) {
        grid.append("<div class=\"w3-col w3-margin-top l6 m6\">")
                .append("<div class=\"w3-card item-wrapper\">")
                .append("<div class=\"w3-container item-back\">");
        grid.append("<div class=\"w3-container img-container\">")
                .append("<img src=\"\" item=\"").append(item.getId()).append("\"/></div>");
        grid.append("<div class=\"w3-container w3-center\">")
                .append("<form action=\"/controller\">")
                .append("<input type=\"hidden\" name=\"command\" value=\"load-item\"/>")
                .append("<input type=\"hidden\" name=\"item-id\" value=\"").append(item.getId()).append("\"/>")
                .append("<button class=\"item-title uppercase\">")
                .append(item.getName())
                .append("</button>")
                .append("</form></div>");
        grid.append("<div class=\"w3-container w3-center\">")
                .append("<div class=\"text-on-color money\">")
                .append(item.getActualPrice())
                .append("</div></div>");

        grid.append("</div></div></div>");
    }

}