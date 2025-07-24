package com.labcorp.ui.pages;

import com.labcorp.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Page Object for Job Details Page
 */
public class JobDetailsPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(JobDetailsPage.class);

    @FindBy(css = "h1.job-title")
    private WebElement jobTitle;

    @FindBy(css = ".job-location")
    private WebElement jobLocation;

    @FindBy(css = ".job-id")
    private WebElement jobId;
    
    // Additional locators for specific job details
    private By jobDescriptionItemsLocator = By.xpath("//ul[2]//li");

    /**
     * Get the job title
     * @return Job title text
     */
    public String getJobTitle() {
        return waitForElementToBeVisible(jobTitle).getText().trim();
    }

    /**
     * Get the job location
     * @return Job location text
     */
    public String getJobLocation() {
        return waitForElementToBeVisible(jobLocation).getText().trim();
    }

    /**
     * Get the job ID
     * @return Job ID text
     */
    public String getJobId() {
        return waitForElementToBeVisible(jobId).getText().trim();
    }

    /**
     * Verify job title matches expected value
     * @param expectedTitle Expected job title
     * @return true if job title matches, false otherwise
     */
    public boolean verifyJobTitle(String expectedTitle) {
        String actualTitle = getJobTitle();
        return actualTitle.equals(expectedTitle);
    }

    /**
     * Verify job location contains expected value
     * @param expectedLocation Expected job location
     * @return true if job location contains expected value, false otherwise
     */
    public boolean verifyJobLocation(String expectedLocation) {
        String actualLocation = getJobLocation();
        return actualLocation.contains(expectedLocation);
    }

    /**
     * Verify job ID is not empty
     * @return true if job ID is not empty, false otherwise
     */
    public boolean verifyJobIdExists() {
        String actualJobId = getJobId();
        logger.info("Verifying job ID exists: " + actualJobId);
        return !actualJobId.isEmpty();
    }
    
    /**
     * Verify job ID matches expected value
     * @param expectedJobId Expected job ID
     * @return true if job ID matches, false otherwise
     */
    public boolean verifySpecificJobId(String expectedJobId) {
        String actualJobId = getJobId();
        logger.info("Verifying job ID: Expected [" + expectedJobId + "], Actual [" + actualJobId + "]");
        return actualJobId.contains(expectedJobId);
    }
    
    /**
     * Verify if job is remote
     * @return true if job is remote, false otherwise
     */
    public boolean verifyJobIsRemote() {
        try {
            // Look for text indicating remote status
            WebElement remoteElement = driver.findElement(By.xpath("//*[contains(text(), 'Full-Time Remote: Yes')]"));
            logger.info("Found remote job indicator: " + remoteElement.getText());
            return true;
        } catch (Exception e) {
            logger.info("Job does not appear to be remote");
            return false;
        }
    }
    
    /**
     * Verify job description contains specific text
     * @param expectedText Text to look for in job description
     * @return true if text is found, false otherwise
     */
    public boolean verifyJobDescriptionContains(String expectedText) {
        logger.info("Looking for text in job description: " + expectedText);
        try {
            // Get all job description items
            List<WebElement> descriptionItems = driver.findElements(jobDescriptionItemsLocator);
            
            // Check each item for the expected text
            for (WebElement item : descriptionItems) {
                String itemText = item.getText().trim();
                if (itemText.contains(expectedText)) {
                    logger.info("Found matching text in job description: " + itemText);
                    return true;
                }
            }
            
            logger.info("Text not found in job description");
            return false;
        } catch (Exception e) {
            logger.error("Error verifying job description: " + e.getMessage());
            return false;
        }
    }
}
