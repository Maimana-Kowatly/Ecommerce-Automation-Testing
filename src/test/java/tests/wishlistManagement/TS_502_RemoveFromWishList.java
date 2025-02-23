package tests.wishlistManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pom.LoginPage;
import pom.WishlistPage;

public class TS_502_RemoveFromWishList {
    private WebDriver driver;
    private LoginPage loginPage;
    private WishlistPage wishlistPage;
    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        wishlistPage = new WishlistPage(driver);
    }
    @Test(priority = 1,dependsOnMethods = "addFromProductPageWithDetails")
    public void WL009_removeItemFromWishlist() throws InterruptedException {
        if(loginPage.isUserLoggedIn()){
            loginPage.logout();
        }
        Thread.sleep(5000);
        loginPage.open();
        loginPage.login("mktest2025@test.com", "2025Testing");
        // Step 1: Navigate to Wishlist Page
//    Thread.sleep(6000);
        driver.get("https://magento.softwaretestingboard.com/wishlist");
        // Step 2: Remove the first item from the wishlist
        boolean isRemoved = wishlistPage.removeFirstItemFromWishlist();
//    Thread.sleep(6000);
        Assert.assertTrue(isRemoved, "item is removed");
        // Step 4: Print confirmation
        System.out.println("Test Passed: Item successfully removed from wishlist.");
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
