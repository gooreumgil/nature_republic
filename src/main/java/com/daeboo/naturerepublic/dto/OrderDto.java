package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.domain.OrderItem;
import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private int totalPrice;
        private int totalItemPrice;
        private int totalDiscountPrice;
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

            this.totalPrice = order.totalPrice(deliveryPrice, usePoints);
            this.totalItemPrice = order.totalItemPrice();
            this.totalDiscountPrice = order.totalDiscountPrice();
            this.usePoints = order.getUsePoints();

        }

    }

}
