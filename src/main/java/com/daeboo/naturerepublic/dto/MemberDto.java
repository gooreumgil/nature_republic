package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class MemberDto {

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
    public static class Order {

        private Long id;
        private String name;
        private Address address;
        private PhoneNumber phoneNumber;
        private Integer points;

        public Order(Member member) {
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
        private Integer points;
        private List<com.daeboo.naturerepublic.domain.Order> orders = new ArrayList<>();

        public MyPageIndex(Member member) {
            this.id = member.getId();
            this.points = member.getPoints();
            this.orders = member.getOrders();
        }
    }












}
