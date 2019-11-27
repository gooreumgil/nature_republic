package com.daeboo.naturerepublic.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderItemDto {

    @Getter @Setter
    @NoArgsConstructor
    @Data
    public static class Create {

        private Long memberId;
        private Long itemId;
        private int price;
        private int discount;
        private int usePoints;
        private int count;
        private String name;
        private String phoneNumber;
        private String mainAddress;
        private String detailAddress;


    }

}
