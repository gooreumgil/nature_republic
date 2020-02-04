package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Integer usePoints;

    private Integer savePoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 생성 메소드
//    public static Order createOrder(Member member, Delivery delivery, Integer savePoints, Integer usePoints, OrderItem... orderItems) {
//        Order order = new Order();
//        order.setMember(member);
//
//        order.savePoints = savePoints;
//        order.usePoints = usePoints;
//        order.setDelivery(delivery);
//
//        for (OrderItem orderItem : orderItems) {
//            order.addOrderItem(orderItem);
//        }
//
//        order.orderDateTime = LocalDateTime.now();
//        order.orderStatus = OrderStatus.ORDER;
//
//        return order;
//    }

    public static Orders createOrder(Member member, Delivery delivery, Integer savePoints, Integer usePoints, List<OrderItem> orderItems) {
        Orders orders = new Orders();
        orders.setMember(member);

        orders.savePoints = savePoints;
        orders.usePoints = usePoints;
        orders.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            orders.addOrderItem(orderItem);
        }

        orders.orderDateTime = LocalDateTime.now();
        orders.orderStatus = OrderStatus.ORDER;

        return orders;
    }

    // 연관관계 편의 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrders(this);
    }

    // 조회 로직
    public int totalPrice(Integer deliveryPrice, Integer usePoints) {

        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            int price = orderItem.getTotalPrice();
            totalPrice += price;
        }

        if (deliveryPrice != null) {
            totalPrice += deliveryPrice;
        }

        if (usePoints != null) {
            totalPrice -= usePoints;
        }

        return totalPrice;

    }

    // 순수 상품 가격조회 (할인, 포인트 제외)
    public int totalItemPrice() {
        int totalItemPrice = 0;
        for (OrderItem orderItem : orderItems) {
            int price = orderItem.getOrderPrice() * orderItem.getCount();
            totalItemPrice += price;
        }

        return totalItemPrice;
    }

    // 전체 상품 할인 조회 (포인트 제외)
    public int totalDiscountPrice() {

        int totalDiscountPrice = 0;

        for (OrderItem orderItem : orderItems) {
            int discountPrice = orderItem.getDiscount() * orderItem.getCount();
            totalDiscountPrice += discountPrice;
        }

        return totalDiscountPrice;

    }

    public void orderComplete() {
        this.orderStatus = OrderStatus.COMP;
    }
}
