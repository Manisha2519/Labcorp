package com.labcorp.api.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test runner for executing API tests only
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api_tests.feature",
        glue = {"com.labcorp.api.stepdefs"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber-pretty.html",
                "json:target/cucumber-reports/api/CucumberTestReport.json"
        },
        monochrome = true
)
public class ApiTestRunner {
    // This class is intentionally empty. It's used only as a holder for the annotations
}
