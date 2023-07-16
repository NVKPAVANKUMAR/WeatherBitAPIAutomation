package com.qa.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author NVK PAVANKUMAR This is a super class for all the classes in this project.
 */
public class TestBase {

    public static FileInputStream fileInputStream;
    public static Properties properties;
    public static int STATUS_CODE_200 = 200;
    public static int STATUS_CODE_400 = 400;

    /**
     * This method initializes the config.properties file
     */
    public static void intialization() {
        try {
            fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties");

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        properties = new Properties();
        try {
            properties.load(fileInputStream); // loads the input stream
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
