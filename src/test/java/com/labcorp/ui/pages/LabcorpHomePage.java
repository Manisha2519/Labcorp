package com.labcorp.ui.pages;

import com.labcorp.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for Labcorp Home Page
 */
public class LabcorpHomePage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(LabcorpHomePage.class);

    // Using direct findElement instead of @FindBy annotation

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement cookieBannerCloseButton;

    /**
     * Navigate to Labcorp home page
     * @param url Base URL of the application
     * @return LabcorpHomePage instance for method chaining
     */
    public LabcorpHomePage navigateToHomePage(String url) {
        navigateTo(url);
        return this;
    }

    /**
     * Handle cookie banner if present
     * @return LabcorpHomePage instance for method chaining
     */
    public LabcorpHomePage handleCookieBanner() {
        try {
            if (isElementDisplayed(cookieBannerCloseButton)) {
                waitForElementToBeClickable(cookieBannerCloseButton);
                clickWithJS(cookieBannerCloseButton);
                logger.info("Accepted all cookies");
            }
        } catch (Exception e) {
            logger.info("Cookie banner not present or could not be handled: " + e.getMessage());
            // Cookie banner may not be present, continue
        }
        return this;
    }

    /**
     * Click on Careers link and switch to the new tab
     * @return CareersPage instance
     */
    public CareersPage clickCareersLink() {
        handleCookieBanner();
        logger.info("Attempting to click on Careers link");
        
        // Store the original window handle
        String originalWindow = driver.getWindowHandle();
        
        try {
            // Define locators for the careers link
            By xpathLocator = By.xpath("//a[contains(@href, 'careers.labcorp.com/global/en')]");
            
            // First try with explicit wait and xpath
            logger.info("Trying to find careers link with xpath");
            wait.until(ExpectedConditions.elementToBeClickable(xpathLocator));
            WebElement careersLinkElement = driver.findElement(xpathLocator);
            
            // Scroll to element
            scrollToElement(careersLinkElement);
            
            try {
                logger.info("Trying direct click");
                careersLinkElement.click();
                logger.info("Successfully clicked on Careers link with direct click");
            } catch (Exception e) {
                logger.info("Direct click failed, trying JavaScript click: " + e.getMessage());
                clickWithJS(careersLinkElement);
                logger.info("Successfully clicked on Careers link with JS click");
            }
        } catch (Exception e) {
            logger.error("Failed to click on Careers link with xpath: " + e.getMessage());
            
            // Try with CSS selector
            try {
                logger.info("Trying to find careers link with CSS selector");
                By cssLocator = By.cssSelector("a[href*='careers.labcorp.com/global/en']");
                WebElement careersLinkElement = driver.findElement(cssLocator);
                clickWithJS(careersLinkElement);
                logger.info("Successfully clicked on Careers link with CSS selector");
            } catch (Exception ex) {
                // Last resort - direct JavaScript execution
                try {
                    logger.info("Trying direct JavaScript execution");
                    ((JavascriptExecutor) driver).executeScript("document.querySelector('a[href*=\"careers.labcorp.com/global/en\"]').click();");
                    logger.info("Successfully clicked with direct JavaScript");
                } catch (Exception finalEx) {
                    logger.error("All attempts to click Careers link failed: " + finalEx.getMessage());
                    throw new RuntimeException("Failed to click on Careers link after multiple attempts", finalEx);
                }
            }
        }
        
        // Wait for the new tab to open and switch to it
        logger.info("Waiting for new tab to open");
        try {
            wait.until(driver -> driver.getWindowHandles().size() > 1);
            
            // Switch to the new tab (the careers page)
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    logger.info("Switched to new tab with URL: " + driver.getCurrentUrl());
                    break;
                }
            }
            
            // Wait for the careers page to load
            wait.until(driver -> driver.getTitle().contains("Careers") || 
                      driver.getCurrentUrl().contains("careers.labcorp.com"));
            
            logger.info("Successfully navigated to Careers page: " + driver.getCurrentUrl());
        } catch (Exception e) {
            logger.error("Failed to switch to Careers tab: " + e.getMessage());
            throw new RuntimeException("Failed to switch to Careers tab", e);
        }
        
        return new CareersPage();
    }
}
