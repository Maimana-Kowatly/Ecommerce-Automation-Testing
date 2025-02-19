package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import utils.CartHelper;

public class CouponValidationTest extends BaseTest {
    private final String ERROR_COLOR = "#fae5e5";
    @Test(priority = 1, description = "TC-5.1 Enter Valid Coupon")
    public void testValidCoupon() {
        throw new SkipException("Skipping the valid coupon test as Cause there is no available codes.");
    }

    @Test(priority = 2, description = "TC-5.2 Enter Invalid Coupon")
    public void testInvalidCoupon() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        driver.findElement(By.linkText("shopping cart")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickApplyDiscountButton();
        Assert.assertTrue(driver.findElement(By.id("coupon_code")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("button[value=\"Apply Discount\"]")).isDisplayed());
        cartPage.enterCouponCode("kjhgvfc");
        cartPage.clickApplyCoupon();

        SoftAssert softAssert = new SoftAssert();
        WebElement messageElement = driver.findElement(By.cssSelector(".message-error.error"));
        String errorMessage = messageElement.findElement(By.tagName("div")).getText();
        softAssert.assertTrue(errorMessage.contains("The coupon code \"kjhgvfc\" is not valid") );
        softAssert.assertEquals(Color.fromString(messageElement.getCssValue("background-color")).asHex(), ERROR_COLOR, "Error message background color is not red.");
        softAssert.assertAll();
    }
}

