package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtils {
    public static Double roundingNumber(Double number, int limit) {
        BigDecimal rounding = BigDecimal.valueOf(number);
        return rounding.setScale(limit, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double getPriceWithDiscount(Double price, int discountPercentage) {
        return price - price * discountPercentage / 100;
    }
}