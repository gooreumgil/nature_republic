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

    private String addressee;

    @Embedded
    private OrderAddress orderAddress;

    private int orderPrice;
    private int count;

    // 연관관계 편의 메소드
    public void setOrder(Order order) {
        this.order = order;
    }
}
