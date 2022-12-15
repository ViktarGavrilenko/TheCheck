package utils;

public class StringUtils {
    public static String getStringGivenLength(String str, int length, Alignment alignment) {
        int lengthStr = str.length();
        StringBuilder outStr = new StringBuilder();
        if (lengthStr > length) {
            lengthStr = length;
            str = str.substring(0, length);
        }
        String repeat = " ".repeat(Math.max(0, length - lengthStr));
        switch (alignment) {
            case START -> {
                outStr.append(str);
                outStr.append(repeat);
            }
            case END -> {
                outStr.append(repeat);
                outStr.append(str);
            }
        }
        return String.valueOf(outStr);
    }

    public static String getStringGivenLength(String firstStr, String endStr, int length){
        String repeat = " ".repeat(Math.max(0, length - firstStr.length() - endStr.length()));
        return firstStr + repeat +endStr;
    }
}
