# API Automation Framework with REST Assured and Cucumber

This project demonstrates API test automation using REST Assured, Cucumber BDD, and Java. The tests are structured in BDD format as requested.

## Project Structure

```
├── src
│   └── test
│       ├── java
│       │   └── com
│       │       └── labcorp
│       │           └── api
│       │               ├── base
│       │               │   └── ApiTestBase.java
│       │               ├── runner
│       │               │   └── TestRunner.java
│       │               ├── stepdefs
│       │               │   └── ApiStepDefinitions.java
│       │               └── utils
│       │                   └── PayloadUtils.java
│       └── resources
│           └── features
│               └── api_tests.feature
└── pom.xml
```

## Features

- BDD-style test scenarios using Cucumber
- REST Assured for API testing
- Detailed request and response validation
- JSON payload handling
- HTML and JSON test reports

## Test Scenarios

1. **GET Request Test**
   - Validates retrieving customer information
   - Verifies response status code, path, ID, and headers

2. **POST Request Test**
   - Creates a complex JSON payload with customer, order, payment, and shipping details
   - Validates the response contains expected data

## Prerequisites

- Java 11 or higher
- Maven

## How to Run Tests

1. Clone the repository
2. Navigate to the project directory
3. Run the tests using Maven:

```bash
mvn clean test
```

## Test Reports

After test execution, reports can be found at:
- HTML Report: `target/cucumber-reports/cucumber-pretty.html`
- JSON Report: `target/cucumber-reports/CucumberTestReport.json`

## Notes

- The tests are configured to run against the beeceptor mock API
- The payload structure follows the provided example in the requirements
