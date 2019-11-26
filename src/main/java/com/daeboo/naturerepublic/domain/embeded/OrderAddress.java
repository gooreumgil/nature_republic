package com.daeboo.naturerepublic.domain.embeded;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class OrderAddress {

    private String main;
    private String detail;

}
