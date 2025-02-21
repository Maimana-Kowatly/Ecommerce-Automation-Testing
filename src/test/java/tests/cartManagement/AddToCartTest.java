package tests.cartManagement;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pom.CartPage;


public class AddToCartTest extends BaseTest {
    private static final String WARNING_MESSAGE_OPTIONS = "You need to choose options for your item.";
    private static final String WARNING_MESSAGE_QUANTITY = "Please specify the quantity of product(s).";
    private static final String SUCCESS_MESSAGE_PREFIX = "You added ";
    private static final String SUCCESS_MESSAGE_SUFFIX = " to your";
    private static final String SUCCESS_COLOR = "#e5efe5";
    private static final String WARNING_COLOR = "#fdf0d5";


    @Test(priority = 1, description = "TC-1.1 ADD Item to the Cart From Home Page with valid inputs")
    public void testAddItemFromHomePageValidInputs() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();
        WebElement product = homePage.getProductByNameHome("Hero Hoodie");
        homePage.selectProductColor(product, "Green");
        homePage.selectProductSize(product, "XL");
        homePage.addProductToCart(product);
        verifyAddProductToCart(1, "Hero Hoodie","XL",54,"Green");

    }

    @Test(priority = 1,  dataProvider = "productsWithOptions",description = "TC-1.2 ADD Item to the Cart From Category Page with valid inputs")
    public void testAddItemFromCategoryPageValidInputs(String category, String subCat,String productName,String productSize, String productColor,float price ) {
        HomePage homePage = new HomePage(driver);
        homePage.goToCategoryFromPages(category, subCat);
        WebElement product = homePage.getProductByName(productName);
        homePage.selectProductColor(product, productColor);
        homePage.selectProductSize(product, productSize);
        homePage.addProductToCart(product);
        verifyAddProductToCart(1, productName,productSize,price,productColor);
    }

    @Test(priority = 1,dataProvider = "products", description = "TC-1.3 ADD Item to the Cart From Product Details Page with valid inputs")
    public void testAddItemFromProductPageValidInputs(String category , String subCategory, String productName, float price,boolean hasQuantity, String quantity, String link)  {
        HomePage homePage = new HomePage(driver);
        homePage.goToCategoryFromNavBar(category, subCategory);
        WebElement product = homePage.getProductByName(productName);
        product.click();
        ProductPage productPage = new ProductPage(driver);

        if(hasQuantity)
            productPage.setQuantity(quantity);

        productPage.addToCart();
        verifyAddProductToCartFromProductPage(productPage, quantity,link);
    }

    @Test(priority = 2, description = "TC-1.4 ADD Item to the Cart From Home Page with invalid inputs")
    public void testAddItemFromHomePageInValidInputs()  {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();
        WebElement product = homePage.getProductByNameHome("Hero Hoodie");
        homePage.hoverOverProduct(product);
        homePage.addProductToCart(product);

        verifyAddProductToCartWithInvalidInputs();

    }

    @Test(priority = 2, description = "TC-1.5 ADD Item to the Cart From Category Page with invalid inputs")
    public void testAddItemFromCategoryPageInValidInputs() {
        HomePage homePage = new HomePage(driver);
        homePage.goToCategoryFromPages("ui-id-4", "Jackets");
        WebElement product = homePage.getProductByName("Juno Jacket");
        homePage.hoverOverProduct(product);
        homePage.addProductToCart(product);

        verifyAddProductToCartWithInvalidInputs();
    }

    @Test(priority = 2,description = "TC-1.6 ADD Item to the Cart From Product Details Page with invalid inputs")
    public void testAddItemFromProductPageInValidInputs() {
        HomePage homePage = new HomePage(driver);
        homePage.goToCategoryFromNavBar("a#ui-id-6", "a#ui-id-26");
        WebElement product = homePage.getProductByName("Set of Sprite Yoga Straps");
        product.click();
        Assert.assertEquals(driver.getCurrentUrl(), BaseUrl + "set-of-sprite-yoga-straps.html", "Didn't redirected to the product page");
        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();
        verifyAddProductToCartFromDetailsWithInvalidInputs();

    }

    private void verifyAddProductToCart(int expectedQuantity, String productName, String productSize, float price, String productColor) {
        WebElement messageElement = driver.findElement(By.cssSelector(".message-success.success.message div"));
        String successMessage = messageElement.getText();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText(), String.valueOf(expectedQuantity), "Cart Icon count Doesn't match Expected");
        softAssert.assertEquals(Color.fromString(driver.findElement(By.cssSelector(".message-success")).getCssValue("background-color")).asHex(), SUCCESS_COLOR);
        softAssert.assertTrue(successMessage.contains(SUCCESS_MESSAGE_PREFIX + productName + SUCCESS_MESSAGE_SUFFIX), "Success Message Didn't appear as Expected");

        CartPage cartPage = new CartPage(driver);
        driver.findElement(By.linkText("shopping cart")).click();
        float subTotal =expectedQuantity * price;
        softAssert.assertEquals(cartPage.getItemColor(productName),productColor);
        softAssert.assertEquals(cartPage.getItemSize(productName),productSize);
        softAssert.assertEquals(cartPage.getItemPrice(productName),"$"+String.format("%.2f",price));
        softAssert.assertEquals(cartPage.getItemSubTotal(productName),"$"+String.format("%.2f",subTotal));
        softAssert.assertTrue(cartPage.getItemQuantity(productName).contains(String.valueOf(expectedQuantity)),"Quantity do not match");
        softAssert.assertAll();
    }

    private void verifyAddProductToCartFromProductPage(ProductPage productPage, String expectedQuantity, String link) {
        Assert.assertEquals(driver.getCurrentUrl(), link, "Didn't redirected to the product page");
        SoftAssert softAssert = new SoftAssert();
        String productName = productPage.getProductName();
        softAssert.assertEquals(productPage.getSuccessMessageColor(), SUCCESS_COLOR);
        softAssert.assertTrue(productPage.getSuccessMessageText().contains(SUCCESS_MESSAGE_PREFIX + productName + SUCCESS_MESSAGE_SUFFIX), "Success Message Didn't appear as Expected");
        softAssert.assertEquals(productPage.getCartIconCount(), expectedQuantity, "Cart Icon count Doesn't match Expected");
        softAssert.assertAll();
    }

    private void verifyAddProductToCartWithInvalidInputs() {
        SoftAssert softAssert = new SoftAssert();
        boolean isCartEmpty = driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().contains("0") || driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText().isEmpty();
        softAssert.assertTrue(isCartEmpty, "Cart isn't empty");
        WebElement messageElement = driver.findElement(By.cssSelector(".message-notice.notice.message"));
        String warningMessage = messageElement.findElement(By.tagName("div")).getText();
        softAssert.assertEquals(warningMessage, WARNING_MESSAGE_OPTIONS);
        softAssert.assertEquals(Color.fromString(messageElement.getCssValue("background-color")).asHex(), WARNING_COLOR);
        softAssert.assertAll();
    }

    private void verifyAddProductToCartFromDetailsWithInvalidInputs() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).isDisplayed(), "Cart isn't empty");
        WebElement messageElement = driver.findElement(By.id("validation-message-box"));
        String warningMessage = messageElement.findElement(By.tagName("div")).getText();
        softAssert.assertEquals(warningMessage, WARNING_MESSAGE_QUANTITY);
        softAssert.assertAll();
    }
    @DataProvider(name = "productsWithOptions")
    public Object[][] productsWithOptionsData() {
        return new Object[][]{
                {"ui-id-4" , "Jackets","Olivia 1/4 Zip Light Jacket","S", "Purple",77},
                {"ui-id-4" , "Jackets","Neve Studio Dance Jacket","S", "Orange" ,69},
                {"ui-id-4" , "Jackets","Jade Yoga Jacket","L", "Blue" ,32},
                {"ui-id-5" , "Tees","Ryker LumaTech™ Tee (V-neck)","M", "Gray" ,28},

        };
    }

    @DataProvider(name = "products")
    public Object[][] productData() {
        return new Object[][]{
                {"a#ui-id-6", "a#ui-id-25", "Push It Messenger Bag", 45, false,"1",BaseUrl+"push-it-messenger-bag.html"},
                {"a#ui-id-6", "a#ui-id-26","Set of Sprite Yoga Straps",17,true ,"3",BaseUrl + "set-of-sprite-yoga-straps.html"}

        };
    }
}
