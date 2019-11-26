package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderItemDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class Create {

        private Long memberId;
        private Long itemId;
        private int price;
        private int count;
        private String addressee;
        private String phoneNumber;
        private OrderAddress orderAddress;


    }

}
