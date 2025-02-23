package tests.wishlistManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pom.LoginPage;
import pom.WishlistPage;

public class TS_505_EditWishlist {
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
    @Test (priority = 1,description = "WL_014 ensure the current data is selected when opening the edit (bordered size,color)",dependsOnMethods = "addFromProductPageWithDetails")
    public void editItemInWishList() throws InterruptedException {
        if (loginPage.isUserLoggedIn()) {
            loginPage.logout();
        }
        Thread.sleep(5000);
        loginPage.open();
        loginPage.login("mktest2025@test.com", "2025Testing");
        // Step 1: Navigate to Wishlist Page
        driver.get("https://magento.softwaretestingboard.com/wishlist");
        // Step 2: Edit the first item in the wishlist
        boolean isBordered = wishlistPage.EditFirstItemFromWishlist();
        // Step 3: Validate if the previous selection is retained
        Assert.assertTrue(isBordered, "❌ Test Failed: Previously selected options are NOT highlighted!");
        // Step 4: Print confirmation
        System.out.println("✅ Test Passed: Wishlist item retains previous selection.");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
