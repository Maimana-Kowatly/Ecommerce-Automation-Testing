package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Page Object Model for Login Page
public class LoginPage {
    private WebDriver driver;
    private By userNameField = By.id("email");
    private By passwordField = By.id("pass");
    private By loginButton = By.id("send2");
    private By customerMenu = By.cssSelector(".customer-name button");
    private By logoutButton = By.cssSelector(".customer-menu .authorization-link a");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public void open() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");
    }
    public void login(String username, String password) {
        System.out.println("Username: " + username + " , Password: " + password);
        driver.findElement(this.userNameField).sendKeys(username);
        driver.findElement(this.passwordField).sendKeys(password);
        driver.findElement(this.loginButton).click();
    }
    public boolean isUserLoggedIn() {
        try {
            // Check if the login success message or user menu is visible
            By loginMessage = By.cssSelector("li.greet.welcome");
            return driver.findElement(loginMessage).isDisplayed();
        } catch (Exception e) {
            return false; // Element not found, meaning user is not logged in
        }
    }
    public void logout() throws InterruptedException {
        // Click on the dropdown menu to open options
        driver.findElement(customerMenu).click();
        // Click on the Sign Out button
        driver.findElement(logoutButton).click();

        // Wait for the first redirection to logoutSuccess page
        Thread.sleep(2000);
        String expectedLogoutUrl = "https://magento.softwaretestingboard.com/customer/account/logoutSuccess/";
        String actualLogoutUrl = driver.getCurrentUrl();
        if (!actualLogoutUrl.equals(expectedLogoutUrl)) {
            throw new RuntimeException("User was not redirected to logout success page.");
        }
        System.out.println("Step 1 Passed: Redirected to logoutSuccess page.");

        // Wait for auto-refresh to homepage
        Thread.sleep(5000);

        String expectedHomepageUrl = "https://magento.softwaretestingboard.com/";
        String actualHomepageUrl = driver.getCurrentUrl();
        if (!actualHomepageUrl.equals(expectedHomepageUrl)) {
            throw new RuntimeException("User was not redirected to the homepage after logout.");
        }
        System.out.println("Step 2 Passed: Redirected to homepage after logout.");
    }
}
