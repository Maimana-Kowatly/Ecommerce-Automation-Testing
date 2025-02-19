package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import utils.CartHelper;

public class RemoveFromCartTest extends BaseTest {


    @Test(priority = 4, description = "TC-3.1 Remove Item from Cart", dataProvider = "productOptions")
    public void testRemoveItemFromCart(String sizeOption, String colorOption, String productName, double price) {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addItemToCart("ui-id-4", "Jackets", sizeOption, colorOption, productName,price);

        // Go to Cart Page and remove item
        driver.findElement(By.linkText("shopping cart")).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem(0);

        // Validate cart is empty
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty after removal.");
        softAssert.assertTrue(cartPage.getEmptyCartMessage().contains("You have no items in your shopping cart."));
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "TC-3.2 Remove All Items from Cart")
    public void testRemoveAllItemsFromCart() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "S", "Purple", "Olivia 1/4 Zip Light Jacket",77.00);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "XS", "Orange", "Neve Studio Dance Jacket",69.00);

        // Go to Cart Page and remove item
        driver.findElement(By.linkText("shopping cart")).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.removeAllItems(2);

        // Validate cart is empty
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty after removal.");
        softAssert.assertTrue(cartPage.getEmptyCartMessage().contains("You have no items in your shopping cart."));
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "TC-3.1 Remove Item from Cart")
    public void testRemoveItemFromCartFromMultipleItems() {
        CartHelper cartHelper = new CartHelper(driver);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "S", "Purple", "Olivia 1/4 Zip Light Jacket",77.00);
        cartHelper.addItemToCart("ui-id-4", "Jackets", "XS", "Orange", "Neve Studio Dance Jacket",69.00);

        // Go to Cart Page and remove item
        driver.findElement(By.linkText("shopping cart")).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem(0);

        // Validate cart is empty
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cartPage.getCartItems().size() == 1, "Cart Doesnt have one item.");
        softAssert.assertAll();
    }

    // Data Provider for different product options
    @DataProvider(name = "productOptions")
    public Object[][] productData() {
        return new Object[][]{
                {"S", "Purple", "Olivia 1/4 Zip Light Jacket",77.00},
                {"XS", "Orange", "Neve Studio Dance Jacket",69.00}
        };
    }
}
