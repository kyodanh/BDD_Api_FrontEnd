package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = ".\\src\\test\\java\\Feature\\B_Home.feature",
        glue = {"Steps"},
        tags = "@Res2",
        monochrome=true,
//        dryRun = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/report_Home.json"})

public class TestRunner_Home {
}
