package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactListPages {
    private static WebElement element = null;


    public static WebElement txt_contact(WebDriver driver){
        element = driver.findElement(By.xpath("/html/body/div/header/h1"));
        return element;
    }
}
