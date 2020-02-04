package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import com.daeboo.naturerepublic.dto.OrderItemDtoWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private OrderAddress address;

    private String addressee;

    private int deliveryPrice;

    private String phoneNumber;

    private String memo;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Orders orders;

//    public static Delivery createDelivery(OrderItemDto.Create orderItemDto) {
//        Delivery delivery = new Delivery();
//
//        OrderAddress orderAddress = new OrderAddress();
//
//        orderAddress.setMain(orderItemDto.getMainAddress());
//        orderAddress.setDetail(orderItemDto.getDetailAddress());
//
//        delivery.address = orderAddress;
//
//        delivery.addressee = orderItemDto.getName();
//        delivery.deliveryPrice = orderItemDto.getDeliveryPrice();
//        delivery.phoneNumber = orderItemDto.getPhoneNumber();
//        delivery.memo = orderItemDto.getMemo();
//        delivery.deliveryStatus = DeliveryStatus.READY;
//
//        return delivery;
//    }

    public static Delivery createDelivery(OrderItemDtoWrapper orderWrapper) {

        Delivery delivery = new Delivery();

        OrderAddress orderAddress = new OrderAddress();

        orderAddress.setMain(orderWrapper.getMainAddress());
        orderAddress.setDetail(orderWrapper.getDetailAddress());

        delivery.address = orderAddress;

        delivery.addressee = orderWrapper.getAddressee();
        delivery.deliveryPrice = orderWrapper.getDeliveryPrice();
        delivery.phoneNumber = orderWrapper.getPhoneNumber();
        delivery.memo = orderWrapper.getMemo();
        delivery.deliveryStatus = DeliveryStatus.READY;

        return delivery;

    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    // 배송도착
    public void deliveryArrived() {
        this.deliveryStatus = DeliveryStatus.COMP;
    }
}
