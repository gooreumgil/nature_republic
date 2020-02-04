package com.daeboo.naturerepublic.domain.embeded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class OrderAddress {

    private String main;
    private String detail;

}
