package com.epam.auction.validator;

import com.epam.auction.entity.Item;
import com.epam.auction.util.DateFixer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class ItemValidatorTest {

    private static ItemValidator itemValidator;

    @BeforeClass
    public static void init() {
        itemValidator = new ItemValidator();
    }

    private Item item;
    private boolean itemValidity;
    private boolean nameValidity;
    private boolean descriptionValidity;
    private boolean startPriceValidity;
    private boolean blitzPriceValidity;
    private boolean startDateValidity;
    private boolean closeDateValidity;

    public ItemValidatorTest(Item item, boolean itemValidity,
                             boolean nameValidity, boolean descriptionValidity,
                             boolean startPriceValidity, boolean blitzPriceValidity,
                             boolean startDateValidity, boolean closeDateValidity) {
        this.item = item;
        this.itemValidity = itemValidity;
        this.nameValidity = nameValidity;
        this.descriptionValidity = descriptionValidity;
        this.startPriceValidity = startPriceValidity;
        this.blitzPriceValidity = blitzPriceValidity;
        this.startDateValidity = startDateValidity;
        this.closeDateValidity = closeDateValidity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {new Item("first item", "first item description.",
                        new BigDecimal(10), new BigDecimal(20),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        true, true, true, true, true, true, true},

                {new Item("second item@$", "second item description.",
                        new BigDecimal(20), new BigDecimal(30),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        false, false, true, true, true, true, true},

                {new Item("3'rd item", "third item description <script>alert(\"js alert\");</script>.",
                        new BigDecimal(30), new BigDecimal(40),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        false, true, false, true, true, true, true},

                {new Item("fourth item", "fourth item description <h1>html header</h1>.",
                        new BigDecimal(40), new BigDecimal(50),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        false, true, false, true, true, true, true},

                {new Item("fifth item", "fifth item description.",
                        new BigDecimal(-2), new BigDecimal(60),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        false, true, true, false, true, true, true},

                {new Item("sixth item", "sixth item description.",
                        new BigDecimal(60), new BigDecimal(50),
                        DateFixer.addDays(5), DateFixer.addDays(8)),
                        false, true, true, true, false, true, true},

                {new Item("seventh item", "seventh item description.",
                        new BigDecimal(70), new BigDecimal(80),
                        DateFixer.addDays(0), DateFixer.addDays(8)),
                        false, true, true, true, true, false, true},

                {new Item("seventh item", "seventh item description.",
                        new BigDecimal(70), new BigDecimal(80),
                        DateFixer.addDays(5), DateFixer.addDays(6)),
                        false, true, true, true, true, true, false},

                {new Item("seventh item", "seventh item description.",
                        new BigDecimal(70), new BigDecimal(80),
                        DateFixer.addDays(5), DateFixer.addDays(2)),
                        false, true, true, true, true, true, false}
        });
    }

    @Test
    public void validateItemParamTest() {
        assertThat(itemValidator.validateItemParam(item), is(itemValidity));
    }

    @Test
    public void validateNameTest() {
        assertThat(itemValidator.validateName(item.getName()), is(nameValidity));
    }

    @Test
    public void validateDescriptionTest() {
        assertThat(itemValidator.validateDescription(item.getDescription()), is(descriptionValidity));
    }

    @Test
    public void validateStartPriceTest() {
        assertThat(itemValidator.validatePrice(item.getStartPrice()), is(startPriceValidity));
    }

    @Test
    public void validateBlitzPriceTest() {
        assertThat(itemValidator.validateBlitzPrice(item.getBlitzPrice(), item.getStartPrice()), is(blitzPriceValidity));
    }

    @Test
    public void validateStartDateTest() {
        assertThat(itemValidator.validateStartDate(item.getStartDate()), is(startDateValidity));
    }

    @Test
    public void validateCloseDateTest() {
        assertThat(itemValidator.validateCloseDate(item.getCloseDate(), item.getStartDate()), is(closeDateValidity));
    }

}