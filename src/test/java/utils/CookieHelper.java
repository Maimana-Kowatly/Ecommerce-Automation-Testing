package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.time.Instant;

public class CookieHelper {
    public static void saveCookiesToFile(WebDriver driver, String filePath) throws IOException {
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file);
        for (Cookie cookie : driver.manage().getCookies()) {
            String expiryFormatted = (cookie.getExpiry() != null) ?
                    cookie.getExpiry().toInstant().toString() : "N/A";

            writer.write(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";"
                    + cookie.getPath() + ";" + expiryFormatted + ";" + cookie.isSecure() + "\n");
        }
        writer.close();
        System.out.println("Cookies saved to file.");
    }


    public static void loadCookiesFromFile(WebDriver driver, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(";", 6); // Limit splits to 6 fields
            if (tokens.length < 6) {
                System.out.println("Invalid cookie format: " + line);
                continue;
            }
            String name = tokens[0];
            String value = tokens[1];
            String domain = tokens[2];
            String path = tokens[3];
            String expiry = tokens[4];
            boolean isSecure = Boolean.parseBoolean(tokens[5]);

            // Parse expiry using Instant (ISO 8601 compatible)
            Instant expiryDate = expiry.equalsIgnoreCase("N/A") ? null : Instant.parse(expiry);

            Cookie cookie = new Cookie.Builder(name, value)
                    .domain(domain)
                    .path(path)
                    .expiresOn(expiryDate != null ? java.util.Date.from(expiryDate) : null)
                    .isSecure(isSecure)
                    .build();

            driver.manage().addCookie(cookie);
        }
        reader.close();
        System.out.println("Cookies loaded from file.");
    }


}
