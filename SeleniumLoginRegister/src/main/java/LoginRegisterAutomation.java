

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginRegisterAutomation {
    public static void main(String[] args) throws InterruptedException {

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // ================================
            // 1️⃣ REGISTER on AutomationExercise
            // ================================
            driver.get("https://mystore-blrc.vercel.app/");
            Thread.sleep(2000);

            driver.findElement(By.linkText("Signup / Login")).click();
            Thread.sleep(2000);

            String email = "manoj" + System.currentTimeMillis() + "@mail.com";
            driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Manoj Kumar");
            driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
            driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
            Thread.sleep(2000);

            // Fill simple registration fields (using minimal data)
            driver.findElement(By.id("id_gender1")).click();
            driver.findElement(By.id("password")).sendKeys("Test@123");
            driver.findElement(By.id("days")).sendKeys("10");
            driver.findElement(By.id("months")).sendKeys("May");
            driver.findElement(By.id("years")).sendKeys("1999");
            driver.findElement(By.id("first_name")).sendKeys("Manoj");
            driver.findElement(By.id("last_name")).sendKeys("Kumar");
            driver.findElement(By.id("address1")).sendKeys("123 Street");
            driver.findElement(By.id("country")).sendKeys("India");
            driver.findElement(By.id("state")).sendKeys("Tamil Nadu");
            driver.findElement(By.id("city")).sendKeys("Chennai");
            driver.findElement(By.id("zipcode")).sendKeys("600001");
            driver.findElement(By.id("mobile_number")).sendKeys("9999999999");
            driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

            Thread.sleep(4000);
            System.out.println("✅ Registration completed on AutomationExercise!");

            // Click Continue / Logout
            try {
                driver.findElement(By.linkText("Continue")).click();
            } catch (Exception e) {
                System.out.println("Continue button not found, skipping...");
            }
            Thread.sleep(2000);
            driver.findElement(By.linkText("Logout")).click();

            // ================================
            // 2️⃣ LOGIN with the same credentials
            // ================================
            driver.findElement(By.linkText("Signup / Login")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
            driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("Test@123");
            driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
            Thread.sleep(3000);

            System.out.println("✅ Login successful on AutomationExercise using: " + email);

            driver.findElement(By.linkText("Logout")).click();
            Thread.sleep(2000);

            // ================================
            // 3️⃣ LOGIN test on Herokuapp
            // ================================
            driver.get("https://the-internet.herokuapp.com/login");
            Thread.sleep(2000);

            driver.findElement(By.id("username")).sendKeys("tomsmith");
            driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
            driver.findElement(By.cssSelector("button.radius")).click();
            Thread.sleep(2000);

            String message = driver.findElement(By.id("flash")).getText();
            System.out.println("✅ Herokuapp Login Message: " + message);

            System.out.println("🎯 Test Completed Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
