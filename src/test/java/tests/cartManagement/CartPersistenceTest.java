package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.CartHelper;
import utils.CookieHelper;

import java.io.IOException;
import java.time.Duration;

public class CartPersistenceTest extends BaseTest {
    private static final String COOKIE_FILE = "cookies.data";


    @Test(priority = 2, description = "TC-4.1 Verify cart persistence for a guest user")
    public void testGuestUserCartPersistence() throws IOException, InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CookieHelper.saveCookiesToFile(driver, COOKIE_FILE);
        driver.quit();
        System.out.println("First session closed.");

        WebDriver newDriver = new ChromeDriver();
        newDriver.get("https://magento.softwaretestingboard.com/what-is-new.html");
        CookieHelper.loadCookiesFromFile(newDriver, COOKIE_FILE);
        newDriver.navigate().refresh();

        System.out.println("Reopened browser with saved session.");
        newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        Assert.assertEquals(newDriver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText(), "1", "Cart Icon count Doesn't match Expected");
        newDriver.quit();
    }

    @Test(priority = 2, description = "TC-4.2 Verify cart persistence for a logged-in user")
    public void testLoggedInUserCartPersistence() {
        CartHelper cartHelper = new CartHelper(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLogInPage();
        loginPage.login("dalal.haffar.1997@gmail.com", "123456789AA@@");

        cartHelper.addDefaultItemToCart();
        loginPage.logout();
        loginPage.goToLogInPage();
        loginPage.login("dalal.haffar.1997@gmail.com", "123456789AA@@");
        driver.get(BaseUrl + "what-is-new.html");
        boolean isCartCounterEmpty = driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().isEmpty() || driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().contains("0");
        Assert.assertFalse(isCartCounterEmpty, "Cart Icon count Doesn't match Expected");

    }

    @Test(priority = 2, description = "TC-4.3 Sync Cart from Guest to Logged-in")
    public void testGuestToLoggedInCartSync() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLogInPage();
        loginPage.login("dalal.haffar.1997@gmail.com", "123456789AA@@");
        driver.get(BaseUrl + "what-is-new.html");
        boolean isCartCounterEmpty = driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().isEmpty() || driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().contains("0");
        Assert.assertFalse(isCartCounterEmpty, "Cart Icon count Doesn't match Expected");

    }
}