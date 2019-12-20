package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.NumberUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class MemberDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class Update {

        private Long id;
        private String name;
        private String passwordConfirm;
        private String email;

        private String city;
        private String street;
        private String zipcode;

        private int birthday;
        private String birthdayResponse;

        private String number1;
        private String number2;
        private String number3;

        public Update(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.city = member.getAddress().getCity();
            this.street = member.getAddress().getStreet();
            this.zipcode = member.getAddress().getZipcode();

            Birthday birthday = member.getBirthday();
            String year = String.valueOf(birthday.getYear());
            String month = String.valueOf(birthday.getMonth());
            String day = String.valueOf(birthday.getDay());

            if (Integer.parseInt(month) < 10) {
                month = "0" + month;
            }

            if (Integer.parseInt(day) < 10) {
                day = "0" + day;
            }

            this.birthdayResponse = year + month + day;

            this.number1 = member.getPhoneNumber().getNumber1();
            this.number2 = member.getPhoneNumber().getNumber2();
            this.number3 = member.getPhoneNumber().getNumber3();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class SignUp {

        @NotEmpty(message = "이름은 필수입니다.")
        private String name;

        @NotEmpty(message = "패스워드는 필수입니다.")
        private String password;

        @NotEmpty(message = "이메일은 필수입니다.")
        @Email
        private String email;

        private String city;
        private String street;
        private String zipcode;

        private int birthDay;

        private String number1;
        private String number2;
        private String number3;
        private List<String> roles;

        public Member toMember() {
            return Member.createMember(name, password, email,
                    new Address(city, street, zipcode),
                    new Birthday(birthDay),
                    new PhoneNumber(number1, number2, number3),
                    roles);

        }

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class ListView {

        private Long id;
        private String name;

        private Address address;
        private Birthday birthday;
        private PhoneNumber phoneNumber;

        private int orderCount;

        public ListView(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.address = member.getAddress();
            this.birthday = member.getBirthday();
            this.phoneNumber = member.getPhoneNumber();
            this.orderCount = member.getOrders().size();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Login {

        private String name;
        private String password;

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class OrderPage {

        private Long id;
        private String name;
        private Address address;
        private PhoneNumber phoneNumber;
        private Integer points;

        public OrderPage(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.address = member.getAddress();
            this.phoneNumber = member.getPhoneNumber();
            this.points = member.getPoints();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class MyPageIndex {

        private Long id;
        private String name;
        private Integer points;

        public MyPageIndex(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.points = member.getPoints();

//            List<Order> orders = member.getOrders();
//
//            for (Order order : orders) {
//                OrderDto.Preview preview = new OrderDto.Preview(order);
//                this.orders.add(preview);
//            }

        }
    }












}
