package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Orders;
import com.daeboo.naturerepublic.domain.OrderItem;
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

        public OrderComplete(Orders orders) {

            List<OrderItem> orderItems = orders.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto.CompletePage completePage = new OrderItemDto.CompletePage(orderItem);
                this.orderItemDtos.add(completePage);
            }

            this.addressee = orders.getDelivery().getAddressee();
            this.orderAddress = orders.getDelivery().getAddress();
            this.deliveryPrice = orders.getDelivery().getDeliveryPrice();
            this.phoneNumber = orders.getDelivery().getPhoneNumber();
            this.memo = orders.getDelivery().getMemo();

            this.totalDiscountPrice = orders.totalDiscountPrice();
            this.totalPrice = orders.totalPrice(deliveryPrice, usePoints);
            this.totalItemPrice = orders.totalItemPrice();
            this.usePoints = orders.getUsePoints();

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

        public Preview(Orders orders) {
            this.id = orders.getId();
            this.itemQuantity = orders.getOrderItems().size();
            this.orderDateTime = orders.getOrderDateTime();
            this.orderStatus = orders.getOrderStatus().toString();
            this.deliveryStatus = orders.getDelivery().getDeliveryStatus().toString();
            this.orderItemDto = new OrderItemDto.Preview(orders.getOrderItems().get(0));
        }

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class DetailPage {

        private Long id;
        private Long memberId;
        private LocalDateTime orderDateTime;
        private String orderStatus;
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

        public DetailPage(Orders orders) {

            this.id = orders.getId();
            this.memberId = orders.getMember().getId();
            this.orderDateTime = orders.getOrderDateTime();
            this.orderStatus = orders.getOrderStatus().toString();

            List<OrderItem> orderItems = orders.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemDtos.add(new OrderItemDto.DetailPage(orderItem));
            }

            this.deliveryStatus = orders.getDelivery().getDeliveryStatus().toString();
            this.addressee = orders.getDelivery().getAddressee();
            this.phoneNumber = orders.getDelivery().getPhoneNumber();
            this.mainAddress = orders.getDelivery().getAddress().getMain();
            this.detailAddress = orders.getDelivery().getAddress().getDetail();
            this.deliveryMemo = orders.getDelivery().getMemo();

            this.totalPrice = orders.totalPrice(orders.getDelivery().getDeliveryPrice(), orders.getUsePoints());
            this.totalItemPrice = orders.totalItemPrice();
            this.totalDiscount = orders.totalDiscountPrice();
            this.deliveryPrice = orders.getDelivery().getDeliveryPrice();
            this.usePoints = orders.getUsePoints();

        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class AdminPage {

        private Long id;
        private int totalPrice;
        private String orderStatus;
        private LocalDateTime orderDateTime;
        private List<OrderItemDto.AdminPage> orderItemDtos = new ArrayList<>();

        public AdminPage(Orders orders) {
            this.id = orders.getId();
            this.totalPrice = orders.totalPrice(orders.getDelivery().getDeliveryPrice(), orders.getUsePoints());
            this.orderStatus = orders.getOrderStatus().toString();
            this.orderDateTime = orders.getOrderDateTime();

            List<OrderItem> orderItems = orders.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto.AdminPage adminPage = new OrderItemDto.AdminPage(orderItem);
                orderItemDtos.add(adminPage);
            }

        }
    }


    @Getter @Setter
    @NoArgsConstructor
    public static class AdminDetailPage {

        private String orderStatus;
        private String deliveryStatus;
        private String phoneNumber;
        private int totalPayPrice;
        private int totalItemPrice;
        private int deliveryPrice;
        private int totalDiscountPrice;
        private int usePoints;
        private String addressee;
        private String mainAddress;
        private String detailAddress;
        private String memo;
        private List<OrderItemDto.DetailPage> orderItemDtos = new ArrayList<>();

        public AdminDetailPage(Orders order) {
            this.orderStatus = order.getOrderStatus().toString();
            this.deliveryStatus = order.getDelivery().getDeliveryStatus().toString();
            this.phoneNumber = order.getDelivery().getPhoneNumber();
            this.totalPayPrice = order.totalPrice(order.getDelivery().getDeliveryPrice(), order.getUsePoints());
            this.totalItemPrice = order.totalItemPrice();
            this.deliveryPrice = order.getDelivery().getDeliveryPrice();
            this.totalDiscountPrice = order.totalDiscountPrice();
            if (order.getUsePoints() == null) {
                this.usePoints = 0;
            } else {
                this.usePoints = order.getUsePoints();
            }
            this.addressee = order.getDelivery().getAddressee();
            this.mainAddress = order.getDelivery().getAddress().getMain();
            this.detailAddress = order.getDelivery().getAddress().getDetail();
            this.memo = order.getDelivery().getMemo();

            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto.DetailPage detailPage = new OrderItemDto.DetailPage(orderItem);
                orderItemDtos.add(detailPage);
            }

        }
    }
}
