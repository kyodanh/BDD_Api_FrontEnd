package Steps;

import Page.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HomeSteps {

    public static WebDriver driver = StepUpSteps.driver;

    public String getBase64Screenshot() {
        this.driver = StepUpSteps.driver;
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public String token(){
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        ///////////////////////////////////Login
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"").toString();
        return token;
    }


    @Given("User thực hiện truy cập vào web")
    public void user_thực_hiện_truy_cập_vào_web() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");

    }

    @Then("user logout")
    public void user_logout() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String body = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token()).
                when().
                get("users/me").
//                post("/users/logout").
                then().
                log().body().statusCode(200).toString();
        System.out.println("print"+body);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        ///////////
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
        if (ContactListPages.txt_contact(driver).isDisplayed() == true) {
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thành công");
            System.out.println("-----------------------");
        } else {
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thất bại");
            System.out.println("-----------------------");
        }
    }


    @Given("Khi user đăng nhập thành công")
    public void khi_user_đăng_nhập_thành_công() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        if (ContactListPages.txt_contact(driver).isDisplayed() == true) {
            System.out.println("-----------------------");
            System.out.println("Đăng nhập thành công");
            System.out.println("-----------------------");
        } else {
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
//            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
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
                        "    \"email\": \"" + data_check.get(int1).get("username") + "\",\n" +
                        "    \"password\": \"" + data_check.get(int1).get("password") + "\"\n" +
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
        this.driver = StepUpSteps.driver;
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
        this.driver = StepUpSteps.driver;
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
//            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        System.out.println("----------table-------------");
    }


    @When("user kiểm tra danh sách contact")
    public void user_kiểm_tra_danh_sách_contact() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
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
//        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        System.out.println("----------table_luc_đau-------------");
    }

    @When("User nhập thông tin mới bằng API {int}")
    public void user_nhập_thông_tin_mới_bằng_api(Integer int1, io.cucumber.datatable.DataTable dataTable) {
        this.driver = StepUpSteps.driver;
        List<Map<String, String>> data_check = dataTable.asMaps(String.class, String.class);
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"" + data_check.get(int1).get("username") + "\",\n" +
                        "    \"password\": \"" + data_check.get(int1).get("password") + "\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().all().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        ///////////////////////////////////////////////
        String reponse = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body("{\n" +
                        "    \"firstName\": \"" + data_check.get(int1).get("firstName") + "\",\n" +
                        "    \"lastName\": \"" + data_check.get(int1).get("lastName") + "\",\n" +
                        "    \"birthdate\": \"" + data_check.get(int1).get("birthdate") + "\",\n" +
                        "    \"email\": \"" + data_check.get(int1).get("email") + "\",\n" +
                        "    \"phone\": \"" + data_check.get(int1).get("phone") + "\",\n" +
                        "    \"street1\": \"" + data_check.get(int1).get("street1") + "\",\n" +
                        "    \"street2\": \"" + data_check.get(int1).get("street2") + "\",\n" +
                        "    \"city\": \"" + data_check.get(int1).get("city") + "\",\n" +
                        "    \"stateProvince\": \"" + data_check.get(int1).get("stateProvince") + "\",\n" +
                        "    \"postalCode\": \"" + data_check.get(int1).get("postalCode") + "\",\n" +
                        "    \"country\": \"" + data_check.get(int1).get("country") + "\"\n" +
                        "}").
                when().
                post("/contacts").
                then().
                log().body().extract().path("_id").toString();

        System.out.println(reponse);


    }

    @When("user reload lại trang")
    public void user_reload_lại_trang() {
        this.driver = StepUpSteps.driver;
        // Write code here that turns the phrase above into concrete actions
        driver.navigate().refresh();
//        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
    }

    @When("user thực hiện chọn đòng để xem chi tiết contact {int}")
    public void user_thực_hiện_chọn_đòng_để_xem_chi_tiết_contact(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        ContactListPages.dong(driver, int1).click();
    }

    @When("hệ thống chuyển qua màn hình chi tiết")
    public void hệ_thống_chuyển_qua_màn_hình_chi_tiết() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        if (DetailsPages.txt_detail(driver).isDisplayed() == true) {
            System.out.println("hệ thống chuyển qua màn hình " + DetailsPages.txt_detail(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, ContactListPages.txt_contact(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        } else {
            Assert.fail("loi");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, "hệ thống không chuyển qua màn hình detail");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        }
    }

    @Then("hệ thống hiển thị thông tin chi tiết")
    public void hệ_thống_hiển_thị_thông_tin_chi_tiết() {
        // Write code here that turns the phrase above into concrete actions
        //////////////////////////////////////
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
        //////////////////////////////////////
        if (DetailsPages.txt_detail(driver).isDisplayed() == true) {
            System.out.println("hệ thống chuyển qua màn hình " + DetailsPages.txt_detail(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, DetailsPages.detail(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        } else {
            Assert.fail("loi");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, "Hệ thống hiển thị thông tin");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        }
    }


    @When("hệ thống hiển thị thông tin chi tiết ứng với người dùng vừa chọn")
    public void hệ_thống_hiển_thị_thông_tin_chi_tiết_ứng_với_người_dùng_vừa_chọn() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        if (DetailsPages.txt_detail(driver).isDisplayed() == true) {
            System.out.println("hệ thống chuyển qua màn hình " + DetailsPages.txt_detail(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, DetailsPages.detail(driver).getText());
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        } else {
            Assert.fail("loi");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, "hệ thống không chuyển qua màn hình detail");
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        }
    }

    @When("User thực hiện vào API với user")
    public void user_thực_hiện_vào_api_với_user(io.cucumber.datatable.DataTable dataTable) {
        this.driver = StepUpSteps.driver;
        List<Map<String, String>> logindata = dataTable.asMaps(String.class, String.class);

        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"" + logindata.get(0).get("username") + "\",\n" +
                        "    \"password\": \"" + logindata.get(0).get("passworđ") + "\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"");
    }

    @Then("User thực hiện vào API get contact list")
    public void user_thực_hiện_vào_api_get_contact_list() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
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
                log().
                body().extract().path("\"token\"").toString();
        ////////////////////////////////////////////////////////
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body().toString();
        System.out.println("-------------------------------------");
        System.out.println(data);
        System.out.println("-------------------------------------");
        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, "Print" + data);
    }

    @Then("User thực hiện xuất body ứng với dòng user chọn")
    public void user_thực_hiện_xuất_body_ứng_với_dòng_user_chọn() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
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
                log().
                body().extract().path("\"token\"").toString();
        ////////////////////////////////////////////////////////
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body().extract().jsonPath().getString("[0]._id");

        System.out.println("-------------------------------------");
        System.out.println(data);
        System.out.println("-------------------------------------");
        //////////////////////////////////////////////////////////
        String data_contact = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts/" + data + "").
                then().
                log().body().toString();
        System.out.println("-------------------------------------");
        System.out.println("user_1 " + data_contact);
        System.out.println("-------------------------------------");

    }


    @When("user thực hiện bấm vào button edit")
    public void user_thực_hiện_bấm_vào_button_edit() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        DetailsPages.edit_btn(driver).click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @When("user nhập thông tin chỉnh sửa")
    public void user_nhập_thông_tin_chỉnh_sửa(io.cucumber.datatable.DataTable dataTable) {
        this.driver = StepUpSteps.driver;
        List<Map<String, String>> data_input = dataTable.asMaps(String.class, String.class);
        EditPages.firstName(driver).clear();
        EditPages.firstName(driver).sendKeys(data_input.get(0).get("firstName"));
        EditPages.lastName(driver).clear();
        EditPages.lastName(driver).sendKeys(data_input.get(0).get("lastName"));
        EditPages.birthdate(driver).clear();
        EditPages.birthdate(driver).sendKeys(data_input.get(0).get("birthdate"));
        EditPages.email(driver).clear();
        EditPages.email(driver).sendKeys(data_input.get(0).get("email"));
        EditPages.phone(driver).clear();
        EditPages.phone(driver).sendKeys(data_input.get(0).get("phone"));
        EditPages.street1(driver).clear();
        EditPages.street1(driver).sendKeys(data_input.get(0).get("street1"));
        EditPages.street2(driver).clear();
        EditPages.street2(driver).sendKeys(data_input.get(0).get("street2"));
        EditPages.city(driver).clear();
        EditPages.city(driver).sendKeys(data_input.get(0).get("city"));
        EditPages.stateProvince(driver).clear();
        EditPages.stateProvince(driver).sendKeys(data_input.get(0).get("stateProvince"));
        EditPages.postalCode(driver).clear();
        EditPages.postalCode(driver).sendKeys(data_input.get(0).get("postalCode"));
        EditPages.country(driver).clear();
        EditPages.country(driver).sendKeys(data_input.get(0).get("country"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @When("user bấm vào button submit")
    public void user_bấm_vào_button_submit() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        EditPages.submit(driver).click();
//        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @When("User thực hiện vào API với kiểm tra thông tin đã chỉnh sửa")
    public void user_thực_hiện_vào_api_với_kiểm_tra_thông_tin_đã_chỉnh_sửa(io.cucumber.datatable.DataTable dataTable) {
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        ///////////////////////////////////Login
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"").toString();
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body().extract().jsonPath().getString("[0]._id");
        System.out.println("-------------------------------------");
        System.out.println(data);
        System.out.println("-------------------------------------");
        //////////////////////////////////Detail contact
        String data_contact = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts/" + data).
                then().
                log().body().toString();
        System.out.println("-------------------------------------");
        System.out.println("user_1 " + data_contact);
        System.out.println("-------------------------------------");
    }

    @Then("Hệ thống hiển thị thông tin chỉnh sửa kèm với thông tin chỉnh sửa API")
    public void hệ_thống_hiển_thị_thông_tin_chỉnh_sửa_kèm_với_thông_tin_chỉnh_sửa_api() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        ///////////////////////////////////Login
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"").toString();
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                    log().body().extract().jsonPath().getString("[0]._id");
        //////////////////////////////////Detail contact
        String data_contact = given().
                header("Content-Type", "application/js  on").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts/" + data).
                then().
                log().body().toString();
        System.out.println("-------------------------------------");
        System.out.println("user_1 " + data_contact);
        System.out.println("-------------------------------------");
        ////////////////////////////////
//        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
    }

    @When("user thực hiện bấm vào button xóa")
    public void user_thực_hiện_bấm_vào_button_xóa() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        DetailsPages.btn_xoa(driver).click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @When("Hệ thông hiển thị pop up và user chọn có")
    public void hệ_thông_hiển_thị_pop_up_và_user_chọn_có() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        Alert alert = driver.switchTo().alert();
        driver.switchTo().alert();
        alert.accept();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    @Then("Hệ thống hiển thị thông báo thành công và xóa contact ra khỏi hệ thống")
    public void hệ_thống_hiển_thị_thông_báo_thành_công_và_xóa_contact_ra_khỏi_hệ_thống() {
        // Write code here that turns the phrase above into concrete actions
//        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }


//    @Given("Khi user đăng nhập vào API {int}")
//    public void khi_user_đăng_nhập_vào_api(Integer int1, io.cucumber.datatable.DataTable dataTable) {
//        this.driver = StepUpSteps.driver;
//        if (ContactListPages.txt_contact(driver).isDisplayed() == true) {
//            System.out.println("-----------------------");
//            System.out.println("Đăng nhập thành công");
//            System.out.println("-----------------------");
//        } else {
//            System.out.println("-----------------------");
//            System.out.println("Đăng nhập thất bại");
//            System.out.println("-----------------------");
//        }
//    }

    @When("Hệ thống hiển thị token của user")
    public void hệ_thống_hiển_thị_token_của_user() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
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
                log().body().
                extract().path("token").toString();
        System.out.println("Token is :" + token);
        if(token.equals(token)){
            ExtentCucumberAdapter.getCurrentStep().log(Status.PASS,"Token is :" + token);
        }else{
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL,"đăng nhập thất bại");
        }

    }

    @When("user thực hiện call API danh sách")
    public void user_thực_hiện_call_api_danh_sách() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        ///////////////////////////////////Login
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"").toString();
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body().toString();
        //////////////////////////////////Detail contact
        ExtentCucumberAdapter.getCurrentStep().log(Status.PASS,"Danh sách contact" + data);
    }

    @When("user thực hiện xem chi tiết contact")
    public void user_thực_hiện_xem_chi_tiết_contact() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        ///////////////////////////////////Login
        String token = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                post("/users/login").
                then().
                log().
                body().extract().path("\"token\"").toString();
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts").
                then().
                log().body().extract().jsonPath().getString("[0]._id");
        //////////////////////////////////Detail contact
        String data_contact = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                when().
                get("/contacts/" + data).
                then().
                log().body().toString();
        System.out.println("-------------------------------------");
        System.out.println("user_1 " + data_contact);
        System.out.println("-------------------------------------");
    }

    @When("user thực hiện xóa contact bàng API")
    public void user_thực_hiện_xóa_contact_bàng_api() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token()).
                when().
                get("/contacts").
                then().
                log().body().extract().jsonPath().getString("[0]._id");
        //////////////////////////////////Delete contact
        String data_delete = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token()).
                when().
                delete("/contacts/"+data).
                then().
                log().body().toString();
        System.out.println("boddy" + data_delete);

    }

    @Then("contact bị xóa và khi call API xem chi tiết hệ thống không hiển thị contact")
    public void contact_bị_xóa_và_khi_call_api_xem_chi_tiết_hệ_thống_không_hiển_thị_contact() {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        /////////////////////////////////Contact
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token()).
                body("{\n" +
                        "    \"firstName\": \"Updated\",\n" +
                        "    \"lastName\": \"Username\",\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                get("/contacts").
                then().
                log().body().toString();
        System.out.println("boddy" + data);
    }


    @When("^user thực hiện bấm vào name để xem thông tin chi tiết thông tin (.*)$")
    public void user_thực_hiện_bấm_vào_name_để_xem_thông_tin_chi_tiết_thông_tin_test_danh(String name) {
        // Write code here that turns the phrase above into concrete actions
        this.driver = StepUpSteps.driver;
        ContactListPages.clm_name(driver,name).click();
    }


    @When("user thực hiện update user đăng nhập")
    public void user_thực_hiện_update_user_đăng_nhập() {
        this.driver = StepUpSteps.driver;
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String data = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token()).
                body("{\n" +
                        "    \"firstName\": \"Updated\",\n" +
                        "    \"lastName\": \"Username\",\n" +
                        "    \"email\": \"kyodanh@gmail.com\",\n" +
                        "    \"password\": \"1234567\"\n" +
                        "}").
                when().
                patch("/users/me").
                then().
                log().body().toString();
        System.out.println("boddy1" + data);
    }



}
