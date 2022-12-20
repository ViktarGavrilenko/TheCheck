package discountconditions;

import config.ConfigurationProperties;
import models.Item;
import models.Order;
import models.PromotionalItem;

import java.util.List;
import java.util.Properties;

import static dao.PromotionalItemDao.getListPromotionalItem;

public class Promotion {
    protected static final Properties configProperties =
            ConfigurationProperties.createProperties("receipt.properties");
    private static final List<Long> LIST_PROMOTIONAL_ITEM = getListPromotionalItem();
    private static final int SUFFICIENT_NUMBER_OF_PROMOTIONAL_ITEMS =
            Integer.parseInt(configProperties.getProperty("sufficientNumberOfPromotionalItems"));
    private static final int PROMOTION_PERCENTAGE = Integer.parseInt(configProperties.getProperty("promotionPercentage"));

    public static boolean isQuantityPromotionItemEnough(List<Order> listOrder) {
        long countDiscount = 0L;
        for (Order order : listOrder) {
            for (Long promotionalItem : LIST_PROMOTIONAL_ITEM) {
                if (order.getItem().getId() == promotionalItem) {
                    countDiscount = countDiscount + order.getQuantity();
                    break;
                }
            }
            if (countDiscount > SUFFICIENT_NUMBER_OF_PROMOTIONAL_ITEMS) {
                return true;
            }
        }
        return false;
    }

    public static Item getPromotionItem(Item item) {
        for (Long promotionalItem : LIST_PROMOTIONAL_ITEM) {
            if (item.getId() == promotionalItem) {
                return new PromotionalItem(item, PROMOTION_PERCENTAGE);
            }
        }
        return item;
    }
}