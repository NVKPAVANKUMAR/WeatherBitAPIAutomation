package com.qa.test;

import com.qa.apidefinitions.WeatherAPIClient;
import com.qa.framework.TestBase;
import com.qa.framework.Utility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static reporting.ExtentTestManager.startTest;

/**
 * @author NVK PAVANKUMAR This is a TestNG class which tests the weather bit API using coordinates
 */
public class WeatherAPILatLongTest extends TestBase {

    public static Response rawResponse;
    public static JsonPath jsonResponse;
    public static Logger log;

    /**
     * This method initializes the Logger class for logging mechanism
     */
    @BeforeTest
    public void setup() {
        log = Logger.getLogger(WeatherAPILatLongTest.class);
    }

    /**
     * A test method which validates status code is 200 from the response
     */
    @Test(priority = 1)
    public void validateCoordinatesQueryStatusCodeIs200(Method method) {
        startTest(method.getName(), method.getName().replace("_", " "));
        rawResponse = WeatherAPIClient.executeWeatherAPI("current", "Coordinates"); // Weather client executes the weather api
        jsonResponse = Utility.convertToJsonResponse(rawResponse);
        int statusCode = Utility.getResponseCode(rawResponse);
        Assert.assertEquals(statusCode, STATUS_CODE_200, "API Execution failed.Status code is not 200"); // Assert status code

        log.info("Response details are as below :");
        log.info("Status code = " + statusCode);
    }

    /**
     * A test method which validates result count is 1 from response
     */
    @Test(priority = 2, dependsOnMethods = "validateCoordinatesQueryStatusCodeIs200")
    public void validateSingleResultReturnedForCoordinate(Method method) {
        startTest(method.getName(), method.getName().replace("_", " "));
        String count = Utility.getValue(jsonResponse, "count");
        Assert.assertEquals(count, "1", "API response showing incorrect count");
        log.info("Count =   " + count);
    }

    /**
     * A test method which validates city name value from response
     */
    @Test(priority = 3, dependsOnMethods = "validateCoordinatesQueryStatusCodeIs200")
    public void validateCityNameOfCoordinate(Method method) {
        startTest(method.getName(), method.getName().replace("_", " "));
        String actualCityName = Utility.getValue(jsonResponse, "data[0].city_name");
        Assert.assertEquals(actualCityName, properties.getProperty("cityName"), "API response showing incorrect city name");
        log.info("City Name = " + actualCityName);
    }

    /**
     * A test method which validates important keys are present in the response
     */
    @Test(priority = 2, dependsOnMethods = "validateCoordinatesQueryStatusCodeIs200")
    public void validateCoordinateQueryReturnsEssentialKeys(Method method) {
        startTest(method.getName(), method.getName().replace("_", " "));
        Assert.assertTrue(rawResponse.asString().contains("app_temp"), "Feels like temperature is not present");
        Assert.assertTrue(rawResponse.asString().contains("temp"), "Temperature not present in response");
    }
}
