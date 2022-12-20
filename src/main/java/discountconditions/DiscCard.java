package discountconditions;

import models.DiscountCardItem;
import models.Item;

import java.util.List;

import static dao.DiscountCardDao.getPercentByNumber;
import static dao.DiscountCardDao.isDiscCardWithNumber;

public class DiscCard {
    public static Item getDiscountItem(Item item, int discountPercentage) {
        return new DiscountCardItem(item, discountPercentage);
    }

    public static byte getMaxDiscount(List<String> discountCards) {
        byte maxDiscountCard = 0;
        for (String discountCard : discountCards) {
            if (isDiscCardWithNumber(discountCard)) {
                byte percent = getPercentByNumber(discountCard);
                if (maxDiscountCard < percent) {
                    maxDiscountCard = percent;
                }
            }
        }
        return maxDiscountCard;
    }
}