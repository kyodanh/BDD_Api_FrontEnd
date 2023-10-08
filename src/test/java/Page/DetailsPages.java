package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DetailsPages {

    private static WebElement element = null;

    public static WebElement txt_detail(WebDriver driver){
        element = driver.findElement(By.xpath("//h1[contains(.,'Contact Details')]"));
        return element;
    }


    public static WebElement detail(WebDriver driver){
        element = driver.findElement(By.xpath("//*[@id=\"contactDetails\"]"));
        return element;
    }

    public static WebElement edit_btn(WebDriver driver){
        element = driver.findElement(By.id("edit-contact"));
        return element;
    }



}
