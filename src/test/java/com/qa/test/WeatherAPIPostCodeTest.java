package com.qa.test;

import com.qa.framework.Utility;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.apidefinitions.WeatherAPIClient;
import com.qa.framework.TestBase;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 *
 * @author NVK PAVANKUMAR This is a TestNG class which tests the weather bit API using post code
 *
 */
public class WeatherAPIPostCodeTest extends TestBase {

    public static Response rawResponse;
    public static JsonPath jsonResponse;
    public static Logger log;

    /**
     * This method initializes the Logger class for logging mechanism
     */
    @BeforeTest
    public void setup() {
        log = Logger.getLogger(WeatherAPIPostCodeTest.class);
    }

    /**
     * A test method which validates status code is 200 from the response
     */
    @Test(priority = 1)
    public void validatePostCodeQueryStatusIs200() {

        rawResponse = WeatherAPIClient.executeWeatherAPI("current","PostCode"); // Weather client executes the weather api
        jsonResponse = Utility.convertToJsonResponse(rawResponse);
        int statusCode = Utility.getResponseCode(rawResponse);
        Assert.assertEquals(statusCode, STATUS_CODE_200, "API Execution failed.Status code is not 200"); // Assert status code

        log.info("Response details are as below :");
        log.info("Status code = " + statusCode);
    }

    /**
     * A test method which validates important keys are present in the response
     */
    @Test(priority=2,dependsOnMethods = "validatePostCodeQueryStatusIs200")
    public void ValidatePostCodeQueryReturnEssentialKeys()
    {
        Assert.assertTrue(rawResponse.asString().contains("app_temp"),"Feels like temperature is not present");
        Assert.assertTrue(rawResponse.asString().contains("temp"), "Temperature not present in response");
    }

    /**
     * A test method which validates city name from response
     */
    @Test(priority=2,dependsOnMethods = "validatePostCodeQueryStatusIs200")
    public void validatePostCodeCityName()
    {
        String actualCityName=Utility.getValue(jsonResponse, "data[0].city_name");
        Assert.assertEquals(actualCityName,properties.getProperty("cityName"),"API response showing incorrect city name"); //Assert city name
        log.info("City Name = " +actualCityName);
    }

    /**
     * A test method which validates count from response
     */
    //BUG: Post code 2020 to return 24 records but returns only one
    @Test(priority=3,dependsOnMethods = "validatePostCodeQueryStatusIs200")
    public void validateMultipleLocationResults()
    {
        String count=Utility.getValue(jsonResponse, "count");
        Assert.assertEquals(count,"24","API response showing incorrect count"); //Assert count
        log.info("Count =   " +count);
    }

    /**
     * A test method which validates status code is 200 from the response
     */
    @Test(priority = 4)
    public void IncorrectPathTest() {

        rawResponse = WeatherAPIClient.executeWeatherAPI("incorrect", "PostCode"); // Calling Weatherbit API with incorrect Path
        int statusCode = Utility.getResponseCode(rawResponse);
        Assert.assertEquals(statusCode, STATUS_CODE_400, "HTTP status code for bad request not returned"); // Assert status code

        log.info("Response details are as below :");
        log.info("Status code = " + statusCode);
    }

    /**
     * A test method which validates status code is 200 from the response
     */
    @Test(priority = 4)
    public void IncorrectQueryTest() {

        rawResponse = WeatherAPIClient.executeWeatherAPI("current", "incorrect"); // Calling Weatherbit API with incorrect query
        int statusCode = Utility.getResponseCode(rawResponse);
        Assert.assertEquals(statusCode, STATUS_CODE_400, "HTTP status code for bad request not returned"); // Assert status code

        log.info("Response details are as below :");
        log.info("Status code = " + statusCode);
    }

    /**
     * A test method which validates when invalid postcode is sent
     */
    //BUG: Post code 0 is invalid but API returns some data instead of error
    @Test(priority = 4)
    public void InvalidPostCodeTest() {

        rawResponse = WeatherAPIClient.executeWeatherAPI("current","IncorrectPostCode"); // Weather client executes the weather api
        jsonResponse = Utility.convertToJsonResponse(rawResponse);
        int statusCode = Utility.getResponseCode(rawResponse);
        Assert.assertEquals(statusCode, STATUS_CODE_200, "API Execution failed.Status code is not 200"); // Assert status code

        String count=Utility.getValue(jsonResponse, "count");
        Assert.assertEquals(count,"0","API response showing some data for invalid post code"); //Assert count is 0 as it is invalid post code
    }
}
