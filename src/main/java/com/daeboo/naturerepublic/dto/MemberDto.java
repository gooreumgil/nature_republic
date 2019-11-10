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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class MemberDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class SignUp {

        @NotEmpty(message = "이름은 필수입니다.")
        private String name;

        private String city;
        private String street;
        private String zipcode;

        private int birthDay;

        private String number1;

        private String number2;

        private String number3;

        public Member toMember() {
            return Member.createMember(name, new Address(city, street, zipcode),
                    new Birthday(birthDay),
                    new PhoneNumber(number1, number2, number3));

        }

    }

}
