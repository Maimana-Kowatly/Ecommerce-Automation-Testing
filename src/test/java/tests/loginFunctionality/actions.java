package tests.loginFunctionality;

import org.testng.annotations.Test;
import pom.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pom.TestUtils;
public class actions {
    @Test
    // correct credentials scenario
    //*** in this test case: (testWrongCredentials)
    // -The test will pass if the success message is displayed when correct credentials are entered and when the expected url redirection match the actual url redirection
    // -The test will fail if the success message is not displayed when credentials are entered (even if the credentials are correct)
    //or if the expected url redirection is different than the actual url redirection
    public void testCorrectCredentials() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login
        loginPage.login("mktest2025@test.com", "2025Testing");
        // check login message
        By loginMessage = By.cssSelector("li.greet.welcome");
        TestUtils testUtils = new TestUtils(driver);
        boolean isExist = testUtils.doesElementExist(loginMessage);
        if (!isExist) {
            Assert.fail("element was not found");
        }
        else{
            System.out.println("Flash Message: "+driver.findElement(loginMessage).getText());
        }
        //check redirect url
        String expectedUrl = "https://magento.softwaretestingboard.com/customer/account/";
        System.out.println("ExpectedUrl: "+expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        System.out.println("ActualUrl: "+actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the correct page.");
        driver.quit();
    }
    @Test
    // wrong username or password testcase scenario
    //*** in this test case: (testWrongCredentials)
    // -The test will pass if the error message is displayed when wrong credentials are entered and when the expected url redirection match the actual url redirection
    // -The test will fail if the error message is not displayed when credentials are entered (even if the credentials are wrong)
    // or if the expected url redirection is different than the actual url redirection

    public void testWrongCredentials() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        // Create LoginPage object
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login
        loginPage.login("mktest2025@test.comm", "2025Testing");
        By loginMessage = By.className("message");
        TestUtils testUtils = new TestUtils(driver);
        boolean isExist = testUtils.doesElementExist(loginMessage);
        if (!isExist) {
            Assert.fail("element was not found");
        }
        else{
            System.out.println("Flash Message: "+driver.findElement(loginMessage).getText());
        }
        //check redirect url
        String expectedUrl = "https://magento.softwaretestingboard.com/customer/account/login/";
        System.out.println("ExpectedUrl: "+expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the correct page.");
        driver.quit();
    }
    @Test
    public void LoginWithEmptyFields() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login with empty fields
        loginPage.login("", "");
        // Check for general login error message
        By loginError = By.className("message-error");
        TestUtils testUtils = new TestUtils(driver);
        boolean isLoginErrorVisible = testUtils.doesElementExist(loginError);
        if (!isLoginErrorVisible) {
            Assert.fail("Error message was not displayed for empty fields.");
        } else {
            System.out.println("Error Message: " + driver.findElement(loginError).getText());
        }
        driver.quit();
    }
    @Test
    public void testLogout() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login
        loginPage.login("mktest2025@test.com", "2025Testing");
        // Click on the dropdown menu (if required)
        By customerMenu = By.cssSelector(".customer-name button");
        driver.findElement(customerMenu).click();
        // Click on the Sign Out button
        By logoutButton = By.cssSelector(".customer-menu .authorization-link a");
        driver.findElement(logoutButton).click();

        // Wait for the first redirection to logoutSuccess page
        Thread.sleep(2000);
        String expectedLogoutUrl = "https://magento.softwaretestingboard.com/customer/account/logoutSuccess/";
        String actualLogoutUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualLogoutUrl, expectedLogoutUrl, "User was not redirected to logout success page.");
        System.out.println("Step 1 Passed: Redirected to logoutSuccess page.");
        // Wait for auto-refresh to homepage
        Thread.sleep(5000); // Adjust if needed
        String expectedHomepageUrl = "https://magento.softwaretestingboard.com/";
        String actualHomepageUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualHomepageUrl, expectedHomepageUrl, "User was not redirected to the homepage after logout.");
        System.out.println("Step 2 Passed: Redirected to homepage after logout.");

        driver.quit();
    }
    //email fields should be case insensitive
    @Test
    public void testInsensivity() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login
        loginPage.login("MKTEST2025@test.com", "2025Testing");
        // check login message
        By loginMessage = By.cssSelector("li.greet.welcome");
        TestUtils testUtils = new TestUtils(driver);
        boolean isExist = testUtils.doesElementExist(loginMessage);
        if (!isExist) {
            Assert.fail("element was not found");
        }
        else{
            System.out.println("Flash Message: "+driver.findElement(loginMessage).getText());
        }
        //check redirect url
        String expectedUrl = "https://magento.softwaretestingboard.com/customer/account/";
        System.out.println("ExpectedUrl: "+expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        System.out.println("ActualUrl: "+actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the correct page.");
        driver.quit();
    }
    @Test
    public void testInvalidEmailFormat() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        // Perform login with empty fields
        loginPage.login("test@", "2025Testing");
        // Check for general login error message
        By loginError = By.id("email-error");
        TestUtils testUtils = new TestUtils(driver);
        boolean isLoginErrorVisible = testUtils.doesElementExist(loginError);
        if (!isLoginErrorVisible) {
            Assert.fail("Error message was not displayed for empty fields.");
        } else {
            System.out.println("Error Message: " + driver.findElement(loginError).getText());
        }
        driver.quit();
    }
}
