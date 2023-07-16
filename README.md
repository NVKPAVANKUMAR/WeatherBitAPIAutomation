# API Test Automation Repository

This repository contains a Java-based test automation framework for API testing, utilizing Rest Assured, TestNG, and Maven.
This communicates to open Weather Bit API which provides current Weather Data for multiple places in the world.

## Prerequisites

Before running the tests, ensure that the following prerequisites are met:

- Java Development Kit (JDK) is installed on your machine.
- Maven is installed on your machine.
- An integrated development environment (IDE) such as IntelliJ or Eclipse is installed.

## Setup & Running Tests

1. Using terminal or cmd clone this repository to your local machine using the following command:

```
git clone https://github.com/sat-kand/WeatherBitAPIAutomation.git
```

2. Navigate to the root folder of cloned repo in terminal and execute the following command to build and run the tests

```
mvn clean install
```

## Test Reports & Artifacts:

Test reports are generated automatically after each test run. The terminal will show output like this:
```shell
Results :

Failed tests: validateResultsCountTest(com.qa.test.WeatherAPIPostCodeTest): API response showing incorrect count expected [24] but found [1]
  validateInvalidPostCodeTest(com.qa.test.WeatherAPIPostCodeTest): API response showing some data for invalid post code expected [0] but found [1]

Tests run: 11, Failures: 2, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE

```
The detailed reports can be found in the `/target/surefire-reports` directory. Open the `index.html` file in a web browser to view the detailed test results.

## Tools and Technology used:

- Java 8
- Rest assured library (3.0.6)
- TestNG (6.14.3),
- Maven (4.0.0)
- Log4j API(1.2.17)
- API used: https://www.weatherbit.io/api/swaggerui/weather-api-v2#/


## Project Structure:

- `src/main/java/com.qa/framework/TestBase.java` : This is a super class of all classes and it loads the configuration file.
- `src/main/java/com.qa/framework/Utility.java` : This contains various utility methods for parsing and reading the response.
- `src/main/java/com.qa/apidefinitions/WeatherAPIClient.java` This contains methods to construct and execute the API request.
- `src/test/java/com.qa.test`: The test classes can be found in the directory.
- `src/main/resources`: Directory contains properties files with various paramaeters for requests and logging.
- `testng.xml` : This is a testNG configuration file.
- `pom.xml` : This file contains all the maven dependencies.

## Known Bugs

Following test cases will fail due to the bugs in WeatherbitAPI
1. InvalidPostcodeTest -> Some response is returned instead of error message
2. ValidateMultipleLocationResults -> Weather data of only one place is returned eventhough the provided post code has multiple locations