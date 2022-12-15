import config.ConfigurationProperties;
import models.DiscountCard;
import models.Order;
import models.Product;
import org.apache.log4j.Logger;
import utils.Alignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static databasequeries.DiscountCardQueries.getPercentByNumber;
import static databasequeries.ProductQueries.getProductById;
import static utils.ArithmeticUtils.roundingNumber;
import static utils.FileUtils.*;
import static utils.StringUtils.getStringGivenLength;

public class Check {
    private static final Logger LOG = Logger.getLogger(Check.class);

    protected static final Properties configProperties =
            ConfigurationProperties.createProperties("receipt.properties");
    private static final int QTY_LENGTH = Integer.parseInt(configProperties.getProperty("qtyLength"));
    private static final int DESCRIPTION_LENGTH = Integer.parseInt(configProperties.getProperty("descriptionLength"));
    private static final int PRICE_LENGTH = Integer.parseInt(configProperties.getProperty("priceLength"));
    private static final int TOTAL_LENGTH = Integer.parseInt(configProperties.getProperty("totalLength"));
    private static final int DISC_LENGTH = Integer.parseInt(configProperties.getProperty("discLength"));
    private static final int STR_LENGTH = QTY_LENGTH + DESCRIPTION_LENGTH + PRICE_LENGTH + TOTAL_LENGTH + DISC_LENGTH;
    private static final String PATH_RECEIPT = configProperties.getProperty("pathReceipt");
    private static final int START_NUMBER = Integer.parseInt(configProperties.getProperty("startNumber"));
    private static final String FILE_WITH_LAST_RECEIPT = configProperties.getProperty("fileWithLastReceipt");

    private final List<Order> listOrder = new ArrayList<>();
    private final List<String> discountCards = new ArrayList<>();
    private static final String CARD = "card";
    private static final byte PERCENTAGE_DISCOUNT = 10;

    public void getCheck(String inputStr) {
        createListProductAndDiscountCardFromStr(inputStr);
        DiscountCard maxDiscountCard = getMaxDiscount();

        File folder = new File(PATH_RECEIPT);
        if (!folder.exists()) {
            folder.mkdir();
        }

        int receiptNumber = getReceiptNumber();
        writeLastNumberInFile(receiptNumber, PATH_RECEIPT + "/" + FILE_WITH_LAST_RECEIPT);
        String pathReceipt = folder + "/" + receiptNumber;

        try (FileWriter writer = new FileWriter(pathReceipt, false)) {
            writer.write(String.valueOf(getHeaderReceipt(String.valueOf(receiptNumber))));
            writer.write(String.valueOf(getBodyReceipt()));
            writer.flush();
        } catch (IOException ex) {
            LOG.error("Receipt creation error");
        }

        displayReceiptToConsole(pathReceipt);
    }

    public void createListProductAndDiscountCardFromStr(String inputStr) {
        String[] items = inputStr.split(" ");
        for (String s : items) {
            String[] item = s.split("-");
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
        long countDiscount = 0L;
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

    public StringBuilder getHeaderReceipt(String receiptNumber) {
        Date date = new Date();
        String dateNow = String.format("%tF", date);
        String timeNow = String.format("%tT", date);
        StringBuilder headerReceipt = new StringBuilder();
        headerReceipt.append("CASH RECEIPT\n");
        headerReceipt.append("Supermarket 123\n");
        headerReceipt.append("Cashier № ").append(receiptNumber);
        headerReceipt.append("\nDate: ").append(dateNow);
        headerReceipt.append("\nTime: ").append(timeNow);
        headerReceipt.append("\n");
        headerReceipt.append("_".repeat(Math.max(0, STR_LENGTH)));
        headerReceipt.append("\n");
        headerReceipt.append(getStringGivenLength("QTY", QTY_LENGTH, Alignment.START));
        headerReceipt.append(getStringGivenLength("DESCRIPTION", DESCRIPTION_LENGTH, Alignment.END));
        headerReceipt.append(getStringGivenLength("PRICE", PRICE_LENGTH, Alignment.END));
        headerReceipt.append(getStringGivenLength("TOTAL", TOTAL_LENGTH, Alignment.END));
        headerReceipt.append(getStringGivenLength("DISC", DISC_LENGTH, Alignment.END));
        headerReceipt.append("\n");
        return headerReceipt;
    }

    public StringBuilder getBodyReceipt() {
        StringBuilder receipt = new StringBuilder();
        double total = 0F;
        double totalDiscount = 0F;
        for (Order order : listOrder) {
            Float price = order.getProduct().getPrice();
            Long qty = order.getQuantity();
            receipt.append(getStringGivenLength(String.valueOf(qty), QTY_LENGTH, Alignment.START));
            receipt.append(getStringGivenLength(
                    String.valueOf(order.getProduct().getName()), DESCRIPTION_LENGTH, Alignment.END));
            receipt.append(getStringGivenLength(String.valueOf(price), PRICE_LENGTH, Alignment.END));
            receipt.append(getStringGivenLength(
                    String.valueOf(roundingNumber((double) (price * qty), 2)), TOTAL_LENGTH, Alignment.END));

            if (isQuantityDiscountedProductEnough() && order.getProduct().isDiscount()) {
                double discount = roundingNumber(((100 - PERCENTAGE_DISCOUNT) * 0.01 * price * qty), 2);
                receipt.append(getStringGivenLength(String.valueOf(discount), DISC_LENGTH, Alignment.END));
                totalDiscount = totalDiscount + discount;
            } else {
                totalDiscount = totalDiscount + price * qty;
            }
            total = total + price * qty;
            receipt.append("\n");
        }
        receipt.append("_".repeat(Math.max(0, STR_LENGTH)));
        receipt.append("\n");
        receipt.append(getStringGivenLength("TOTAL:",
                String.valueOf(roundingNumber((total), 2)), STR_LENGTH));
        receipt.append("\n");
        receipt.append(getStringGivenLength("DISCOUNT:",
                String.valueOf(roundingNumber((total - totalDiscount), 2)), STR_LENGTH));
        receipt.append("\n");
        receipt.append(getStringGivenLength("TOTAL DISCOUNTED:",
                String.valueOf(roundingNumber((totalDiscount), 2)), STR_LENGTH));
        return receipt;
    }

    public int getReceiptNumber() {
        int receiptNumber = START_NUMBER + 1;

        try {
            receiptNumber = Integer.parseInt(readFiles(PATH_RECEIPT + "/" + FILE_WITH_LAST_RECEIPT)) + 1;
        } catch (IOException e) {
            LOG.error("File creation error " + e);
        } catch (NumberFormatException e) {
            LOG.error("Invalid number in the file " + e);
        }
        return receiptNumber;
    }
}