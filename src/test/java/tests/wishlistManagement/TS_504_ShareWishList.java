package tests.wishlistManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pom.LoginPage;
import pom.WishlistPage;

public class TS_504_ShareWishList {
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
            Thread.sleep(5000); // Wait for logout to complete
        }
        loginPage.open();
        loginPage.login("mktest2025@test.com", "2025Testing");

        // Navigate to Wishlist page
        driver.get("https://magento.softwaretestingboard.com/wishlist");
    }
    @Test
    public void shareWishListWithValidEmails() {
        shareWishList("m.kowatli97@gmail.com",true);
    }
    @Test
    public void shareWishListWithInvalidEmail() {
        shareWishList("test1@te",false);
        // ✅ Verify that the error message appears
        if (wishlistPage.isInvalidEmailErrorDisplayed()) {
            System.out.println("Invalid email error message displayed correctly.");
        } else {
            System.out.println("Error message NOT displayed for invalid email.");
        }
    }
    private void shareWishList(String email, boolean validEmail) {
        if (wishlistPage.isWishlistPageLoaded()) {
            wishlistPage.clickShareWishlist();
            wishlistPage.enterEmails(email);
            wishlistPage.clickSend();
            boolean isSuccess = false;
            boolean isErrorDisplayed = false;
            if (validEmail) {
                // Wait for success message ONLY if a valid email was used
                isSuccess = wishlistPage.isWishlistSharedSuccessfully();
            } else {
                // Wait for error message ONLY if an invalid email was used
                isErrorDisplayed = wishlistPage.isInvalidEmailErrorDisplayed();
            }
            //  Debugging Output
            System.out.println(" Debug: isSuccess = " + isSuccess);
            System.out.println(" Debug: isErrorDisplayed = " + isErrorDisplayed);
            if (validEmail) {
                if (isSuccess) {
                    System.out.println("Wishlist shared successfully!");
                } else {
                    System.out.println("Wishlist sharing failed when it should have succeeded!");
                }
            } else {
                if (isErrorDisplayed) {
                    System.out.println("Invalid email error message displayed correctly.");
                } else {
                    System.out.println("Error message NOT displayed for invalid email.");
                }
                if (isSuccess) {
                    System.out.println("Wishlist was shared when it should NOT have been!");
                }
            }
        } else {
            System.out.println("Failed to load wishlist page.");
        }
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
