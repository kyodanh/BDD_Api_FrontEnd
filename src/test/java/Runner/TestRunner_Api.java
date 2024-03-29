package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = ".\\src\\test\\java\\Feature\\A_Login.feature",
        glue = {"Steps"},
        tags = "@Api1",
        monochrome=true,
        dryRun = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/report.json"})

public class TestRunner_Api {
}
