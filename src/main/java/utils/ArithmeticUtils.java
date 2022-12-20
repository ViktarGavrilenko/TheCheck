package utils;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtils {
    private static final Logger LOG = Logger.getLogger(ArithmeticUtils.class);

    public static Double roundingNumber(Double number, int limit) {
        BigDecimal rounding = BigDecimal.valueOf(number);
        return rounding.setScale(limit, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double getPriceWithDiscount(Double price, int discountPercentage) {
        return price - price * discountPercentage / 100;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            LOG.error("Line " + s + " is not a number of Integer type");
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            LOG.error("Line " + s + " is not a number of Long type");
            return false;
        }
    }
}