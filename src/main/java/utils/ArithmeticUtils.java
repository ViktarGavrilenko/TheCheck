package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtils {
    public static Double roundingNumber(Double number, int limit){
        BigDecimal rounding = new BigDecimal(number);
        return rounding.setScale(limit, RoundingMode.HALF_UP).doubleValue();
    }
}