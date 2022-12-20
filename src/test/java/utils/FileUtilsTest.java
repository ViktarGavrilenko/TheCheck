package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static utils.FileUtils.createDir;

public class FileUtilsTest {

    @Test
    public void testCreateDir() {
        String nameFolder = "testFolder";
        createDir(nameFolder);
        File folder = new File(nameFolder);
        Assert.assertTrue(folder.exists(), "The folder not created");
        folder.delete();
    }
}