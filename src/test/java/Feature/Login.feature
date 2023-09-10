Feature: Thực hiện login và 1 trang web và kiểm tra API của trang web đó

  Scenario: User thực hiện login
    Given : user thực hiện mở trang web
    When : Sao khi chuyển tới trang web user thực hiện nhập thông tin username và password vào form
    |username|password|
    |kyodanh@gmail.com        |1234567        |
    And user thực hiện kiểm tra trên API và bắt token trả về
    Then Hệ thống chuyển qua màn hình conact
    And ở API hệ thống trả về danh sách table