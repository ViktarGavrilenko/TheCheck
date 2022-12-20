package discountconditions;

import models.Item;
import models.SimpleItem;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static discountconditions.DiscCard.getDiscountItem;
import static discountconditions.DiscCard.getMaxDiscount;

public class DiscCardTest {

    @Test
    public void testGetDiscountItemWithTenPercent() {
        Item item = new SimpleItem(1 ,"Box", 10.2);
        Assert.assertEquals(getDiscountItem(item, 10).getPrice(), 9.18,
                "Incorrect price after discount ");
    }
    @Test
    public void testGetDiscountItemWithFivePercent() {
        Item item = new SimpleItem(1 ,"Box", 50.0);
        Assert.assertEquals(getDiscountItem(item, 5).getPrice(), 47.5,
                "Incorrect price after discount ");
    }

    @Test
    public void testGetDiscountItemWithZeroPercent() {
        Item item = new SimpleItem(1 ,"Box", 50.0);
        Assert.assertEquals(getDiscountItem(item, 0).getPrice(), 50.0,
                "Incorrect price after discount ");
    }

    @Test
    public void testGetDiscountItemWithOneHundredPercent() {
        Item item = new SimpleItem(1 ,"Box", 50.0);
        Assert.assertEquals(getDiscountItem(item, 100).getPrice(), 0,
                "Incorrect price after discount ");
    }

    @Test
    public void testGetMaxDiscount() {
        List<String> discCardList = new ArrayList<>();
        discCardList.add("1111");
        discCardList.add("1234");
        discCardList.add("2");
        discCardList.add("5555");
        discCardList.add("mycard");
        Assert.assertEquals(getMaxDiscount(discCardList), 11, "Maximum discount is not correct");
    }
}