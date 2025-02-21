package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

public class ProductPage {
    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCart() {
        driver.findElement(By.id("product-addtocart-button")).click();
    }

    public String getSuccessMessageText() {
        return driver.findElement(By.cssSelector(".message-success div")).getText();
    }

    public String getSuccessMessageColor() {
        return Color.fromString(driver.findElement(By.cssSelector(".message-success")).getCssValue("background-color")).asHex();
    }

    public void setQuantity(String quantity) {
        WebElement quantityInput = driver.findElement(By.cssSelector("input[name='super_group[34]']"));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    public String getProductName() {
        return driver.findElement(By.cssSelector("h1.page-title span")).getText();
    }

    public String getCartIconCount() {
        return driver.findElement(By.cssSelector(".minicart-wrapper .counter-number")).getText();
    }

    public void clickUpdateCarte(){
        driver.findElement(By.id("product-updatecart-button")).click();
    }
    public void selectProductSize( String value){
        driver.findElement(By.cssSelector("div[option-tooltip-value=\""+value.toUpperCase()+"\"]")).click();
    }
    public void selectProductColor(String value){
        driver.findElement(By.cssSelector("div[option-label=\""+value+"\"]")).click();

    }
}