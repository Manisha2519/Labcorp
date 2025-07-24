package com.labcorp.ui.pages;

import com.labcorp.ui.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {

    // Page elements using @FindBy annotation
    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    /**
     * Navigate to login page
     * @param baseUrl Base URL of the application
     * @return LoginPage instance for method chaining
     */
    public LoginPage navigateToLoginPage(String baseUrl) {
        navigateTo(baseUrl + "/login");
        return this;
    }

    /**
     * Enter username
     * @param username Username to enter
     * @return LoginPage instance for method chaining
     */
    public LoginPage enterUsername(String username) {
        waitForElementToBeVisible(usernameInput).clear();
        usernameInput.sendKeys(username);
        return this;
    }

    /**
     * Enter password
     * @param password Password to enter
     * @return LoginPage instance for method chaining
     */
    public LoginPage enterPassword(String password) {
        waitForElementToBeVisible(passwordInput).clear();
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Click login button
     * @return HomePage instance after successful login
     */
    public void clickLoginButton() {
        waitForElementToBeClickable(loginButton).click();
    }

    /**
     * Login with username and password
     * @param username Username to enter
     * @param password Password to enter
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Get error message text
     * @return Error message text
     */
    public String getErrorMessageText() {
        return waitForElementToBeVisible(errorMessage).getText();
    }
}
