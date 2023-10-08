package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "D:\\java\\BDD_Api_FrontEnd\\src\\test\\java\\Feature\\Home.feature",
        glue = {"Steps"},
        tags = "@tag3",
        monochrome=true,
//        dryRun = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})

public class TestRunner_Home {
}
