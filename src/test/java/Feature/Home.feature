Feature: User thực hiện kểm tra các chức năng có trong màn hình Home

  Background: User đã đăng nhập vào hệ thống
    Given user thực hiện truy câp tới hệ thống
    And user thực hiện đăng nhập thông tin
    |username|password|
    |admin   |admin   |
    When user thực hiện truy cập vào màn hình Home
    Then hệ thống hiển thị màn hình Home

    Scenario: User thực hiện kiểm tra thông tin data trong bảng