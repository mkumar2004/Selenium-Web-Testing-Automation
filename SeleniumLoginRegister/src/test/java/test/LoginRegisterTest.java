package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginRegisterTest {

    WebDriver driver;
    String baseUrl = "https://automationexercise.com/"; 
    String email;
    String password = "Test@12345";

    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void registerUser() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Signup / Login")).click();

        // Create random email
        email = "manoj" + System.currentTimeMillis() + "@mail.com";
        driver.findElement(By.name("name")).sendKeys("Manoj Kumar");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        Thread.sleep(2000);

        // Fill registration form
        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("days")).sendKeys("10");
        driver.findElement(By.id("months")).sendKeys("May");
        driver.findElement(By.id("years")).sendKeys("1995");
        driver.findElement(By.id("first_name")).sendKeys("Manoj");
        driver.findElement(By.id("last_name")).sendKeys("Kumar");
        driver.findElement(By.id("address1")).sendKeys("Chennai");
        driver.findElement(By.id("state")).sendKeys("Tamil Nadu");
        driver.findElement(By.id("city")).sendKeys("Chennai");
        driver.findElement(By.id("zipcode")).sendKeys("600001");
        driver.findElement(By.id("mobile_number")).sendKeys("9999999999");
        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        Thread.sleep(2000);
        System.out.println("✅ Registration successful for: " + email);
    }

    public void loginUser() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Signup / Login")).click();
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        Thread.sleep(2000);
        System.out.println("✅ Login successful for: " + email);
    }

    public void tearDown() {
        if (driver != null) driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        LoginRegisterTest test = new LoginRegisterTest();
        test.setup();
        test.registerUser();
        test.loginUser();
        test.tearDown();
    }
}
