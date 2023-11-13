Feature: Thực hiện kiểm tra màn hình danh sách và API của page danh sách

  Background: User thực hiện đăng nhập vào trang web
    Given User thực hiện truy cập vào web
    When user đăng nhập vào web
      | username          | password |
      | kyodanh@gmail.com | 1234567  |
    Then hệ thống chuyển sang màn hình danh sách

 @Test_jenkin
  Scenario Outline: user kiểm tra bảng danh sách khi người dùng đăng nhập thành công
    Given Khi user đăng nhập thành công
    When  Hệ thống chuyển qua màn hình danh sách user
    Then Hệ thống chuyển thị thông tin bảng ứng với user <row>
      | username          | password |
      | kyodanh@gmail.com | 1234567  |

    Examples:
      | row |
      | 0   |


  @Test_jenkin_2
  Scenario Outline: user thêm mới danh sách vào bảng
    Given Khi user đăng nhập thành công
    When  user bấm vào nút thêm mới
    And hệ thống chuyển qua màn hình thêm mới và user nhập thông tin
      | firstName | lastName | birthdate  | phone      | street1         | city        |
      | test      | danh     | 1998/03/08 | 0942058905 | thích quảng đức | Thu Dau Mot |
    And user bấm vào nút thêm mới contact
    And hệ thống trả về màn hình danh sách
    Then Hệ thống chuyển thị thông tin bảng ứng với user <row>
      | username          | password |
      | kyodanh@gmail.com | 1234567  |

    Examples:
      | row |
      | 0   |


  @Test_jenkin_3
  Scenario Outline: user thêm mới danh sách vào bảng bằng API
    Given Khi user đăng nhập thành công
    When  user kiểm tra danh sách contact
    And User nhập thông tin mới bằng API <row>
      | username          | password | firstName | lastName | birthdate  | email      | phone      | street1         | street2            | city        | stateProvince | postalCode | country |
      | kyodanh@gmail.com | 1234567  | test_13  | danh     | 1998/03/08 | danh@gmail.com | 0942058905 | thích quảng đức | phường chánh nghĩa | Thu Dau Mot | Bình Dương    | 12345      | VietNam |
    And user reload lại trang
    Then Hệ thống chuyển thị thông tin bảng ứng với user <row>
      | username          | password |
      | kyodanh@gmail.com | 1234567  |

    Examples:
      | row |
      | 0   |



  @Test_jenkin_4
  Scenario Outline: user xem thông tin chi tiết contact
    Given Khi user đăng nhập thành công
    When  user thực hiện chọn đòng để xem chi tiết contact <row>
    And hệ thống chuyển qua màn hình chi tiết
    Then hệ thống hiển thị thông tin chi tiết

    Examples:
      | row |
      | 1   |


  @Test_jenkin_5
  Scenario Outline: user xem thông tin chi tiết contact vaf thực hiện chỉnh sửa contact
    Given Khi user đăng nhập thành công
    When user thực hiện chọn đòng để xem chi tiết contact <row>
    And hệ thống hiển thị thông tin chi tiết ứng với người dùng vừa chọn
    And User thực hiện vào API với user
      | username          | passworđ |
      | kyodanh@gmail.com | 1234567  |
    Then User thực hiện vào API get contact list
    Then User thực hiện xuất body ứng với dòng user chọn

    Examples:
      | row |
      | 1   |


  @Test_jenkin_5
  Scenario Outline: user xem thông tin chi tiết contact
    Given Khi user đăng nhập thành công
    When user thực hiện chọn đòng để xem chi tiết contact <row>
    And hệ thống hiển thị thông tin chi tiết ứng với người dùng vừa chọn
    And user thực hiện bấm vào button edit
    And user nhập thông tin chỉnh sửa
      | username          | password | firstName | lastName | birthdate  | email          | phone      | street1         | street2            | city        | stateProvince | postalCode | country |
      | kyodanh@gmail.com | 1234567  | test_14   | danh     | 1998/03/08 | danh@gmail.com | 0942058905 | thích quảng đức | phường chánh nghĩa | Thu Dau Mot | Bình Dương    | 12345      | VietNam |
    And user bấm vào button submit
    And User thực hiện vào API với kiểm tra thông tin đã chỉnh sửa
      | username          | passworđ |
      | kyodanh@gmail.com | 1234567  |
    Then Hệ thống hiển thị thông tin chỉnh sửa kèm với thông tin chỉnh sửa API

    Examples:
      | row |
      | 1   |


  @Test_jenkin_5
  Scenario Outline: user xem thông tin chi tiết contact và thực hiện thông tin user
    Given Khi user đăng nhập thành công
    When user thực hiện chọn đòng để xem chi tiết contact <row>
    And hệ thống hiển thị thông tin chi tiết ứng với người dùng vừa chọn
    And user thực hiện bấm vào button xóa
    And Hệ thông hiển thị pop up và user chọn có
    Then Hệ thống hiển thị thông báo thành công và xóa contact ra khỏi hệ thống

    Examples:
      | row |
      | 1   |


  @Test_jenkin_5
  Scenario: user thực hiện xóa contact thông tin dựa vào API
    Given Khi user đăng nhập thành công
    When Hệ thống hiển thị token của user
    And user thực hiện call API danh sách
    And user thực hiện xem chi tiết contact
    And user thực hiện xóa contact bàng API
    Then contact bị xóa và khi call API xem chi tiết hệ thống không hiển thị contact

