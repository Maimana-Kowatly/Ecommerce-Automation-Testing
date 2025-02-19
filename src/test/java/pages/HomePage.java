package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class HomePage {
    protected static final String BaseUrl = "https://magento.softwaretestingboard.com/";
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToHomePage() {
        driver.get(BaseUrl);
    }

    public void goToCategoryFromNavBar(String category, String subCategory) {
        // Go to Product page
        Actions actions = new Actions(driver);
        WebElement categoryMenu = driver.findElement(By.cssSelector(category));
        actions.moveToElement(categoryMenu).perform();

        // Click on 'Fitness Equipment'
        WebElement subCategoryMenu = driver.findElement(By.cssSelector(subCategory));
        subCategoryMenu.click();
    }

    public void goToCategoryFromPages(String category, String subCategory) {
        driver.findElement(By.id(category)).click();
        driver.findElement(By.linkText(subCategory)).click();
    }

    public void goToProductDetails(String productName) {
        driver.findElement(By.linkText(productName)).click();
    }

    public List<WebElement> getProducts() {
        return driver.findElements(By.cssSelector("ol.product-items li.product-item"));
    }

    public void addProductToCart(WebElement product) {
        product.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    public void selectProductOption(WebElement product, String optionId) {
        product.findElement(By.id(optionId)).click();
    }

    public void hoverOverProduct(WebElement product) {
        Actions actions = new Actions(driver);
        actions.moveToElement(product).perform();
    }

    public void clickCartIcon(){
        driver.findElement(By.cssSelector(".minicart-wrapper a")).click();
    }
    public WebElement getProductByNameHome(String productName){
       return driver.findElement(By.xpath("//strong/a[contains(text(), '"+productName+"')]/ancestor::div[@class=\"product-item-details\"]"));
    }
    public WebElement getProductByName(String productName){
        return driver.findElement(By.xpath("//strong/a[contains(text(), '"+productName+"')]/ancestor::div[@class=\"product-item-info\"]"));
    }
    public void selectProductSize(WebElement product, String value){
        product.findElement(By.cssSelector("div[option-tooltip-value=\""+value.toUpperCase()+"\"]")).click();
    }
    public void selectProductColor(WebElement product,String value){
        product.findElement(By.cssSelector("div[option-label=\""+value+"\"]")).click();
    }

}