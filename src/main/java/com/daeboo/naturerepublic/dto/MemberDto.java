package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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

        public Member toMember() {
            return Member.createMember(name, password, email,
                    new Address(city, street, zipcode),
                    new Birthday(birthDay),
                    new PhoneNumber(number1, number2, number3));

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

}
