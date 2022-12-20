package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.Alignment.END;
import static utils.Alignment.START;
import static utils.StringUtils.getStringGivenLength;
import static utils.StringUtils.getStringGivenLengthWithAlignment;

public class StringUtilsTest {
    private static final String IncorrectString = "Incorrect string";

    @Test
    public void testGetStringGivenLengthWithAlignment() {
        Assert.assertEquals(getStringGivenLengthWithAlignment("str", 5, END), "  str",
                IncorrectString);
        Assert.assertEquals(getStringGivenLengthWithAlignment("str", 5, START), "str  ",
                IncorrectString);
        Assert.assertEquals(getStringGivenLengthWithAlignment("string", 5, START), "strin",
                IncorrectString);
        Assert.assertEquals(getStringGivenLengthWithAlignment("string", 5, END), "strin",
                IncorrectString);
        Assert.assertEquals(getStringGivenLengthWithAlignment("string", 0, END), "",
                IncorrectString);
    }

    @Test
    public void testGetStringGivenLength() {
        Assert.assertEquals(getStringGivenLength("Start", "end", 15), "Start       end",
                IncorrectString);
        Assert.assertEquals(getStringGivenLength("Start", "end", 7), "Startend",
                IncorrectString);
        Assert.assertEquals(getStringGivenLength("", "", 7), "       ",
                IncorrectString);
    }
}