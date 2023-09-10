package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "D:\\java\\BDD_Api_FrontEnd\\src\\test\\java\\Feature\\Login.feature",
        glue = {"Steps"},
//        tags = "@Smoke",
        monochrome=true,
        plugin = {"pretty", "junit:target/JUnitReports/report.xml",
                "json:target/JSONReports/report.json",
                "pretty", "html:target/cucumber-reports.html"})

public class TestRunner_Login {
}
