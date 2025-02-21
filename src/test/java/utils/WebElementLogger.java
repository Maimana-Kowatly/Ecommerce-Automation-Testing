package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class WebElementLogger {

    public static void logWebElement(WebElement element) {
        logWebElementWithChildren(element, 0);
    }

    private static void logWebElementWithChildren(WebElement element, int depth) {
        if (element == null) {
            System.out.println(indent(depth) + "WebElement is null.");
            return;
        }

        try {
            // Log current element details
            String details = String.format(
                    "%s<Tag: %s | Text: '%s' | Class: '%s' | ID: '%s' | Name: '%s'>",
                    indent(depth),
                    element.getTagName(),
                    element.getText().isEmpty() ? "[No Text]" : element.getText().trim(),
                    element.getAttribute("class"),
                    element.getAttribute("id"),
                    element.getAttribute("name")
            );
            System.out.println(details);

            // Find and log children recursively
            List<WebElement> children = element.findElements(By.xpath("./*"));
            for (WebElement child : children) {
                logWebElementWithChildren(child, depth + 1);
            }

        } catch (Exception e) {
            System.err.println(indent(depth) + "Failed to log WebElement: " + e.getMessage());
        }
    }

    private static String indent(int depth) {
        return "  ".repeat(Math.max(0, depth));
    }
}
