package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pom.HomePage;
import utils.CartHelper;
public class NavigateToCartTest extends BaseTest {

    @Test(priority = 3, description = "TC-7.1 Navigate to cart from cart icon")
    public void testNavigateFromCartIcon() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        Thread.sleep(3000);

        HomePage homePage = new HomePage(driver);
        homePage.clickCartIcon();
        driver.findElement(By.cssSelector("a.action.viewcart")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/cart/");
    }

    @Test(priority = 3, description = "TC-7.2 Navigate to cart from success message")
    public void testNavigateFromSuccessMessage() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        driver.findElement(By.linkText("shopping cart")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/cart/");
    }
}
