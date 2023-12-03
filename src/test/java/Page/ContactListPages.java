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

    public static WebElement btn_themmoi(WebDriver driver){
        element = driver.findElement(By.xpath("//*[@id=\'add-contact\']"));
        return element;
    }

    public static WebElement clm_name(WebDriver driver,String name){
        element = driver.findElement(By.xpath("//td[contains(.,'"+name+"')]"));
        return element;
    }

    public static WebElement dong(WebDriver driver,int dong){
        element = driver.findElement(By.xpath("//tr["+dong+"]/td[2]"));
        return element;
    }


}
