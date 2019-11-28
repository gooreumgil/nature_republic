package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.OrderItemDto;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import com.daeboo.naturerepublic.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Order order(OrderItemDto.Create orderItemDto) {

        // 엔티티 조회
        Member member = memberRepository.findById(orderItemDto.getMemberId()).get();
        Item item = itemRepository.findById(orderItemDto.getItemId()).get();

        // 배송정보 생성
        Delivery delivery = Delivery.createDelivery(orderItemDto);

        int orderPrice = orderItemDto.getPrice();
        int discount = orderItemDto.getDiscount();
        int count = orderItemDto.getCount();
        Integer usePoints = orderItemDto.getUsePoints();

        // 주문상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, discount, count);

        // 주문생성
        Order order = Order.createOrder(member, delivery, usePoints, orderItem);

        // 포인트 차감
        member.minusPoints(usePoints);

        return orderRepository.save(order);

    }

}
