Feature: Thực hiện login và 1 trang web và kiểm tra API của trang web đó


  @Res
  Scenario: User thực hiện login
    Given user thực hiện mở trang web
    When Sao khi chuyển tới trang web user thực hiện nhập thông tin username và password vào form
      | username          | password |
      | kyodanh@gmail.com | 1234567  |
    And user bấm vào login
    And user thực hiện kiểm tra trên API và bắt token trả về
    Then Hệ thống chuyển qua màn hình conact
    And ở API hệ thống trả về danh sách table


  @Res
  Scenario Outline: user thực hiện tạo tài khoản người dùng với và kiểm tra thông tin người dùng
    Given user thực hiện mở trang web
    When Sao khi chuyển tới trang web user thực hiện nhập thông tin username và password vào form
      | username           | password |
      | danhtest@gmail.com | 1234567  |
    And user bấm vào login
    But Nhưng hệ thống báo user sai vì <username_check> và <pass_check> không có trong hệ thống
    And user thực hiện đăng kí thông tin
    And user nhập thông tin đăng kí
      | firstname | lastname | username_signup | password_signup |
      | danhn_1   | danhn_1  | danhtest        | 1234567         |
    And hệ thống hiển thị thông báo thành công <username_check> và <pass_check> được ghi nhận vào server
    Then Hệ thống chuyển qua màn hình conact của user
      | username_moi        | password_moi |
      | danhtest7@gmail.com | 1234567      |

    Examples:
      | username_check     | pass_check |
      | danhtest@gmail.com | 1234567    |


  @Res
  Scenario Outline: user thực hiện tạo tài khoản người thiếu f_name và l_name
    Given user thực hiện mở trang web
    When User thực hiện bấm vào nút đăng kí
    And user nhập thông tin đăng kí <row>
      | firstname | lastname | username_signup     | password_signup |
      | null      | null     | danhtest7@gmail.com | 1234567         |
    Then Hệ thống hiển thị thông báo cần nhập f_name và l_name

    Examples:
      | row |
      | 0   |
