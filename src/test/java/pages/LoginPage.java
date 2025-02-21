package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLogInPage() {
        driver.findElement(By.linkText("Sign In")).click();
    }

    public void login(String email, String password) {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("send2")).click();
    }

    public void logout() {
        driver.findElement(By.cssSelector("button.action.switch[data-action='customer-menu-toggle']")).click();
        driver.findElement(By.linkText("Sign Out")).click();
    }
}
