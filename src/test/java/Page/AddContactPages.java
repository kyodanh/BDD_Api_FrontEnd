package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddContactPages {
    private static WebElement element = null;


    public static WebElement firstName(WebDriver driver){
        element = driver.findElement(By.id("firstName"));
        return element;
    }

    public static WebElement lastName(WebDriver driver){
        element = driver.findElement(By.id("lastName"));
        return element;
    }

    public static WebElement birthdate(WebDriver driver){
        element = driver.findElement(By.id("birthdate"));
        return element;
    }

    public static WebElement email(WebDriver driver){
        element = driver.findElement(By.id("email"));
        return element;
    }

    public static WebElement phone(WebDriver driver){
        element = driver.findElement(By.id("phone"));
        return element;
    }

    public static WebElement street1(WebDriver driver){
        element = driver.findElement(By.id("street1"));
        return element;
    }

    public static WebElement city(WebDriver driver){
        element = driver.findElement(By.id("city"));
        return element;
    }

    public static WebElement btn_submit(WebDriver driver){
        element = driver.findElement(By.id("submit"));
        return element;
    }

}
