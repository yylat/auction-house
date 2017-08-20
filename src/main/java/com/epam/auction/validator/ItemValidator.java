package com.epam.auction.validator;

import com.epam.auction.entity.Item;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class ItemValidator extends Validator {

    private final static String NAME_PATTERN = "[A-Za-zА-Яа-яЁё ]{2,45}";

    private static final BigDecimal minPrice = new BigDecimal(0);
    private static final BigDecimal maxPrice = new BigDecimal(999999999999999999999.999);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date minStartDate;
    private final Date minCloseDate;

    public ItemValidator() {
        this.minStartDate = Date.from(LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.UTC));
        this.minCloseDate = Date.from(LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.UTC));
    }

    public boolean validItemParam(Item item) {
        return validate(item.getName(), NAME_PATTERN) &&
                validatePrice(item.getStartPrice()) &&
                validatePrice(item.getBlitzPrice()) &&
                validateDate(item);
    }

    private boolean validatePrice(BigDecimal value) {
        if (minPrice.compareTo(value) <= -1 && maxPrice.compareTo(value) >= 1) {
            return true;
        } else {
            setValidationMessage("Price can be in ranfe from " + minPrice + " to " + maxPrice + ". Value: [" + value + "].");
            return false;
        }
    }

    private boolean validateDate(Item item) {
        if (minStartDate.compareTo(item.getStartDate()) >= 1 && minCloseDate.compareTo(item.getCloseDate()) >= 1) {
            return true;
        } else {
            setValidationMessage("Start date can begin from: " + minStartDate +
                    ". Close date can begin from: " + minCloseDate +
                    ". Entered start and close dates: " + item.getStartDate() + ", " + item.getCloseDate() + ".");
            return false;
        }
    }

}