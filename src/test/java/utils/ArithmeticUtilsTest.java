package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import static discountconditions.Promotion.getPromotionItem;
import static org.testng.Assert.*;
import static utils.ArithmeticUtils.getPriceWithDiscount;
import static utils.ArithmeticUtils.roundingNumber;

public class ArithmeticUtilsTest {

    @Test
    public void testRoundingNumber() {
        Assert.assertEquals(roundingNumber(6.666, 2), 6.67,"Incorrect rounding");
        Assert.assertEquals(roundingNumber(6.6666, 2), 6.67,"Incorrect rounding");
        Assert.assertEquals(roundingNumber(6.649, 2), 6.65,"Incorrect rounding");
        Assert.assertEquals(roundingNumber(6.001, 2), 6,"Incorrect rounding");
    }

    @Test
    public void testGetPriceWithDiscount() {
        Assert.assertEquals(getPriceWithDiscount(10.0, 10), 9,
                "Incorrect discounted price");
        Assert.assertEquals(getPriceWithDiscount(10.0, 0), 10,
                "Incorrect discounted price");
        Assert.assertEquals(getPriceWithDiscount(10.0, 100), 0,
                "Incorrect discounted price");
        Assert.assertEquals(roundingNumber(getPriceWithDiscount(22.22, 10),2), 20,
                "Incorrect discounted price");
    }
}