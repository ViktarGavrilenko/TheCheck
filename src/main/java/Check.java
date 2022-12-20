import config.ConfigurationProperties;
import models.Order;
import models.SimpleItem;
import org.apache.log4j.Logger;
import utils.Alignment;
import utils.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static dao.ProductDao.getProductById;
import static dao.ProductDao.isProductWithId;
import static discountconditions.DiscCard.getDiscountItem;
import static discountconditions.DiscCard.getMaxDiscount;
import static discountconditions.Promotion.getPromotionItem;
import static discountconditions.Promotion.isQuantityPromotionItemEnough;
import static utils.ArithmeticUtils.*;
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
    private static final String KEYWORD_FOR_DISCOUNT_CARD = configProperties.getProperty("keywordForDiscountCard");

    private static final String SLASH = "/";

    private final List<Order> listOrder = new ArrayList<>();
    private final List<String> discountCards = new ArrayList<>();
    private int discountPercentage = 0;

    public void generateCheck(String inputStr) {
        createListProductAndDiscountCardFromStr(inputStr);
        discountPercentage = getMaxDiscount(discountCards);
        createDir(PATH_RECEIPT);

        int receiptNumber = getReceiptNumber();
        writeLastNumberInFile(receiptNumber, PATH_RECEIPT + SLASH + FILE_WITH_LAST_RECEIPT);
        String pathReceipt = PATH_RECEIPT + SLASH + receiptNumber;

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
            if (item[0].toLowerCase().equals(KEYWORD_FOR_DISCOUNT_CARD)) {
                discountCards.add(item[1]);
            } else {
                if (isInteger(item[0]) && isLong(item[1]) && isProductWithId(Integer.parseInt(item[0]))) {
                    SimpleItem simpleProduct = getProductById(Integer.parseInt(item[0]));
                    Order order = new Order(simpleProduct, Long.parseLong(item[1]));
                    listOrder.add(order);
                }
            }
        }
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
        headerReceipt.append(StringUtils.getStringGivenLengthWithAlignment("QTY", QTY_LENGTH, Alignment.START));
        headerReceipt.append(StringUtils.getStringGivenLengthWithAlignment("DESCRIPTION", DESCRIPTION_LENGTH, Alignment.END));
        headerReceipt.append(StringUtils.getStringGivenLengthWithAlignment("PRICE", PRICE_LENGTH, Alignment.END));
        headerReceipt.append(StringUtils.getStringGivenLengthWithAlignment("TOTAL", TOTAL_LENGTH, Alignment.END));
        headerReceipt.append(StringUtils.getStringGivenLengthWithAlignment("DISC", DISC_LENGTH, Alignment.END));
        headerReceipt.append("\n");
        return headerReceipt;
    }

    public StringBuilder getBodyReceipt() {
        StringBuilder receipt = new StringBuilder();
        double total = 0F;
        double totalDiscount = 0F;
        boolean isQtyPromotionEnough = isQuantityPromotionItemEnough(listOrder);
        for (Order order : listOrder) {
            Double price = order.getItem().getPrice();
            Long qty = order.getQuantity();
            receipt.append(StringUtils.getStringGivenLengthWithAlignment(String.valueOf(qty), QTY_LENGTH, Alignment.START));
            receipt.append(StringUtils.getStringGivenLengthWithAlignment(
                    String.valueOf(order.getItem().getName()), DESCRIPTION_LENGTH, Alignment.END));
            receipt.append(StringUtils.getStringGivenLengthWithAlignment(String.valueOf(price), PRICE_LENGTH, Alignment.END));
            receipt.append(StringUtils.getStringGivenLengthWithAlignment(
                    String.valueOf(roundingNumber((price * qty), 2)), TOTAL_LENGTH, Alignment.END));

            if (isQtyPromotionEnough) {
                order.setItem(getPromotionItem(order.getItem()));
            }

            if (!discountCards.isEmpty()) {
                order.setItem(getDiscountItem(order.getItem(), discountPercentage));
            }

            receipt.append(StringUtils.getStringGivenLengthWithAlignment(String.valueOf(roundingNumber(
                    order.getItem().getPrice() * qty, 2)), DISC_LENGTH, Alignment.END));
            totalDiscount = totalDiscount + order.getItem().getPrice() * qty;

            total = total + price * qty;
            receipt.append("\n");
        }
        receipt.append(getFooterReceipt(total, totalDiscount));
        return receipt;
    }

    public StringBuilder getFooterReceipt(Double total, Double totalDiscount) {
        StringBuilder receipt = new StringBuilder();
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