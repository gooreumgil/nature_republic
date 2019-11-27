package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import com.daeboo.naturerepublic.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private OrderAddress address;

    private String addressee;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    public static Delivery createDelivery(OrderItemDto.Create orderItemDto) {
        Delivery delivery = new Delivery();
        delivery.address.setMain(orderItemDto.getMainAddress());
        delivery.address.setDetail(orderItemDto.getDetailAddress());
        delivery.addressee = orderItemDto.getName();
        delivery.deliveryStatus = DeliveryStatus.READY;
        return delivery;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
