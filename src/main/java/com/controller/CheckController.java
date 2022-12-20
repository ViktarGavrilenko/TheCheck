package com.controller;

import config.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

import static utils.FileUtils.readFileForHtml;

@RestController()
public class CheckController {
    protected static final Properties configProperties =
            ConfigurationProperties.createProperties("receipt.properties");
    private static final String PATH_RECEIPT = configProperties.getProperty("pathReceipt");

    @GetMapping("/check")
    public StringBuilder getCheck(@RequestParam(value = "id") String id) {
        try {
            return readFileForHtml(PATH_RECEIPT + "/" + id);
        } catch (NumberFormatException e) {
            return new StringBuilder("Invalid number in the file " + e);
        }
    }
}
