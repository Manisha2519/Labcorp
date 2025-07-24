package com.labcorp.ui.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test runner for executing UI tests only
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/ui_tests.feature",
        glue = {"com.labcorp.ui.stepdefs"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui/cucumber-pretty.html",
                "json:target/cucumber-reports/ui/CucumberTestReport.json"
        },
        monochrome = true
)
public class UiTestRunner {
    // This class is intentionally empty. It's used only as a holder for the annotations
}
