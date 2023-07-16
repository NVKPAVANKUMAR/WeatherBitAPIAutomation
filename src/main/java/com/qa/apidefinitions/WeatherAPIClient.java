package com.qa.apidefinitions;

import com.qa.framework.TestBase;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

/**
 * @author NVK PAVANKUMAR WeatherAPIClient acts as client responsible for constructing
 * the request URL and executing the weather API using Get method.
 */
public class WeatherAPIClient extends TestBase {

    public static Response response;
    public static RequestSpecification requestSpecification;

    /**
     * This method is responsible for executing the Weatherbit API using GET method
     *
     * @return response in raw format
     */

    public static Response executeWeatherAPI(String Path, String Query) {
        intialization();

        try {
            response = given().spec(generateRequestSpecification(Query)).when()
                    .get(Path).then().extract().response();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * These methods generate request based on the query parameters passed in
     * config.properties file. It also configures filters for request and response
     * logging.
     *
     * @return requestSpecification This is newly built request based on the
     * parameters.
     * @throws FileNotFoundException
     */

    public static RequestSpecification generateRequestSpecification(String Query) throws FileNotFoundException {

        PrintStream requestPrintStream = new PrintStream(
                new FileOutputStream(System.getProperty("user.dir") + "/target/request.log"));
        PrintStream responsePrintStream = new PrintStream(
                new FileOutputStream(System.getProperty("user.dir") + "/target/response.log"));
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(properties.getProperty("baseurl"));
        if (Query == "PostCode") {
            requestBuilder.addQueryParam("postal_code", properties.getProperty("postal_code"));
        } else if (Query == "IncorrectPostCode") {
            requestBuilder.addQueryParam("postal_code", properties.getProperty("incorrect_postal_code"));
        } else if (Query == "Coordinates") {
            requestBuilder.addQueryParam("lat", properties.getProperty("latitude"));
            requestBuilder.addQueryParam("lon", properties.getProperty("longitude"));
        }
        requestBuilder.addQueryParam("key", properties.getProperty("apiKey"));
        requestBuilder.addFilter(RequestLoggingFilter.logRequestTo(requestPrintStream));// generate log for request.
        requestBuilder.addFilter(ResponseLoggingFilter.logResponseTo(responsePrintStream));// generate log for response
        requestSpecification = requestBuilder.build();
        return requestSpecification;
    }
}
