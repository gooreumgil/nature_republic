package com.daeboo.naturerepublic.domain.embeded;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Birthday {

    private int year;
    private int month;
    private int day;

    protected Birthday() {
    }

    public Birthday(int birthDay) {
        String year = String.valueOf(birthDay).substring(0, 4);
        String month = String.valueOf(birthDay).substring(4, 6);
        String day = String.valueOf(birthDay).substring(6, 8);

        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
    }
}
