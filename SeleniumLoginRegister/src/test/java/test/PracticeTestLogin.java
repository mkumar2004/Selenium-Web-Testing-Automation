package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PracticeTestLogin {

    WebDriver driver;
    String baseUrl = "https://practicetestautomation.com/practice-test-login/";

    // Fixed user credentials
    String validUsername = "student";
    String validPassword = "Password123";

    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Slow typing for visible automation
    public void typeSlowly(WebElement element, String text, int delayMillis) throws InterruptedException {
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
            Thread.sleep(delayMillis);
        }
    }

    public void loginUser(String username, String password) throws InterruptedException {
        driver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        typeSlowly(usernameField, username, 200);
        Thread.sleep(300);
        typeSlowly(passwordField, password, 200);
        Thread.sleep(300);

        loginButton.click();

        Thread.sleep(2000);

        // Check if login successful
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Logged In Successfully')]")));
            System.out.println("✅ Login successful for username: " + username);
        } catch (Exception e) {
            // Get error message if login fails
            try {
                WebElement errorMsg = driver.findElement(By.xpath("//div[@id='error']"));
                System.out.println("❌ Login failed for username: " + username + " | Error: " + errorMsg.getText());
            } catch (Exception ex) {
                System.out.println("❌ Login failed for username: " + username + " | Unknown error");
            }
        }
    }

    public void tearDown() {
        if (driver != null) driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        PracticeTestLogin test = new PracticeTestLogin();
        test.setup();

        // Test both valid and invalid logins
        String[][] credentials = {
                {test.validUsername, test.validPassword},        // valid
                {test.validUsername, "WrongPassword"},           // invalid password
                {"WrongUser", test.validPassword},               // invalid username
                {"WrongUser", "WrongPassword"}                   // invalid username and password
        };

        for (String[] cred : credentials) {
            test.loginUser(cred[0], cred[1]);
        }

        test.tearDown();
    }
}

