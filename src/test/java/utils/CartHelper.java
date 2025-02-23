package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.HomePage;

public class CartHelper {
    private WebDriver driver;

    public CartHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void addItemToCart(String categoryId, String subCategory, String size, String color, String productName, double price) {
        HomePage homePage = new HomePage(driver);
        homePage.goToCategoryFromPages(categoryId, subCategory);

        WebElement product = homePage.getProductByName(productName);
        homePage.selectProductColor(product, color);
        homePage.selectProductSize(product, size);
        homePage.addProductToCart(product);
    }

    public void addDefaultItemToCart() {
        addItemToCart("ui-id-4", "Jackets", "S", "Blue", "Olivia 1/4 Zip Light Jacket",77.00);
    }
}
