package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ParabankAutomation{

    WebDriver driver;
    String baseRegisterUrl = "https://parabank.parasoft.com/parabank/register.htm";
    String baseLoginUrl = "https://parabank.parasoft.com/parabank/login.htm";

    // Fixed user credentials
    String firstName = "John";
    String lastName = "Doe";
    String address = "123 Street";
    String city = "Chennai";
    String state = "Tamil Nadu";
    String zipCode = "600001";
    String phone = "9999999999";
    String ssn = "123-45-6789";
    String username = "manojtest123";  // fixed username
    String password = "Test@12345";     // fixed password

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

    public void registerUser() throws InterruptedException {
        driver.get(baseRegisterUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        typeSlowly(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer.firstName"))), firstName, 200);
        typeSlowly(driver.findElement(By.id("customer.lastName")), lastName, 200);
        typeSlowly(driver.findElement(By.id("customer.address.street")), address, 200);
        typeSlowly(driver.findElement(By.id("customer.address.city")), city, 200);
        typeSlowly(driver.findElement(By.id("customer.address.state")), state, 200);
        typeSlowly(driver.findElement(By.id("customer.address.zipCode")), zipCode, 200);
        typeSlowly(driver.findElement(By.id("customer.phoneNumber")), phone, 200);
        typeSlowly(driver.findElement(By.id("customer.ssn")), ssn, 200);
        typeSlowly(driver.findElement(By.id("customer.username")), username, 200);
        typeSlowly(driver.findElement(By.id("customer.password")), password, 200);
        typeSlowly(driver.findElement(By.id("repeatedPassword")), password, 200);

        driver.findElement(By.xpath("//input[@value='Register']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Welcome')]")));
        System.out.println("✅ Registration successful for username: " + username);
    }

    public void loginUser(String user, String pass) throws InterruptedException {
        driver.get(baseLoginUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));

        typeSlowly(usernameField, user, 200);
        Thread.sleep(300);
        typeSlowly(passwordField, pass, 200);
        Thread.sleep(300);

        loginButton.click();

        Thread.sleep(2000);

        // Check if login successful
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));
            System.out.println("✅ Login successful for username: " + user);
        } catch (Exception e) {
            // Get error message if login fails
            try {
                WebElement errorMsg = driver.findElement(By.xpath("//p[contains(@class,'error')]"));
                System.out.println("❌ Login failed for username: " + user + " | Error: " + errorMsg.getText());
            } catch (Exception ex) {
                System.out.println("❌ Login failed for username: " + user + " | Unknown error");
            }
        }
    }

    public void tearDown() {
        if (driver != null) driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        ParabankAutomation test = new ParabankAutomation();
        test.setup();

        // Register user (only once, comment this after first successful registration)
        test.registerUser();

        // Test both valid and invalid logins
        String[][] credentials = {
                {test.username, test.password},        // valid
                {test.username, "WrongPass"},          // invalid password
                {"WrongUser", test.password},          // invalid username
                {"WrongUser", "WrongPass"}             // invalid username and password
        };

        for (String[] cred : credentials) {
            test.loginUser(cred[0], cred[1]);
        }

        test.tearDown();
    }
}

