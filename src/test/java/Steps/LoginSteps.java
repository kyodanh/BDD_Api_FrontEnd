package Steps;

import Page.ContactListPages;
import Page.LoginPages;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LoginSteps {

    public static WebDriver driver = StepUpSteps.driver;

    @Given(": user thực hiện mở trang web")
    public void user_thực_hiện_mở_trang_web() {
        // Write code here that turns the phrase above into concrete actions

        this.driver = StepUpSteps.driver;
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
    }

    @When(": Sao khi chuyển tới trang web user thực hiện nhập thông tin username và password vào form")
    public void sao_khi_chuyển_tới_trang_web_user_thực_hiện_nhập_thông_tin_username_và_password_vào_form(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        LoginPages.username(driver).sendKeys(data.get(0).get("username"));
        LoginPages.password(driver).sendKeys(data.get(0).get("password"));
        LoginPages.login_btn(driver).click();
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
        ///////////
    }

    @When("user thực hiện kiểm tra trên API và bắt token trả về")
    public void user_thực_hiện_kiểm_tra_trên_api_và_bắt_token_trả_về() {
        // Write code here that turns the phrase above into concrete actions
       baseURI = "https://thinking-tester-contact-list.herokuapp.com";
       String token = given().
                             header("Content-Type","application/json").
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
       System.out.println("Token is :"+token);
       ///////////////////////
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @Then("Hệ thống chuyển qua màn hình conact")
    public void hệ_thống_chuyển_qua_màn_hình_conact() {
        // Write code here that turns the phrase above into concrete actions
        if(ContactListPages.txt_contact(driver).isDisplayed()==true){
            System.out.println(ContactListPages.txt_contact(driver).getText());
        }else{
            Assert.fail("Hệ thống đăng nhập không thành công");
        }
        ///////////////
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }


    @Then("ở API hệ thống trả về danh sách table")
    public void ở_api_hệ_thống_trả_về_danh_sách_table() {
        // Write code here that turns the phrase above into concrete actions
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type","application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().all().
                extract().path("token").toString();
        System.out.println("Token is :"+token);
        ///////////////////////////////////////////////
        given().
                header("Content-Type","application/json").
                header("Authorization","Bearer "+token).
                when().
                get("/contacts").
                then().
                log().all();
    }
}
