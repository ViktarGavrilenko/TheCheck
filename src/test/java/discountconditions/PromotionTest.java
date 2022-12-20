package discountconditions;

import models.Item;
import models.Order;
import models.SimpleItem;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static discountconditions.DiscCard.getDiscountItem;
import static discountconditions.Promotion.getPromotionItem;
import static discountconditions.Promotion.isQuantityPromotionItemEnough;
import static org.testng.Assert.*;

public class PromotionTest {

    @Test
    public void testIsQuantityPromotionItemEnoughTrue() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(new SimpleItem(1, "Box", 10.0), 6L));
        Assert.assertTrue(isQuantityPromotionItemEnough(orders));
    }

    @Test
    public void testIsQuantityPromotionItemEnoughFalse() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(new SimpleItem(1, "Box", 10.0), 5L));
        Assert.assertFalse(isQuantityPromotionItemEnough(orders));
    }

    @Test
    public void testIsQuantityPromotionItemEnoughTrueFromSeveral() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(new SimpleItem(1, "Box", 10.0), 3L));
        orders.add(new Order(new SimpleItem(3, "Pencil", 5.0), 3L));
        Assert.assertTrue(isQuantityPromotionItemEnough(orders));
    }

    @Test
    public void testIsQuantityPromotionItemEnoughFalseFromSeveral() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(new SimpleItem(1, "Box", 10.0), 3L));
        orders.add(new Order(new SimpleItem(2, "Pencil", 5.0), 3L));
        Assert.assertFalse(isQuantityPromotionItemEnough(orders));
    }

    @Test
    public void testGetPromotionItemTrue() {
        Item item = new SimpleItem(1 ,"Box", 50.0);
        Assert.assertEquals(getPromotionItem(item).getPrice(), 45,
                "Incorrect price of a promotional product");
    }

    @Test
    public void testGetPromotionItemFalse() {
        Item item = new SimpleItem(2 ,"Box", 50.0);
        Assert.assertEquals(getPromotionItem(item).getPrice(), 50,
                "Incorrect price of a promotional product");
    }
}