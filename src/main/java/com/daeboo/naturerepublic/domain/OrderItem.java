package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.domain.embeded.OrderAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int discount;
    private int count;

    // 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int discount, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.discount = discount;
        orderItem.count = count;

        item.removeStock(count);

        return orderItem;
    }

    // 연관관계 편의 메소드
    public void setOrder(Order order) {
        this.order = order;
    }

    // 로직
    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }

}
