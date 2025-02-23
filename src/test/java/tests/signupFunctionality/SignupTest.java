package tests.signupFunctionality;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pom.SignupPage;

public class SignupTest {
    @Test(description = "S-001 signup with non existing email") //passed
    public void signupNonExistCredentials(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);
        signupPage.open();
        String firstName = "Mima";
        String lastName = "test";
        String email = "testuser2025@sprint.com";
        String password = "Test@12345";
        String confirmPassword = "Test@12346";
        signupPage.signup(firstName, lastName, email, password, confirmPassword);
        Thread.sleep(5000);

        boolean isSignupSuccessful = signupPage.isSignupSuccessful();
        Assert.assertTrue(isSignupSuccessful, "Signup was not successful.");

        System.out.println("Signup Test Passed!");

        driver.quit();
    }
    @Test(description = "S-002 signup with already exist email not allowed")//passed
    public  void testCredentialExistingError() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);
        signupPage.open();
        String firstName = "Mima";
        String lastName = "test";
        String email = "mktest2025@test.com"; // Use an existing email
        String password = "Test@12345";
        String confirmPassword = "Test@12345";
        signupPage.signup(firstName, lastName, email, password, confirmPassword);
        Thread.sleep(5000);
        boolean isErrorMessageDisplayed = signupPage.isSignupErrorDisplayed();
        Assert.assertTrue(isErrorMessageDisplayed, "Expected error message for existing account was not displayed.");
        System.out.println("Test Passed: Signup with existing email correctly blocked.");

    }
    @Test(description = "S-003 error on signup with mismatching passwords fields ")//passed
    public  void testPasswordMismatchValidation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);
        signupPage.open();
        String firstName = "Mima";
        String lastName = "test";
        String email = "testuser@example.com";
        String password = "Test@12345";
        String confirmPassword = "Test@11111"; // Mismatched password
        signupPage.signup(firstName, lastName, email, password, confirmPassword);
        Thread.sleep(3000);
        boolean isPasswordErrorDisplayed = signupPage.isPasswordMismatchErrorDisplayed();
        Assert.assertTrue(isPasswordErrorDisplayed, "Expected error message for mismatched passwords was not displayed.");

        System.out.println("Test Passed: Password mismatch correctly detected.");

        driver.quit();
    }


@Test(description = "S-004 error on signup with weak password")//passed
    public static void testWeakPasswordValidation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);

        signupPage.open();

        String firstName = "Test";
        String lastName = "User";
        String email = "mktest@test.com";
        String password = "12345678"; // Weak password (does not meet complexity rules)
        String confirmpassword = "12345678";
        signupPage.signup(firstName, lastName, email, password,confirmpassword);

        Thread.sleep(3000);

        boolean isWeakPasswordErrorDisplayed = signupPage.isWeakPasswordErrorDisplayed();

        Assert.assertTrue(isWeakPasswordErrorDisplayed, "Expected error message for weak password was not displayed.");

        System.out.println("Test Passed: Weak password correctly detected.");

        driver.quit();
    }
    @Test(description = "S-005 error on signup with short password")//passed
    public static void testShortPasswordValidation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);

        signupPage.open();

        // Perform signup with short password
        String firstName = "Test";
        String lastName = "User";
        String email = "shortpassword@example.com";
        String password = "12"; // Short password
        String passwordConfirmation = "12";
        signupPage.signup(firstName, lastName, email, password,passwordConfirmation);

        Thread.sleep(3000);

        boolean isShortPasswordErrorDisplayed = signupPage.isShortPasswordErrorDisplayed();

        Assert.assertTrue(isShortPasswordErrorDisplayed, "Expected error message for short password was not displayed.");

        System.out.println("Test Passed: Short password correctly detected.");

        driver.quit();
    }
    @Test(description = "S-006 error on signup with invalid email format")//passed
    public static void testInvalidEmailFormat() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);

        signupPage.open();

        String firstName = "Test";
        String lastName = "User";
        String email = "mktest@";
        String password = "Test@12345";
        String confirmpassword = "Test@12345";
        signupPage.signup(firstName, lastName, email, password,confirmpassword);

        // wait for validation message to appear
        Thread.sleep(3000);
        boolean isInvalidEmailErrorDisplayed = signupPage.isInvalidEmailErrorDisplayed();

        Assert.assertTrue(isInvalidEmailErrorDisplayed, "Expected error message for invalid email format was not displayed.");

        System.out.println("Test Passed: Invalid email format correctly detected.");

        driver.quit();
    }
    @Test(description = "S-007 error on signup with empty fields")//passed
    public static void testEmptyFieldsValidation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        SignupPage signupPage = new SignupPage(driver);
        signupPage.open();
        signupPage.signup("","","","","");
        Thread.sleep(3000);
        boolean isFirstNameErrorDisplayed = signupPage.isFieldErrorDisplayed("firstname-error");
        boolean isLastNameErrorDisplayed = signupPage.isFieldErrorDisplayed("lastname-error");
        boolean isEmailErrorDisplayed = signupPage.isFieldErrorDisplayed("email_address-error");
        boolean isPasswordErrorDisplayed = signupPage.isFieldErrorDisplayed("password-error");
        boolean isConfirmPasswordErrorDisplayed = signupPage.isFieldErrorDisplayed("password-confirmation-error");

        Assert.assertTrue(isFirstNameErrorDisplayed, "First name error message not displayed.");
        Assert.assertTrue(isLastNameErrorDisplayed, "Last name error message not displayed.");
        Assert.assertTrue(isEmailErrorDisplayed, "Email error message not displayed.");
        Assert.assertTrue(isPasswordErrorDisplayed, "Password error message not displayed.");
        Assert.assertTrue(isConfirmPasswordErrorDisplayed, "Confirm password error message not displayed.");

        System.out.println("Test Passed: Required field validation messages displayed correctly.");

        driver.quit();
    }
}
