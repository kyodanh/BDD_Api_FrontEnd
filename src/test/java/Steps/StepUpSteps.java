package Steps;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class StepUpSteps {


    public static WebDriver driver;
    public static ExtentReports report;
    public static ExtentTest logger;




    @Before
    public void browers_setup(){
        ExtentSparkReporter extent = new ExtentSparkReporter(new File(System.getProperty("user.dir")+"/Reports/demo.html"));
        report= new ExtentReports();
        report.attachReporter(extent);
        System.setProperty("webdriver.chrome.driver", "D:\\java\\BDD_Api_FrontEnd\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println("open browers");
    }


    @After
    public void teardown(){
        driver.quit();
        System.out.println("close browers");
        report.flush();
    }

}
