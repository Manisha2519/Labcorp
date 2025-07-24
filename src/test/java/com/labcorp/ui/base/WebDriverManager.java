package com.labcorp.ui.base;

// Using fully qualified name to avoid conflict with our own WebDriverManager class
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

/**
 * Manages WebDriver instances for browser automation
 */
public class WebDriverManager {
    private static WebDriver driver;
    private static final int IMPLICIT_WAIT_TIME = 10;
    private static final int PAGE_LOAD_TIMEOUT = 30;

    private WebDriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initialize WebDriver based on browser name
     * @param browser Browser name (chrome, firefox, edge, safari)
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                // Force WebDriverManager to download the latest ChromeDriver that matches the installed Chrome
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().clearDriverCache().clearResolutionCache().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                // Add additional options to help with compatibility
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser " + browser + " is not supported");
        }

        // Configure timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        driver.manage().window().maximize();
        
        return driver;
    }

    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Close the browser and quit WebDriver
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }
}
