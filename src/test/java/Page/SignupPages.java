package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignupPages {
    private static WebElement element = null;


    public static WebElement firstName(WebDriver driver){
        element = driver.findElement(By.id("firstName"));
        return element;
    }

    public static WebElement lastName(WebDriver driver){
        element = driver.findElement(By.id("lastName"));
        return element;
    }

    public static WebElement username(WebDriver driver){
        element = driver.findElement(By.id("email"));
        return element;
    }

    public static WebElement password(WebDriver driver){
        element = driver.findElement(By.id("password"));
        return element;
    }

    public static WebElement submit(WebDriver driver){
        element = driver.findElement(By.id("submit"));
        return element;
    }

    public static WebElement txt_thongbao_1(WebDriver driver){
        element = driver.findElement(By.id("error"));
        return element;
    }

}
