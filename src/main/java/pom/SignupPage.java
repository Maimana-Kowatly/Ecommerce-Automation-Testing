package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignupPage {
    private WebDriver driver;

    // Selectors
    private By firstNameField = By.id("firstname");
    private By lastNameField = By.id("lastname");
    private By emailField = By.id("email_address");
    private By passwordField = By.id("password");
    private By confirmPasswordField = By.id("password-confirmation");
    private By signupButton = By.cssSelector("button[title='Create an Account']");
    private By invalidEmailError = By.id("email_address-error");
    private By errorMessage = By.cssSelector("div[data-bind='html: $parent.prepareMessageForHtml(message.text)']");
    private By passwordMismatchError = By.id("password-confirmation-error");// Error when passwords do not match
    private By passwordError = By.id("password-error");//weak or short
    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/create/");
    }

    public void signup(String firstName, String lastName, String email, String password, String confirmPassword) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
        driver.findElement(signupButton).click();
    }
    public boolean isSignupSuccessful() {
        try {
            // Check if the success message is displayed
            By successMessage = By.cssSelector("div.message-success");
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false; // Element not found, meaning signup was not successful
        }
    }
    public boolean isPasswordMismatchErrorDisplayed() {
        try {
            return driver.findElement(passwordMismatchError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isSignupErrorDisplayed() {
        try {
            WebElement errorElement = driver.findElement(errorMessage);
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false; // Error message not found
        }
    }
    public boolean isInvalidEmailErrorDisplayed() {
        try {
            return driver.findElement(invalidEmailError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isWeakPasswordErrorDisplayed() {
        try {
            return driver.findElement(passwordError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isShortPasswordErrorDisplayed() {
        return driver.findElement(passwordError).isDisplayed();
    }
    public boolean isFieldErrorDisplayed(String errorElementId) {
        try {
            WebElement errorElement = driver.findElement(By.id(errorElementId));
            return errorElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
