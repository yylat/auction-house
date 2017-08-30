package com.epam.auction.filter;

import com.epam.auction.controller.PageAddress;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.entity.User;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        urlPatterns = {"/jsp/user/edit_item.jsp"})
public class ItemUpdateAccessFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        User user = (User) session.getAttribute(RequestConstant.USER);
        Item item = (Item) session.getAttribute(RequestConstant.ITEM);
        if (user != null && item != null && user.getId() != item.getSellerId()) {
            if (user.getId() != item.getSellerId()) {
                LOGGER.log(Level.INFO, "Attempt to access item editing page from not seller of item.");
                request.getRequestDispatcher(PageAddress.ACTIVE_ITEMS).forward(request, response);
            } else if (!item.getStatus().equals(ItemStatus.CREATED) || !item.getStatus().equals(ItemStatus.CONFIRMED)) {
                LOGGER.log(Level.INFO, "Attempt to access item editing page when item not editable.");
                request.getRequestDispatcher(PageAddress.USER_ITEMS).forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
