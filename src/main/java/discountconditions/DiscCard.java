package discountconditions;

import models.DiscountCard;
import models.DiscountCardItem;
import models.Item;

import java.util.List;

import static dao.DiscountCardDao.getPercentByNumber;
import static dao.DiscountCardDao.isDiscCardWithNumber;

public class DiscCard {
    public static Item getDiscountPrice(Item item, int discountPercentage) {
        return new DiscountCardItem(item, discountPercentage);
    }

    public static DiscountCard getMaxDiscount(List<String> discountCards) {
        DiscountCard maxDiscountCard = new DiscountCard();
        for (String discountCard : discountCards) {
            if (isDiscCardWithNumber(discountCard)) {
                byte percent = getPercentByNumber(discountCard);
                if (maxDiscountCard.getPercent() < percent) {
                    maxDiscountCard.setPercent(percent);
                }
            }
        }
        return maxDiscountCard;
    }
}
