package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.LoginPage;
import utils.CartHelper;

public class MoveToWishListTest extends BaseTest {
    private final String SUCCESS_MESSAGE ="has been moved to your wish list.";
    private final String SUCCESS_COLOR = "#e5efe5";
    @Test(priority = 1, description = "TC-8.1 Move item from cart to wishlist as Logged-in user")
    public void testMoveToWishlistAsLoggedInUser() {
        CartHelper cartHelper = new CartHelper(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLogInPage();
        loginPage.login("dalal.haffar.1997@gmail.com", "123456789AA@@");

        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
        driver.findElement(By.linkText("shopping cart")).click();
        cartPage.clickMoveToWishlist(0);

        SoftAssert softAssert = new SoftAssert();
        WebElement messageElement = driver.findElement(By.cssSelector(".success.message"));
        String successMessage = messageElement.findElement(By.tagName("div")).getText();
        softAssert.assertTrue(successMessage.contains(SUCCESS_MESSAGE) );
        softAssert.assertEquals(Color.fromString(messageElement.getCssValue("background-color")).asHex(), SUCCESS_COLOR);
        driver.findElement(By.cssSelector(".customer-welcome button[data-action='customer-menu-toggle']")).click();
        softAssert.assertFalse(driver.findElement(By.cssSelector("li.wishlist a span")).getText().isEmpty(),"Item was not added to the wishlist.");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "TC-8.2 Move to wishlist link should not appear for Guest user")
    public void testMoveToWishlistAsGuestUser() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "S", "Purple", "Olivia 1/4 Zip Light Jacket",77.00);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "XS", "Orange", "Neve Studio Dance Jacket",69.00);
        driver.findElement(By.linkText("shopping cart")).click();

        Assert.assertTrue(driver.findElements(By.cssSelector(".action-towishlist")).isEmpty());
    }
}
