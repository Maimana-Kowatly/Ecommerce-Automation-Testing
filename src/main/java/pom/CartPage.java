package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void updateQuantity(String productName, int quantity) {
        WebElement qtyInput = driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tr[@class=\"item-info\"]/td[@data-th=\"Qty\"]//input"));
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(quantity));
    }
    public WebElement getQuantityErrorElement(String productName) {
        return driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tr[@class=\"item-info\"]/td[@data-th=\"Qty\"]//div[@class=\"mage-error\"]"));
    }

    public void removeItem(int index) {
        driver.findElements(By.cssSelector(".cart.item")).get(index).findElement(By.cssSelector(".action.action-delete")).click();
    }

    public void clickApplyDiscountButton(){
         driver.findElement(By.cssSelector(".block.discount .title[data-role='title']")).click();
    }

    public void enterCouponCode(String copoun){
        driver.findElement(By.id("coupon_code")).sendKeys(copoun);
    }
    public void  clickApplyCoupon(){
        driver.findElement(By.cssSelector("button[value=\"Apply Discount\"]")).click();
    }
    public void clickUpdateCart(){
        driver.findElement(By.cssSelector("button[title=\"Update Shopping Cart\"]")).click();
    }
    public String getCartTotal() {
        return driver.findElement(By.xpath("//td[@data-th=\"Order Total\"]//span")).getText();
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(By.cssSelector(".cart.item"));
    }

    public boolean isCartEmpty() {
        return driver.findElements(By.cssSelector(".cart.item")).isEmpty();
    }

    public String getEmptyCartMessage() {
        return driver.findElement(By.cssSelector(".cart-empty p")).getText();
    }
    public void clickMoveToWishlist(int index){
        getCartItems().get(index).findElement(By.cssSelector(".action-towishlist")).click();
    }
    public void clickEditItem(String productName){
        driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tbody[@class=\"cart item\"]//a[@title=\"Edit item parameters\"]")).click();
    }
    public void removeAllItems(int count) {
        for (int i = 0; i < count; i++) {
            removeItem(0);
        }
    }
    public String getSuccessMessageText() {
        return driver.findElement(By.cssSelector(".message-success div")).getText();
    }

    public String getSuccessMessageColor() {
        return Color.fromString(driver.findElement(By.cssSelector(".message-success")).getCssValue("background-color")).asHex();
    }
    public String getItemSize(String productName) {
        return driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tbody[@class=\"cart item\"]//dl/dt[contains(text(),'Size')]/following-sibling::dd")).getText().trim();
    }

    public String getItemColor(String productName) {
        return driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tbody[@class=\"cart item\"]//dl/dt[contains(text(),'Color')]/following-sibling::dd")).getText().trim();
    }
    public String getItemSubTotal(String productName) {
        return driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tr[@class=\"item-info\"]/td[@data-th=\"Subtotal\"]//span[@class=\"price\"]")).getText();
    }
    public String getItemPrice(String productName) {
        return driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tr[@class=\"item-info\"]/td[@data-th=\"Price\"]//span[@class=\"price\"]")).getText();
    }
    public String getItemQuantity(String productName) {
       WebElement qtyInput = driver.findElement(By.xpath("//tbody[@class='cart item']//strong[@class='product-item-name']/a[contains(text(), '"+productName+"')]/ancestor::tr[@class=\"item-info\"]/td[@data-th=\"Qty\"]//input"));
        return qtyInput.getAttribute("value");
    }

}