package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WishlistPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By addToWishlistButton = By.cssSelector("a.action.towishlist"); // Add to Wishlist button
    private By successMessage = By.cssSelector("div.message-success"); // Success message
    private By wishlistCount = By.cssSelector("span.counter-number"); // Wishlist counter
    private By colorOption = By.id("option-label-color-93-item-57"); // Purple
    private By sizeOption = By.id("option-label-size-143-item-166"); // XS
    private By wishlistItems = By.cssSelector(".product-item"); // Wishlist items
    private By removeButtons = By.cssSelector(".a.btn-remove");

    private By shareWishlistButton = By.cssSelector("button.action.share[title='Share Wish List']"); // Share button
    private By emailInput = By.id("email_address"); // Email input
    private By sendButton = By.cssSelector("button.action.submit"); // Send button
    private By successShareMessage = By.cssSelector("div.message-success.success.message");
    private By invalidEmailerrorMessage = By.id("email_address-error");
    private By emptyWishlistMessage = By.cssSelector(".message.info.empty");
    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addToWishlist() {
        System.out.println("adding to wishlist");
        driver.findElement(addToWishlistButton).click();
    }

//    public boolean isAddedSuccessfully() {
//        return driver.findElement(successMessage).isDisplayed();
//    }
//
//    public String getWishlistCount() {
//        return driver.findElement(wishlistCount).getText();
//    }
    public void selectColorAndSize() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Select color
        WebElement colorOptionElement = wait.until(ExpectedConditions.elementToBeClickable(colorOption));
        colorOptionElement.click();

        // Select size
        WebElement sizeOptionElement = wait.until(ExpectedConditions.elementToBeClickable(sizeOption));
        System.out.println("Selected size: " + sizeOptionElement.getText());
        sizeOptionElement.click();
    }

    public boolean isWishlistPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.urlContains("wishlist"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isWishlistEmpty() {
        List<WebElement> items = driver.findElements(wishlistItems);
        return items.isEmpty();
    }
    public boolean isSeeDetailsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement seeDetailsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".tooltip.wrapper.product-item-tooltip .toggle"))); // Locator for "See Details"
            return seeDetailsButton.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


    public boolean isRedirectedToLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the URL changes OR a known login element appears
        return wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("customer/account/login"),
                ExpectedConditions.presenceOfElementLocated(By.id("email")) // Example: Email field on login page
        ));
    }
    public boolean removeFirstItemFromWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        try {
            //  Ensure the wishlist page is loaded properly
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ol.product-items")));

            // Check if there are any wishlist items
            List<WebElement> wishlistItems = driver.findElements(By.cssSelector("li.product-item"));
            if (wishlistItems.isEmpty()) {
                System.out.println("No items in the wishlist to remove.");
                return false;  //
            }
            //  Get the first wishlist item
            WebElement firstWishlistItem = wishlistItems.get(0);
            //  Hover over the product image
            WebElement productImage = firstWishlistItem.findElement(By.cssSelector("a.product-item-photo"));
            actions.moveToElement(productImage).perform();
            Thread.sleep(1000); // Give time for hover effect (can be replaced with explicit wait)
            //  Wait for the remove button to appear and click it
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(firstWishlistItem.findElement(By.cssSelector("a.btn-remove"))));
            actions.moveToElement(removeButton).click().perform();
            // Handle possible page refresh (wait until the item disappears)
            wait.until(ExpectedConditions.stalenessOf(firstWishlistItem));
            //  Wait for the success message
            By successMessageLocator = By.cssSelector("div.message-success.success.message");
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
            System.out.println("Success Message: " + successMessage.getText());
            return successMessage.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to remove item from wishlist: " + e.getMessage());
            return false;  //
        }
    }


    public boolean EditFirstItemFromWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        try {
            //  Ensure the wishlist page is loaded properly
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ol.product-items")));

            //  Check if there are any wishlist items
            List<WebElement> wishlistItems = driver.findElements(By.cssSelector("li.product-item"));
            if (wishlistItems.isEmpty()) {
                System.out.println("No items in the wishlist to edit.");
                return false;  //
            }
            Thread.sleep(3000);
            //  Get the first wishlist item
            WebElement firstWishlistItem = wishlistItems.get(0);
            System.out.println(firstWishlistItem.getText());
            //  Hover over the product image
            WebElement productImage = firstWishlistItem.findElement(By.cssSelector("a.product-item-photo"));
            actions.moveToElement(productImage).perform();
            Thread.sleep(3000);

            WebElement editButton = firstWishlistItem.findElement(By.cssSelector("a.action.edit[href*='/wishlist/index/configure/']"));
            editButton.click();
            System.out.println(editButton.getText());
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".swatch-option.text")));

            boolean isOptionSelected = driver.findElements(By.cssSelector(".swatch-option.text.selected")).size() > 0;

            if (!isOptionSelected) {
                System.out.println("Bug: Previously selected options are NOT highlighted.");
            } else {
                System.out.println("✅ Test Passed: Previously selected options are correctly highlighted.");
            }

            return isOptionSelected;
        } catch (Exception e) {
            System.out.println("Failed to edit item in wishlist: " + e.getMessage());
            return false;
        }
    }

    public void clickShareWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(shareWishlistButton)).click();
    }
    public void hoverOverFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.product.photo.product-item-photo"))); // First product image locator

        actions.moveToElement(productImage).perform();
    }

    public void enterEmails(String emails) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(emails);
    }

    //for share wishlist
    public void clickSend() {
        wait.until(ExpectedConditions.elementToBeClickable(sendButton)).click();
    }


    public boolean isWishlistSharedSuccessfully() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successShareMessage)).isDisplayed();
    }
    public boolean isInvalidEmailErrorDisplayed() {
        WebElement errorElement = driver.findElement(invalidEmailerrorMessage);
        return errorElement.isDisplayed() && errorElement.getText().contains("Please enter valid email addresses");
    }

    public int getWishlistItemCount() {
        List<WebElement> items = driver.findElements(wishlistItems);
        return items.size();
    }

    public void removeLastItem() {
        List<WebElement> items = driver.findElements(removeButtons);
        if (!items.isEmpty()) {
            items.get(items.size() - 1).click();
            wait.until(ExpectedConditions.invisibilityOf(items.get(items.size() - 1))); // Wait for removal
        }
    }

    public boolean isWishlistEmptyMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyWishlistMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isPreviousOptionsSelected() {
        WebElement errorElement = driver.findElement(By.cssSelector("swatch-option text selected"));
        return errorElement.isDisplayed() && errorElement.getText().contains("Please enter valid email addresses");
    }



}
