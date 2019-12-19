package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Nullable
    private int orderPrice;

    @Nullable
    private Integer discount;
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
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    // 로직
    public int getTotalPrice() {
        if (discount != null) {
            return (orderPrice - discount) * count;
        } else {
            return this.orderPrice * this.count;
        }
    }

}
