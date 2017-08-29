package com.epam.auction.validator;

import com.epam.auction.entity.Item;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ItemValidator extends Validator {

    private final static String NAME_PATTERN = "['A-Za-zА-Яа-яЁё ]{2,45}";

    private static final BigDecimal minPrice = new BigDecimal(0);
    private static final BigDecimal maxPrice = new BigDecimal(999999999999999999999.999);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date minStartDate;
    private final Date minCloseDate;

    public ItemValidator() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);
        this.minStartDate = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        this.minCloseDate = calendar.getTime();
    }

    public boolean validateItemParam(Item item) {
        return validateItemParam(item.getName(),
                item.getStartPrice(), item.getBlitzPrice(),
                item.getStartDate(), item.getCloseDate());
    }

    public boolean validateItemParam(String name, BigDecimal startPrice, BigDecimal blitzPrice,
                                     Date startDate, Date closeDate) {
        return validate(name, NAME_PATTERN) &&
                validatePrice(startPrice) &&
                validatePrice(blitzPrice) &&
                validateStartDate(startDate) &&
                validateCloseDate(closeDate);
    }

    private boolean validatePrice(BigDecimal value) {
        if (minPrice.compareTo(value) <= -1 && maxPrice.compareTo(value) >= 1) {
            return true;
        } else {
            setValidationMessage("Price can be in range from " + minPrice + " to " + maxPrice + ". Value: [" + value + "].");
            return false;
        }
    }

    private boolean validateStartDate(Date startDate) {
        if (minStartDate.compareTo(startDate) <= 0) {
            return true;
        } else {
            setValidationMessage("Start date can begin from: " + minStartDate +
                    ". Entered start date: " + startDate);
            return false;
        }
    }

    private boolean validateCloseDate(Date closeDate) {
        if (minCloseDate.compareTo(closeDate) <= 0) {
            return true;
        } else {
            setValidationMessage("Close date can begin from: " + minStartDate +
                    ". Entered close date: " + closeDate);
            return false;
        }
    }

}