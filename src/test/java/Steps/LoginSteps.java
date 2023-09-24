package Steps;

import Page.ContactListPages;
import Page.LoginPages;
import Page.SignupPages;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LoginSteps {

    public static WebDriver driver = StepUpSteps.driver;
    public static ExtentReports report = StepUpSteps.report;
    public static ExtentTest logger = StepUpSteps.logger;

    @Given("user thực hiện mở trang web")
    public void user_thực_hiện_mở_trang_web() {
        logger = report.createTest("user thực hiện mở trang web");
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        logger.pass("Navigated to the specified URL");
    }

    @When("Sao khi chuyển tới trang web user thực hiện nhập thông tin username và password vào form")
    public void sao_khi_chuyển_tới_trang_web_user_thực_hiện_nhập_thông_tin_username_và_password_vào_form(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        LoginPages.username(driver).sendKeys(data.get(0).get("username"));
        LoginPages.password(driver).sendKeys(data.get(0).get("password"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        ///////////
    }

    @When("user thực hiện kiểm tra trên API và bắt token trả về")
    public void user_thực_hiện_kiểm_tra_trên_api_và_bắt_token_trả_về() {
        // Write code here that turns the phrase above into concrete actions
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                statusCode(200).
                log().all().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        logger.pass("Token is :" + token);
        ///////////////////////
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @Then("Hệ thống chuyển qua màn hình conact")
    public void hệ_thống_chuyển_qua_màn_hình_conact() {
        // Write code here that turns the phrase above into concrete actions
        if (ContactListPages.txt_contact(driver).isDisplayed() == true) {
            System.out.println(ContactListPages.txt_contact(driver).getText());
            logger.pass(ContactListPages.txt_contact(driver).getText());
        } else {
            Assert.fail("Hệ thống đăng nhập không thành công");
        }
        ///////////////
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }


    @Then("ở API hệ thống trả về danh sách table")
    public void ở_api_hệ_thống_trả_về_danh_sách_table() {
        // Write code here that turns the phrase above into concrete actions
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().all().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        ///////////////////////////////////////////////
        given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().all();
    }


    @When("user bấm vào login")
    public void user_bấm_vào_login() {
        // Write code here that turns the phrase above into concrete actions
        LoginPages.login_btn(driver).click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @When("^Nhưng hệ thống báo user sai vì (.*) và (.*) không có trong hệ thống$")
    public void nhưng_hệ_thống_báo_user_sai_vì_danhtest_gmail_com_và_da_không_có_trong_hệ_thống(String username_check, String pass_check) {
        // Write code here that turns the phrase above into concrete actions
        if (LoginPages.txt_thong_bao_sai(driver).isDisplayed() == true) {
            System.out.println(LoginPages.txt_thong_bao_sai(driver).getText());
        } else {
            Assert.fail("Hiện đăng nhập đúng bị sai case");
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        ///////////////////////API đăng nhập user sai
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"" + username_check + "\",\n" +
                        "    \"password\": \"" + pass_check + "\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().body();
    }


    @When("user thực hiện đăng kí thông tin")
    public void user_thực_hiện_đăng_kí_thông_tin() {
        // Write code here that turns the phrase above into concrete actions
        LoginPages.btn_signup(driver).click();
    }

    @When("user nhập thông tin đăng kí")
    public void user_nhập_thông_tin_đăng_kí(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> signup_data = dataTable.asMaps(String.class, String.class);
        SignupPages.firstName(driver).sendKeys(signup_data.get(0).get("firstname"));
        SignupPages.lastName(driver).sendKeys(signup_data.get(0).get("lastname"));
        SignupPages.username(driver).sendKeys(signup_data.get(0).get("username_signup"));
        SignupPages.password(driver).sendKeys(signup_data.get(0).get("password_signup"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        SignupPages.submit(driver).click();
    }

    @When("^hệ thống hiển thị thông báo thành công (.*) và (.*) được ghi nhận vào server$")
    public void hệ_thống_hiển_thị_thông_báo_thành_công_danhtest_gmail_com_và_da_được_ghi_nhận_vào_server(String username_check, String pass_check) {
        // Write code here that turns the phrase above into concrete actions
        ///////////////////////API đăng nhập user sai
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"" + username_check + "\",\n" +
                        "    \"password\": \"" + pass_check + "\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().body();
    }


    @Then("Hệ thống chuyển qua màn hình conact của user")
    public void hệ_thống_chuyển_qua_màn_hình_conact_của_user(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        List<Map<String, String>> datacheck = dataTable.asMaps(String.class, String.class);

        ///////////////////////////////////////
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"" + datacheck.get(0).get("username_moi") + "\",\n" +
                        "    \"password\": \"" + datacheck.get(0).get("password_moi") + "\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().body().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        ///////////////////////////////////////////////
        given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body();
    }

}
