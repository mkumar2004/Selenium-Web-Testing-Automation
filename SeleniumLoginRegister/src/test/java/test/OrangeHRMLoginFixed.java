package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeHRMLoginFixed {

    WebDriver driver;
    String baseUrl = "https://opensource-demo.orangehrmlive.com/";

    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Method to type slowly into a field
    public void typeSlowly(WebElement element, String text, int delayMillis) throws InterruptedException {
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
            Thread.sleep(delayMillis);
        }
    }

    // Login method
    public void login(String username, String password) throws InterruptedException {
        driver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        // Type username and password slowly
        typeSlowly(usernameField, username, 300);
        Thread.sleep(500);
        typeSlowly(passwordField, password, 300);
        Thread.sleep(500);

        // Click login
        loginButton.click();

        // Wait 2 seconds to see result
        Thread.sleep(2000);

        // Check if login is successful
        if (driver.getCurrentUrl().contains("/web/index.php/dashboard/index")) {
            System.out.println("✅ Login successful for username: " + username);
            // Logout to test next credentials
            WebElement userDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[@class='oxd-userdropdown-name']")));
            userDropdown.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//a[text()='Logout']")).click();
            Thread.sleep(2000);
        } else {
            // Get error message if login fails
            try {
                WebElement errorMsg = driver.findElement(
                        By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']"));
                System.out.println("❌ Login failed for username: " + username + " | Error: " + errorMsg.getText());
            } catch (Exception e) {
                System.out.println("❌ Login failed for username: " + username + " | Unknown error");
            }
        }
    }

    public void tearDown() {
        if (driver != null) driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
    	OrangeHRMLoginFixed test = new OrangeHRMLoginFixed();
        test.setup();

        // Array of test credentials: {username, password}
        String[][] testCredentials = {
                {"Admin", "admin123"},      // valid
                {"Admin", "wrongpassword"}, // invalid password
                {"WrongUser", "admin123"},  // invalid username
                {"test", "test"}            // invalid username and password
        };

        // Execute login for all credentials
        for (String[] creds : testCredentials) {
            test.login(creds[0], creds[1]);
        }

        test.tearDown();
    }
}

