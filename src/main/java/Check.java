import models.DiscountCard;
import models.Order;
import models.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static databasequeries.DiscountCardQueries.getPercentByNumber;
import static databasequeries.ProductQueries.getProductById;

public class Check {
    private final List<Order> listOrder = new ArrayList<>();
    private final List<String> discountCards = new ArrayList<>();
    private static final String CARD = "card";
    private static final byte PERCENTAGE_DISCOUNT = 10;

    public void getCheck(String inputStr) {
        createListProductAndDiscountCardFromStr(inputStr);
        DiscountCard maxDiscountCard = getMaxDiscount();
        System.out.println(getHeaderReceipt());

        StringBuilder receipt = new StringBuilder();

        double total = 0F;
        for (Order order : listOrder) {
            Float price = order.getProduct().getPrice();
            Long qty = order.getQuantity();
            receipt.append(qty)
                    .append("      ").append(order.getProduct().getName())
                    .append("      ").append(price)
                    .append("      ").append(price * qty);

            if (isQuantityDiscountedProductEnough() && order.getProduct().isDiscount()) {
                Double totalDiscount = ((100 - PERCENTAGE_DISCOUNT) * 0.01 * price * qty);
                BigDecimal roundingTotal = new BigDecimal(totalDiscount);
                roundingTotal = roundingTotal.setScale(2, RoundingMode.HALF_UP);

                receipt.append("(" + roundingTotal + ")");
                total = total + roundingTotal.doubleValue();
            } else {
                total = total + price * qty;
            }
            receipt.append("\n");
        }

        System.out.println(receipt);
        System.out.println("________________________________________");
        System.out.println("Total                        " + total);
    }

    public void createListProductAndDiscountCardFromStr(String inputStr) {
        String[] items = inputStr.split(" ");
        for (int i = 0; i < items.length; i++) {
            String[] item = items[i].split("-");
            if (item[0].equals(CARD)) {
                discountCards.add(item[1]);
            } else {
                Product product = getProductById(Integer.parseInt(item[0]));
                Order order = new Order(product, Long.parseLong(item[1]));
                listOrder.add(order);
            }
        }
    }

    public DiscountCard getMaxDiscount() {
        DiscountCard maxDiscountCard = new DiscountCard();
        for (String discountCard : discountCards) {
            byte percent = getPercentByNumber(discountCard);
            if (maxDiscountCard.getPercent() < percent) {
                maxDiscountCard.setPercent(percent);
            }
        }
        return maxDiscountCard;
    }

    public boolean isQuantityDiscountedProductEnough() {
        Long countDiscount = 0L;
        for (Order order : listOrder) {
            if (order.getProduct().isDiscount()) {
                countDiscount = countDiscount + order.getQuantity();
            }
            if (countDiscount > 5) {
                return true;
            }
        }
        return false;
    }

    public StringBuilder getHeaderReceipt() {
        Date date = new Date();
        String dateNow = String.format("%tF", date);
        String timeNow = String.format("%tT", date);
        StringBuilder headerReceipt = new StringBuilder();
        headerReceipt.append("CASH RECEIPT\n");
        headerReceipt.append("Supermarket 123\n");
        headerReceipt.append("Cashier №   \n");
        headerReceipt.append("Date: ").append(dateNow);
        headerReceipt.append("\nTime: ").append(timeNow);
        headerReceipt.append("\n________________________________________\n");
        headerReceipt.append("QTY    DESCRIPTION        PRICE    TOTAL \n");
        return headerReceipt;
    }
}