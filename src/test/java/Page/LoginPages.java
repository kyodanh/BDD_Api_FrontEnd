package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPages {

    private static WebElement element = null;


    public static WebElement username(WebDriver driver){
        element = driver.findElement(By.id("email"));
        return element;
    }

    public static WebElement password(WebDriver driver){
        element = driver.findElement(By.id("password"));
        return element;
    }

    public static WebElement login_btn(WebDriver driver){
        element = driver.findElement(By.id("submit"));
        return element;
    }

    public static WebElement txt_thong_bao_sai(WebDriver driver) {
        element = driver.findElement(By.id("error"));
        return element;
    }

    public static WebElement btn_signup(WebDriver driver){
        element = driver.findElement(By.id("signup"));
        return element;
    }
}
