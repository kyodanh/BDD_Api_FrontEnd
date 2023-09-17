package Steps;

import Page.AddContactPages;
import Page.ContactListPages;
import Page.LoginPages;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HomeSteps {

    public static WebDriver driver = StepUpSteps.driver;

    @Given("User thực hiện truy cập vào web")
    public void user_thực_hiện_truy_cập_vào_web() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");

    }

    @When("user đăng nhập vào web")
    public void user_đăng_nhập_vào_web(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        LoginPages.username(driver).sendKeys(data.get(0).get("username"));
        LoginPages.password(driver).sendKeys(data.get(0).get("password"));
        LoginPages.login_btn(driver).click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        ///////////
    }

    @Then("hệ thống chuyển sang màn hình danh sách")
    public void hệ_thống_chuyển_sang_màn_hình_danh_sách() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        if(ContactListPages.txt_contact(driver).isDisplayed()==true){
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thành công");
            System.out.println("-----------------------");
        }else{
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thất bại");
            System.out.println("-----------------------");
        }
    }




    @Given("Khi user đăng nhập thành công")
    public void khi_user_đăng_nhập_thành_công() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        if(ContactListPages.txt_contact(driver).isDisplayed()==true){
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thành công");
            System.out.println("-----------------------");
        }else{
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thất bại");
            System.out.println("-----------------------");
        }
    }

    @When("Hệ thống chuyển qua màn hình danh sách user")
    public void hệ_thống_chuyển_qua_màn_hình_danh_sách_user() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        //////////////////////////////////////
        System.out.println("----------table-------------");
        WebElement Table = driver.findElement(By.xpath("//*[@id=\"myTable\"]"));
        List<WebElement> rows_table = Table.findElements(By.tagName("tr"));
        int rows_count = rows_table.size();
        for (int row = 0; row < rows_count; row++) {
            List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
            int columns_count = Columns_row.size();
            System.out.println((("Number of cells In Row " + row) + " are ") + columns_count);
            for (int column = 0; column < columns_count; column++) {
                String celltext = Columns_row.get(column).getText();
                System.out.println((((("Cell Value Of row number " + row) + " and column number ") + column) + " Is ") + celltext);
            }
        }
        System.out.println("----------table-------------");
        //////////////////////////////////////
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @Then("Hệ thống chuyển thị thông tin bảng ứng với user {int}")
    public void hệ_thống_chuyển_thị_thông_tin_bảng_ứng_với_user(Integer int1, io.cucumber.datatable.DataTable dataTable) {
        this.driver = StepUpSteps.driver;
        // Write code here that turns the phrase above into concrete actions
        List<Map<String, String>> data_check = dataTable.asMaps(String.class, String.class);
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \""+data_check.get(int1).get("username")+"\",\n" +
                        "    \"password\": \""+data_check.get(int1).get("password")+"\"\n" +
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

    @When("user bấm vào nút thêm mới")
    public void user_bấm_vào_nút_thêm_mới() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        ContactListPages.btn_themmoi(driver).click();
        //////////////////////////////////////
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }
    @When("hệ thống chuyển qua màn hình thêm mới và user nhập thông tin")
    public void hệ_thống_chuyển_qua_màn_hình_thêm_mới_và_user_nhập_thông_tin(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data_input = dataTable.asMaps(String.class, String.class);
        AddContactPages.firstName(driver).sendKeys(data_input.get(0).get("firstName"));
        AddContactPages.lastName(driver).sendKeys(data_input.get(0).get("lastName"));
        AddContactPages.birthdate(driver).sendKeys(data_input.get(0).get("birthdate"));
        AddContactPages.phone(driver).sendKeys(data_input.get(0).get("phone"));
        AddContactPages.street1(driver).sendKeys(data_input.get(0).get("street1"));
        AddContactPages.city(driver).sendKeys(data_input.get(0).get("city"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }

    }

    @When("user bấm vào nút thêm mới contact")
    public void user_bấm_vào_nút_thêm_mới_contact() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        AddContactPages.btn_submit(driver).click();
        //////////////////////////////////////
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }


    @When("hệ thống trả về màn hình danh sách")
    public void hệ_thống_trả_về_màn_hình_danh_sách() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("----------table-------------");
        WebElement Table = driver.findElement(By.xpath("//*[@id=\"myTable\"]"));
        List<WebElement> rows_table = Table.findElements(By.tagName("tr"));
        int rows_count = rows_table.size();
        for (int row = 0; row < rows_count; row++) {
            List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
            int columns_count = Columns_row.size();
            System.out.println((("Number of cells In Row " + row) + " are ") + columns_count);
            for (int column = 0; column < columns_count; column++) {
                String celltext = Columns_row.get(column).getText();
                System.out.println((((("Cell Value Of row number " + row) + " and column number ") + column) + " Is ") + celltext);
            }
        }
        System.out.println("----------table-------------");
    }


    @When("user kiểm tra danh sách contact")
    public void user_kiểm_tra_danh_sách_contact() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("----------table_luc_đau-------------");
        WebElement Table = driver.findElement(By.xpath("//*[@id=\"myTable\"]"));
        List<WebElement> rows_table = Table.findElements(By.tagName("tr"));
        int rows_count = rows_table.size();
        for (int row = 0; row < rows_count; row++) {
            List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
            int columns_count = Columns_row.size();
            System.out.println((("Number of cells In Row " + row) + " are ") + columns_count);
            for (int column = 0; column < columns_count; column++) {
                String celltext = Columns_row.get(column).getText();
                System.out.println((((("Cell Value Of row number " + row) + " and column number ") + column) + " Is ") + celltext);
            }
        }
        System.out.println("----------table_luc_đau-------------");
    }
    @When("User nhập thông tin mới bằng API {int}")
    public void user_nhập_thông_tin_mới_bằng_api(Integer int1, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data_check = dataTable.asMaps(String.class, String.class);
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \""+data_check.get(int1).get("username")+"\",\n" +
                        "    \"password\": \""+data_check.get(int1).get("password")+"\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().all().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        ///////////////////////////////////////////////
        String reponse= given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body("{\n" +
                        "    \"firstName\": \""+data_check.get(int1).get("firstName")+"\",\n" +
                        "    \"lastName\": \""+data_check.get(int1).get("lastName")+"\",\n" +
                        "    \"birthdate\": \""+data_check.get(int1).get("birthdate")+"\",\n" +
                        "    \"email\": \""+data_check.get(int1).get("email")+"\",\n" +
                        "    \"phone\": \""+data_check.get(int1).get("phone")+"\",\n" +
                        "    \"street1\": \""+data_check.get(int1).get("street1")+"\",\n" +
                        "    \"street2\": \""+data_check.get(int1).get("street2")+"\",\n" +
                        "    \"city\": \""+data_check.get(int1).get("city")+"\",\n" +
                        "    \"stateProvince\": \""+data_check.get(int1).get("stateProvince")+"\",\n" +
                        "    \"postalCode\": \""+data_check.get(int1).get("postalCode")+"\",\n" +
                        "    \"country\": \""+data_check.get(int1).get("country")+"\"\n" +
                        "}").
                when().
                post("/contacts").
                then().
                log().body().extract().path("_id").toString();

        System.out.println(reponse);



    }
    @When("user reload lại trang")
    public void user_reload_lại_trang() {
        // Write code here that turns the phrase above into concrete actions
        driver.navigate().refresh();
    }

}
