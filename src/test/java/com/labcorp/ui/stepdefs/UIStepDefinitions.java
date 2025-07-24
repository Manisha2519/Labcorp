package com.labcorp.ui.stepdefs;

import com.labcorp.ui.base.WebDriverManager;
import com.labcorp.ui.pages.CareersPage;
import com.labcorp.ui.pages.JobDetailsPage;
import com.labcorp.ui.pages.LabcorpHomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Step definitions for UI tests
 */
public class UIStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(UIStepDefinitions.class);
    // Driver is managed by WebDriverManager class
    private LabcorpHomePage homePage;
    private CareersPage careersPage;
    private JobDetailsPage jobDetailsPage;
    private String capturedJobTitle;
    private String capturedJobLocation;
    private String capturedJobId;

    @Before
    public void setup() {
        // Setup is handled in the step definitions
    }

    @After
    public void tearDown() {
        // Browser closure is handled in the step definitions
    }

    @Given("I open the browser {string}")
    public void iOpenTheBrowser(String browser) {
        WebDriverManager.initializeDriver(browser);
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        homePage = new LabcorpHomePage();
        homePage.navigateToHomePage(url);
    }

    @And("I click on the Careers link")
    public void iClickOnTheCareersLink() {
        careersPage = homePage.clickCareersLink();
    }

    @And("I search for job position {string}")
    public void iSearchForJobPosition(String position) {
        careersPage.searchForPosition(position);
    }

    @And("I select a job position from search results")
    public void iSelectAJobPositionFromSearchResults() {
        jobDetailsPage = careersPage.selectJobPosition();
        
        // Capture the job details for later assertions
        // capturedJobTitle = jobDetailsPage.getJobTitle();
        // capturedJobLocation = jobDetailsPage.getJobLocation();
        // capturedJobId = jobDetailsPage.getJobId();
        
        // // Log the captured details for debugging
        // System.out.println("Captured Job Title: " + capturedJobTitle);
        // System.out.println("Captured Job Location: " + capturedJobLocation);
        // System.out.println("Captured Job ID: " + capturedJobId);
    }

    @Then("I should verify the job title")
    public void iShouldVerifyTheJobTitle() {
        Assert.assertFalse("Job title should not be empty", capturedJobTitle.isEmpty());
        System.out.println("Verified Job Title: " + capturedJobTitle);
    }

    @And("I should verify the job location")
    public void iShouldVerifyTheJobLocation() {
        Assert.assertFalse("Job location should not be empty", capturedJobLocation.isEmpty());
        System.out.println("Verified Job Location: " + capturedJobLocation);
    }

    @And("I should verify the job ID")
    public void iShouldVerifyTheJobID() {
        Assert.assertFalse("Job ID should not be empty", capturedJobId.isEmpty());
        System.out.println("Verified Job ID: " + capturedJobId);
    }

    @And("I close the browser")
    public void iCloseTheBrowser() {
        WebDriverManager.quitDriver();
    }
    
    @Then("I should verify the job ID is {string}")
    public void iShouldVerifyTheJobIdIs(String expectedJobId) {
        logger.info("Verifying job ID is: " + expectedJobId);
        Assert.assertTrue("Job ID should contain " + expectedJobId, 
                jobDetailsPage.verifySpecificJobId(expectedJobId));
    }
    
    @Then("I should verify the job is remote")
    public void iShouldVerifyTheJobIsRemote() {
        logger.info("Verifying job is remote");
        Assert.assertTrue("Job should be remote", jobDetailsPage.verifyJobIsRemote());
    }
    
    @Then("I should verify the job description contains {string}")
    public void iShouldVerifyTheJobDescriptionContains(String expectedText) {
        logger.info("Verifying job description contains: " + expectedText);
        Assert.assertTrue("Job description should contain: " + expectedText,
                jobDetailsPage.verifyJobDescriptionContains(expectedText));
    }
}
