package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.OrderItem;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

public class OrderItemDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class Create {

        private Long itemId;
        private int orderPrice;
        private Integer discount;
        private int count;

    }

    @Getter
    @Setter
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

    @Getter
    @Setter
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DetailPage {

        private Long id;
        private Long itemId;
        private String s3Key;
        private String name;
        private int itemPrice;
        private int count;

        public DetailPage(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.itemId = orderItem.getItem().getId();
            this.s3Key = orderItem.getItem().getItemSrcs().get(0).getS3Key();
            this.name = orderItem.getItem().getNameKor();
            this.itemPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AdminPage {

        private Long id;
        private Long itemId;
        private String itemName;
        private String s3Key;

        public AdminPage(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.itemId = orderItem.getItem().getId();
            this.itemName = orderItem.getItem().getNameKor();
            this.s3Key = orderItem.getItem().getItemSrcs().get(0).getS3Key();
        }
    }

}
