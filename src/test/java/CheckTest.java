import config.ConfigurationProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Properties;

public class CheckTest {

    protected static final Properties configProperties =
            ConfigurationProperties.createProperties("receipt.properties");
    private static final String PATH_RECEIPT = configProperties.getProperty("pathReceipt");
    Check check = new Check();

    @Test
    public void testGenerateCheck() {
        int numberOfFilesBefore = new File(PATH_RECEIPT).listFiles().length;
        check.generateCheck("1-1 card-789");
        Assert.assertEquals(new File(PATH_RECEIPT).listFiles().length, numberOfFilesBefore + 1,
                "Check not created");
    }

    @Test
    public void testNumberCheckInFile() {
        int checkNumber = check.getReceiptNumber();
        check.generateCheck("1-1 card-1234");
        Assert.assertEquals(check.getReceiptNumber(), checkNumber + 1, "Invalid number of check");
    }

    @Test
    public void testCheckNameIsNumber() {
        int checkNumber = check.getReceiptNumber();
        check.generateCheck("1-1");
        Assert.assertTrue(new File(PATH_RECEIPT + "/" + checkNumber).isFile(),
                "The name of the new check is incorrect");
    }
}