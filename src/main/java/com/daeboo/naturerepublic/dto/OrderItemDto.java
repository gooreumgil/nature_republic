package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.domain.OrderItem;
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
        private Integer discount;
        private Integer usePoints;
        private Integer deliveryPrice;
        private int count;
        private String name;
        private String phoneNumber;
        private String mainAddress;
        private String detailAddress;
        private String memo;


    }

    @Getter @Setter
    @NoArgsConstructor
    public static class CompletePage {

        private String itemName;
        private String s3Key;
        private int itemPrice;
        private int count;
        private int discount;

        public CompletePage(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getNameKor();
            this.s3Key = orderItem.getItem().getItemSrcs().get(0).getS3Key();
            this.itemPrice = orderItem.getItem().getPrice();
            this.count = orderItem.getCount();
            this.discount = orderItem.getDiscount();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Preview {

        private Long id;
        private String s3Key;
        private String name;

        public Preview(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.s3Key = orderItem.getItem().getItemSrcs().get(0).getS3Key();
            this.name = orderItem.getItem().getNameKor();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class DetailPage {

        private Long id;
        private String s3Key;
        private String name;
        private int itemPrice;
        private int count;

        public DetailPage(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.s3Key = orderItem.getItem().getItemSrcs().get(0).getS3Key();
            this.name = orderItem.getItem().getNameKor();
            this.itemPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

}
