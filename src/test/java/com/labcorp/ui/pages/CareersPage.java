package com.labcorp.ui.pages;

import com.labcorp.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for Labcorp Careers Page
 */
public class CareersPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(CareersPage.class);

    // Using direct findElement instead of @FindBy for more flexibility
    private By searchInputLocator = By.cssSelector("input[data-ph-at-id='globalsearch-input']");
    private By searchButtonLocator = By.cssSelector("button[data-ph-at-id='globalsearch-submit']");
    private By searchIconLocator = By.cssSelector("span.submit-icon i.icon-search-3");
    private By searchResultsLocator = By.cssSelector(".job-card");
    private By specificJobSpanLocator = By.cssSelector("span[data-ph-id='ph-page-element-page11-Zk12Zp']");
    
    // Specific XPath for Senior Software Engineer position
    private By seniorSoftwareEngineerLocator = By.xpath("(//div//span[contains(text(), 'Senior Software Engineer ')])[1]");

    /**
     * Search for a job position
     * @param position job position to search for
     * @return CareersPage instance
     */
    public CareersPage searchForPosition(String position) {
        logger.info("Searching for position: " + position);
        try {
            // Wait for the search page to fully load with a longer timeout
            logger.info("Waiting for search input to be visible");
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
            
            // Find the search input element and enter the position
            WebElement searchInput = driver.findElement(searchInputLocator);
            searchInput.clear();
            searchInput.sendKeys(position);
            logger.info("Entered search text: " + position);
            
            // Try multiple approaches to trigger the search
            try {
                // First try: Click the search icon
                logger.info("Trying to click search icon");
                WebElement searchIcon = driver.findElement(searchIconLocator);
                wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
                searchIcon.click();
                logger.info("Clicked search icon");
            } catch (Exception e) {
                logger.info("Failed to click search icon: " + e.getMessage());
                
                try {
                    // Second try: Click the search button
                    logger.info("Trying to click search button");
                    WebElement searchButton = driver.findElement(searchButtonLocator);
                    wait.until(ExpectedConditions.elementToBeClickable(searchButton));
                    searchButton.click();
                    logger.info("Clicked search button");
                } catch (Exception ex) {
                    // Third try: Press Enter key
                    logger.info("Trying to press Enter key: " + ex.getMessage());
                    searchInput.sendKeys(Keys.ENTER);
                    logger.info("Pressed Enter key");
                }
            }
            
            // Wait for search results to load
            try {
                // Wait for results to appear with a longer timeout
                logger.info("Waiting for search results to appear");
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultsLocator));
                
                // Close any autocomplete dropdown if needed
                searchInput.sendKeys(Keys.ESCAPE);
                
                // Get the count of search results
                List<WebElement> results = driver.findElements(searchResultsLocator);
                logger.info("Search completed, found " + results.size() + " results");
            } catch (Exception e) {
                logger.warn("Error while waiting for search results: " + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error searching for position: " + e.getMessage());
            throw new RuntimeException("Failed to search for position: " + position, e);
        }
        return this;
    }

    /**
     * Select a job position from search results
     * @return JobDetailsPage instance
     */
    public JobDetailsPage selectJobPosition() {
        logger.info("Selecting job position from search results");
        
        try {
            // First try to find the Senior Software Engineer position using the specific XPath
            logger.info("Looking for Senior Software Engineer position using XPath");
            
            try {
                // Wait with a longer timeout (30 seconds)
                WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                longWait.until(ExpectedConditions.presenceOfElementLocated(seniorSoftwareEngineerLocator));
                
                WebElement seniorSoftwareEngineerElement = driver.findElement(seniorSoftwareEngineerLocator);
                logger.info("Found Senior Software Engineer position: " + seniorSoftwareEngineerElement.getText());
                
                // Get the parent element that's clickable (job card)
                WebElement jobCard = seniorSoftwareEngineerElement;
                while (jobCard != null && !jobCard.getTagName().equalsIgnoreCase("a")) {
                    try {
                        jobCard = jobCard.findElement(By.xpath(".."));
                    } catch (Exception e) {
                        break;
                    }
                }
                
                if (jobCard != null) {
                    scrollToElement(jobCard);
                    waitForElementToBeClickable(jobCard);
                    
                    try {
                        logger.info("Attempting to click on Senior Software Engineer job");
                        jobCard.click();
                        logger.info("Successfully clicked on Senior Software Engineer job");
                        return new JobDetailsPage();
                    } catch (Exception e) {
                        logger.info("Direct click failed, trying JavaScript click: " + e.getMessage());
                        clickWithJS(jobCard);
                        logger.info("Successfully clicked on Senior Software Engineer job using JavaScript");
                        return new JobDetailsPage();
                    }
                }
            } catch (Exception e) {
                logger.warn("Could not find Senior Software Engineer using XPath, trying alternative approach: " + e.getMessage());
            }
            
            // If the above fails, try to find the job with data-ph-id attribute
            try {
                logger.info("Looking for specific job span with data-ph-id='ph-page-element-page11-Zk12Zp'");
                wait.until(ExpectedConditions.presenceOfElementLocated(specificJobSpanLocator));
                WebElement specificJobSpan = driver.findElement(specificJobSpanLocator);
                
                // Get the parent job card element that contains this span
                WebElement jobCard = specificJobSpan;
                while (jobCard != null && !jobCard.getTagName().equalsIgnoreCase("a") && 
                       !jobCard.getAttribute("class").contains("job-card")) {
                    try {
                        jobCard = jobCard.findElement(By.xpath(".."));
                    } catch (Exception e) {
                        break;
                    }
                }
                
                if (jobCard != null) {
                    logger.info("Found specific job with text: " + specificJobSpan.getText());
                    scrollToElement(jobCard);
                    waitForElementToBeClickable(jobCard);
                    
                    try {
                        logger.info("Attempting to click on specific job");
                        jobCard.click();
                        logger.info("Successfully clicked on specific job");
                        return new JobDetailsPage();
                    } catch (Exception e) {
                        logger.info("Direct click failed, trying JavaScript click: " + e.getMessage());
                        clickWithJS(jobCard);
                        logger.info("Successfully clicked on specific job using JavaScript");
                        return new JobDetailsPage();
                    }
                }
            } catch (Exception e) {
                logger.warn("Could not find specific job span, falling back to first result: " + e.getMessage());
            }
            
            // Fallback: Get all search results
            List<WebElement> results = driver.findElements(searchResultsLocator);
            
            if (results.isEmpty()) {
                logger.error("No job search results found");
                throw new RuntimeException("No job search results found");
            }
            
            logger.info("Found " + results.size() + " job results");
            
            // Click on the first job result
            WebElement firstJob = results.get(0);
            scrollToElement(firstJob);
            waitForElementToBeClickable(firstJob);
            
            try {
                logger.info("Attempting to click on first job result");
                firstJob.click();
            } catch (Exception e) {
                logger.info("Direct click failed, trying JavaScript click: " + e.getMessage());
                clickWithJS(firstJob);
            }
            
            logger.info("Successfully selected first job from search results");
            
        } catch (Exception e) {
            logger.error("Failed to select job position: " + e.getMessage());
            throw new RuntimeException("Failed to select job position", e);
        }
        
        return new JobDetailsPage();
    }
}
