package com.daeboo.naturerepublic.domain.embeded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class PhoneNumber {

    private String number1;
    private String number2;
    private String number3;

    protected PhoneNumber() {
    }

    public PhoneNumber(String number1, String number2, String number3) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }
}
