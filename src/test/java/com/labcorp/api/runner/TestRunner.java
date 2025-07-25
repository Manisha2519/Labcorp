package com.labcorp.api.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test runner for executing Cucumber BDD tests
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.labcorp.api.stepdefs", "com.labcorp.ui.stepdefs"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/all/cucumber-pretty.html",
                "json:target/cucumber-reports/all/CucumberTestReport.json"
        },
        monochrome = true
)
public class TestRunner {
    // This class is intentionally empty. It's used only as a holder for the annotations
}
