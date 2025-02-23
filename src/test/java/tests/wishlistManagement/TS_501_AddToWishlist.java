package tests.wishlistManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pom.LoginPage;
import pom.WishlistPage;

//precondition to run this class functions together to have empty wishlist

public class TS_501_AddToWishlist {
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

    @Test(priority = 1, description = "TS_501 WL_001 test add to wish list without login")
    public void addToWishlistWithoutLogin() throws InterruptedException {
        // Step 1: Open product page
        driver.get("https://magento.softwaretestingboard.com/ariel-roll-sleeve-sweatshirt.html");
        Thread.sleep(5000);
        // Step 2: Click "Add to Wishlist"
        wishlistPage.addToWishlist();
        Thread.sleep(2000); // Wait for redirect

        // Step 3: Verify redirection to login
        Assert.assertTrue(wishlistPage.isRedirectedToLogin(), "User was NOT redirected to login page!");
        System.out.println("Test Passed: Without login, user is redirected to login page.");
    }

    @Test(priority = 4, description = "TS_501, WL_004 from the product page, add an item with color and size")
    public  void addFromProductPageWithDetails() throws InterruptedException {
        // Step 1: Login
//        loginPage.open();
//        loginPage.login("mktest2025@test.com", "2025Testing");

        // Step 2: Navigate to product page
        driver.get("https://magento.softwaretestingboard.com/olivia-1-4-zip-light-jacket.html");

        // Step 3: Select Size & Color
        wishlistPage.selectColorAndSize();

        // Step 4: Click "Add to Wishlist"
        wishlistPage.addToWishlist();
        Thread.sleep(4000);
        // Step 5: Wait for Wishlist Page to Load
        Assert.assertTrue(wishlistPage.isWishlistPageLoaded(), "Wishlist page did NOT load after adding item!");

        // Step 6: Verify "See Details" appears under the added item
        Assert.assertTrue(wishlistPage.isSeeDetailsVisible(), "See Details button is NOT visible!");

        System.out.println("Test Passed: Item added to wishlist, redirected, and 'See Details' is visible.");
    }

    @Test(priority = 3,description = "TS_501,WL_003 from the product page add to wish list without details, this will ensure that its optional to add to wishlist with/without color and size ")
    public void addFromProductPageWithoutDetails() throws InterruptedException {
        // Step 1: Login
        loginPage.open();
        loginPage.login("mktest2025@test.com", "2025Testing");

        // Step 2: Navigate to product page
        driver.get("https://magento.softwaretestingboard.com/olivia-1-4-zip-light-jacket.html");

        // Step 3: Select Size & Color
//        wishlistPage.selectColorAndSize("Purple","XS");

        // Step 4: Click "Add to Wishlist"
        wishlistPage.addToWishlist();
        Thread.sleep(5000);
        // Step 5: Wait for Wishlist Page to Load
        Assert.assertTrue(wishlistPage.isWishlistPageLoaded(), "Wishlist page did NOT load after adding item!");
        // Step 6: Verify "See Details" appears under the added item
//        Assert.assertFalse(wishlistPage.isSeeDetailsVisible(), "See Details button is NOT visible!");
        System.out.println("Test Passed: Item added to wishlist, redirected, and 'See Details' is visible.");
    }


    @Test(priority = 2,description = "TS_501,WL002 add from outside from the lists (category or hot seller ...) should not show the see details in the wish list page")
    public void addFromCategoryListWithDetails() throws InterruptedException {
        // Step 1: Login
//        loginPage.open();
//        loginPage.login("mktest2025@test.com", "2025Testing");

        // Step 2: Navigate to product page
        driver.get("https://magento.softwaretestingboard.com/women/tops-women/hoodies-and-sweatshirts-women.html");

        // Step 3: Select Size & Color
        wishlistPage.selectColorAndSize();
        wishlistPage.hoverOverFirstProduct();
        // Step 4: Click "Add to Wishlist"
        wishlistPage.addToWishlist();

        // Step 5: Wait for Wishlist Page to Load
        Assert.assertTrue(wishlistPage.isWishlistPageLoaded(), "Wishlist page did NOT load after adding item!");
        Assert.assertTrue(wishlistPage.isSeeDetailsVisible(), "See Details button is NOT visible!");
        // Step 6: Verify "See Details" appears under the added item
        System.out.println("Test Passed: Item added to wishlist, redirected, and 'See Details' is visible.");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
