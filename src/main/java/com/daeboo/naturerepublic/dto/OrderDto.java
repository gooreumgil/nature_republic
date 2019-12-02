package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.domain.OrderItem;
import com.daeboo.naturerepublic.domain.OrderStatus;
import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class OrderComplete {

        private List<OrderItemDto.CompletePage> orderItemDtos = new ArrayList<>();

        private String addressee;
        private OrderAddress orderAddress;
        private Integer deliveryPrice;
        private String phoneNumber;
        private String memo;
        private Integer totalPrice;
        private Integer totalItemPrice;
        private Integer totalDiscountPrice;
        private Integer usePoints;

        public OrderComplete(Order order) {

            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto.CompletePage completePage = new OrderItemDto.CompletePage(orderItem);
                this.orderItemDtos.add(completePage);
            }

            this.addressee = order.getDelivery().getAddressee();
            this.orderAddress = order.getDelivery().getAddress();
            this.deliveryPrice = order.getDelivery().getDeliveryPrice();
            this.phoneNumber = order.getDelivery().getPhoneNumber();
            this.memo = order.getDelivery().getMemo();

            this.totalDiscountPrice = order.totalDiscountPrice();
            this.totalPrice = order.totalPrice(deliveryPrice, usePoints) - totalDiscountPrice;
            this.totalItemPrice = order.totalItemPrice();
            this.usePoints = order.getUsePoints();

        }


    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Preview {

        private Long id;

        private int itemQuantity;
        private LocalDateTime orderDateTime;
        private String orderStatus;
        private String deliveryStatus;
        private OrderItemDto.Preview orderItemDto;
        public Preview(Order order) {
            this.id = order.getId();
            this.orderDateTime = order.getOrderDateTime();
            this.orderStatus = order.getOrderStatus().toString();
            this.deliveryStatus = order.getDelivery().getDeliveryStatus().toString();
            this.orderItemDto = new OrderItemDto.Preview(order.getOrderItems().get(0));
        }

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class DetailPage {

        private Long id;
        private LocalDateTime orderDateTime;
        private List<OrderItemDto.DetailPage> orderItemDtos = new ArrayList<>();
        private String deliveryStatus;

        private String addressee;
        private String phoneNumber;
        private String mainAddress;
        private String detailAddress;
        private String deliveryMemo;

        private int totalPrice;
        private int totalItemPrice;
        private Integer totalDiscount;
        private Integer deliveryPrice;
        private Integer usePoints;

        public DetailPage(Order order) {

            this.id = order.getId();
            this.orderDateTime = order.getOrderDateTime();

            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemDtos.add(new OrderItemDto.DetailPage(orderItem));
            }

            this.deliveryStatus = order.getDelivery().getDeliveryStatus().toString();
            this.addressee = order.getDelivery().getAddressee();
            this.phoneNumber = order.getDelivery().getPhoneNumber();
            this.mainAddress = order.getDelivery().getAddress().getMain();
            this.detailAddress = order.getDelivery().getAddress().getDetail();
            this.deliveryMemo = order.getDelivery().getMemo();

            this.totalPrice = order.totalPrice(order.getDelivery().getDeliveryPrice(), order.getUsePoints());
            this.totalItemPrice = order.totalItemPrice();
            this.totalDiscount = order.totalDiscountPrice();
            this.deliveryPrice = order.getDelivery().getDeliveryPrice();
            this.usePoints = order.getUsePoints();

        }
    }


}
