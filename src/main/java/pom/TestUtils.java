package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestUtils {
    WebDriver driver;
    public TestUtils(WebDriver driver) {
        this.driver=driver;
    }

    public Boolean doesElementExist(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        }
        catch (Exception e){
            System.out.println("Element not found "+ locator);
            return false;
        }

    }
    public void switchWindow(){
        System.out.println("all window handles: "+driver.getWindowHandles());
        System.out.println("all window handles: "+driver.getWindowHandle());
        driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
    }
    public void switchBack() {
        driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
    }
}
