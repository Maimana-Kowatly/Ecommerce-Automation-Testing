package tests.wishlistManagement;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pom.LoginPage;
import pom.WishlistPage;

public class TS_503_EmptyWishlist {
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

    @BeforeMethod
    public void loginAndNavigateToWishlist() throws InterruptedException {
        if (loginPage.isUserLoggedIn()) {
            loginPage.logout();
            Thread.sleep(500); // Wait for logout to complete
        }
        loginPage.open();
        loginPage.login("mktest2025@test.com", "2025Testing");

        // Navigate to Wishlist page
        driver.get("https://magento.softwaretestingboard.com/wishlist");
    }


@Test (priority = 2,description = "",dependsOnMethods = "WL009_removeItemFromWishlist")
    public void emptyTheWishlist() {
    driver.navigate().refresh();
        if (!wishlistPage.isWishlistPageLoaded()) {
            System.out.println("Wishlist page failed to load.");
            return;
        }
    driver.get("https://magento.softwaretestingboard.com/wishlist");
        // Step 1: Verify the wishlist contains at least one item
        if (wishlistPage.getWishlistItemCount() == 0) {
            System.out.println("Wishlist is already empty, cannot remove last item.");
            return;
        }
        System.out.println("Wishlist contains items, proceeding with removal.");
        // Step 2: Remove the last item
        wishlistPage.removeFirstItemFromWishlist();
        wishlistPage.removeFirstItemFromWishlist();
        if (wishlistPage.isWishlistEmptyMessageDisplayed()) {
            System.out.println("Warning message displayed: 'You have no items in your wish list.'");
        } else {
            System.out.println(" Expected warning message NOT displayed.");
        }
        if (wishlistPage.getWishlistItemCount() == 0) {
            System.out.println("Wishlist is now empty.");
        } else {
            System.out.println("Wishlist still contains items after removal.");
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
