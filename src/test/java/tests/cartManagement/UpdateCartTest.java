package tests.cartManagement;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.ProductPage;
import utils.CartHelper;

public class UpdateCartTest extends BaseTest {

    @Test(priority = 1, description = "CM-007 Increase One Item quantity with valid amount from Cart Page")
    public void testIncreaseItemQuantityValid() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
        driver.findElement(By.linkText("shopping cart")).click();
        cartPage.updateQuantity("Olivia 1/4 Zip Light Jacket", 2);
        cartPage.clickUpdateCart();
        Thread.sleep(3000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getItemSubTotal("Olivia 1/4 Zip Light Jacket"), "$154.00", "Subtotal incorrect");
        softAssert.assertEquals(cartPage.getCartTotal(), "$154.00", "Total price incorrect");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "CM-008 Decrease One Item quantity with valid amount from Cart Page")
    public void testDecreaseItemQuantityValid() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
        driver.findElement(By.linkText("shopping cart")).click();
        cartPage.updateQuantity("Olivia 1/4 Zip Light Jacket", 1);
        cartPage.clickUpdateCart();
        Thread.sleep(3000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getItemSubTotal("Olivia 1/4 Zip Light Jacket"), "$77.00", "Subtotal incorrect");
        softAssert.assertEquals(cartPage.getCartTotal(), "$77.00", "Total price incorrect");
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "CM-009 Increase One Item quantity with invalid amount from Cart Page")
    public void testIncreaseItemQuantityInvalid() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
               driver.findElement(By.linkText("shopping cart")).click();

        cartPage.updateQuantity("Olivia 1/4 Zip Light Jacket", 10000000);
        cartPage.clickUpdateCart();
        Thread.sleep(3000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.xpath("//aside[@class=\"modal-popup confirm                                 _show\"]//div[@data-role=\"content\"]")).getText().contains("The requested qty exceeds the maximum qty allowed in shopping cart"));
        softAssert.assertTrue(driver.findElement(By.xpath("//aside[@class=\"modal-popup confirm                                 _show\"]//div[@data-role=\"content\"]")).isDisplayed());
        softAssert.assertEquals(cartPage.getCartTotal(), "$77.00", "Total price incorrect");
        softAssert.assertAll();

    }

    @Test(priority = 4, description = "CM-010 Decrease One Item quantity with invalid amount from Cart Page (-1)")
    public void testDecreaseItemQuantityInvalidNegative() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
               driver.findElement(By.linkText("shopping cart")).click();

        cartPage.updateQuantity("Olivia 1/4 Zip Light Jacket", -1);
        cartPage.clickUpdateCart();

        Thread.sleep(3000);

        SoftAssert softAssert = new SoftAssert();
        WebElement cartQuantityErrorElement = cartPage.getQuantityErrorElement("Olivia 1/4 Zip Light Jacket");
        softAssert.assertTrue(cartQuantityErrorElement.isDisplayed(), "No error message displayed");
        softAssert.assertEquals(Color.fromString(cartQuantityErrorElement.getCssValue("color")).asHex(), "#e02b27", "Error message color is not red");
        softAssert.assertEquals(cartQuantityErrorElement.getText(), "Please enter a number greater than 0 in this field.", "Incorrect error message");
        softAssert.assertEquals(cartPage.getCartTotal(), "$77.00", "Total price incorrect");
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "CM-011 Decrease One Item quantity with invalid amount from Cart Page (0)")
    public void testDecreaseItemQuantityInvalidZero() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
               driver.findElement(By.linkText("shopping cart")).click();

        cartPage.updateQuantity("Olivia 1/4 Zip Light Jacket", 0);
        cartPage.clickUpdateCart();
        Thread.sleep(3000);

        SoftAssert softAssert = new SoftAssert();
        WebElement cartQuantityErrorElement = cartPage.getQuantityErrorElement("Olivia 1/4 Zip Light Jacket");
        softAssert.assertTrue(cartQuantityErrorElement.isDisplayed(), "No error message displayed");
        softAssert.assertEquals(Color.fromString(cartQuantityErrorElement.getCssValue("color")).asHex(), "#e02b27", "Error message color is not red");
        softAssert.assertEquals(cartQuantityErrorElement.getText(), "Please enter a number greater than 0 in this field.", "Incorrect error message");
        softAssert.assertEquals(cartPage.getCartTotal(), "$77.00", "Total price incorrect");
        softAssert.assertAll();
    }

    @Test(priority = 6, description = "CM-012 Update Item specifications")
    public void testUpdateItemSpecifications() throws InterruptedException {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addDefaultItemToCart();
        CartPage cartPage = new CartPage(driver);
        driver.findElement(By.linkText("shopping cart")).click();
        cartPage.clickEditItem("Olivia 1/4 Zip Light Jacket");
        ProductPage productPage = new ProductPage(driver);
        productPage.selectProductSize( "M");
        productPage.selectProductColor( "Black");
        productPage.clickUpdateCarte();
        cartPage.clickUpdateCart();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getItemColor("Olivia 1/4 Zip Light Jacket"),"Black");
        softAssert.assertEquals(cartPage.getItemSize("Olivia 1/4 Zip Light Jacket"),"M");
        softAssert.assertEquals(cartPage.getItemPrice("Olivia 1/4 Zip Light Jacket"),"$77.00");
        softAssert.assertEquals(cartPage.getItemSubTotal("Olivia 1/4 Zip Light Jacket"),"$77.00");
        softAssert.assertEquals(cartPage.getItemQuantity("Olivia 1/4 Zip Light Jacket"),"1");
        softAssert.assertEquals(cartPage.getCartTotal(), "$77.00", "Total price incorrect");
        softAssert.assertEquals(cartPage.getSuccessMessageColor(),"#e5efe5", "Message is not in green");
        softAssert.assertEquals(cartPage.getSuccessMessageText(), "Olivia 1/4 Zip Light Jacket was updated in your shopping cart.", "Incorrect success message");
        softAssert.assertAll();
    }
}
